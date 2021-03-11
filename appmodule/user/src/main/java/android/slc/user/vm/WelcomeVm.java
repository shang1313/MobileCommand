package android.slc.user.vm;

import android.app.Application;
import android.content.DialogInterface;
import android.slc.app.startup.TaskConstant;
import android.slc.app.startup.TaskUtils;
import android.slc.appbase.data.api.main.callback.SlcObserver;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.extras.command.config.ConstantsCommand;
import android.slc.extras.user.entity.UserVo;
import android.slc.extras.user.repository.local.UserDaoRepository;
import android.slc.appbase.data.repository.remote.AppServiceRepository;
import android.slc.appbase.data.utils.SysMustFileUtils;
import android.slc.appbase.vm.AppBaseViewModel;
import android.slc.appbase.vm.GlobalDataVm;
import android.slc.network.SimpleDownloadService;
import android.slc.or.SlcCallbackConfig;
import android.slc.or.SlcParam;
import android.slc.rx.SimpleSingleObserver;
import android.slc.slcdialog.SlcPopup;
import android.slc.user.R;
import android.slc.user.data.config.ConstantsUser;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ServiceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.effective.android.anchors.AnchorsManager;
import com.effective.android.anchors.task.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;

/**
 * @author slc
 * @date 2020-07-10 11:31
 */
public class WelcomeVm extends AppBaseViewModel {
    public ObservableBoolean isLoading = new ObservableBoolean(false);
    private boolean taskIsEnd;
    private boolean isNormalRequestPermission;

    public WelcomeVm(@NonNull Application application) {
        super(application);
    }

    public void init() {
        ThreadUtils.runOnUiThreadDelayed(() -> {
            AnchorsManager.getInstance().debuggable(!ConstantsBase.VALUE_IS_ON_LINE)
                    .start(TaskUtils.delayTaskBuilder()
                            .add(new Task("serviceAndPermission") {
                                @Override
                                protected void run(@NotNull String s) {
                                    ServiceUtils.startService(SimpleDownloadService.class);
                                    taskIsEnd = true;
                                    checkPermission();
                                }
                            }).dependOn(TaskConstant.TASK_THIRD_PART, TaskConstant.TASK_SLC)
                            .build());
        }, 1000);
    }

    /**
     * 监察权限
     */
    public void checkPermission() {
        if (!taskIsEnd) {
            return;
        }
        if (!isNormalRequestPermission) {
            isNormalRequestPermission = true;
            Dexter.withActivity(getViewDelegate().getActivityContext()).withPermissions(ConstantsBase.Permission.getBasePermissionArray()).withListener(
                    new CompositeMultiplePermissionsListener(new BaseMultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            super.onPermissionsChecked(report);
                            if (report.areAllPermissionsGranted()) {
                                isLoading.set(true);
                                SysMustFileUtils.initAssets()
                                        .subscribe(new SimpleSingleObserver<>());
                                autoLogin();
                            } else if (report.isAnyPermissionPermanentlyDenied()) {
                                new SlcPopup.NativeAlertDialogBuilder(getViewDelegate().getActivityContext())
                                        .setMessage(R.string.label_base_permission_setting_hint)
                                        .setCancelable(false)
                                        .setPositiveButton(R.string.action_i_know)
                                        .setOnClickListener((dialog, which) -> {
                                            ActivityUtils.startActivity(IntentUtils.getLaunchAppDetailsSettingsIntent(AppUtils.getAppPackageName()));
                                            isNormalRequestPermission = false;
                                        })
                                        .create().show();
                            } else {
                                new SlcPopup.NativeAlertDialogBuilder(getViewDelegate().getActivityContext())
                                        .setMessage(R.string.label_base_permission_hint)
                                        .setCancelable(false)
                                        .setPositiveButton(R.string.action_i_know)
                                        .setOnClickListener((dialog, which) -> {
                                            isNormalRequestPermission = false;
                                            checkPermission();
                                        })
                                        .create().show();
                            }
                        }
                    })).check();
        }
    }

    private void autoLogin() {
        AppServiceRepository.terminalVerify(DeviceUtils.getUniqueDeviceId())
                .compose(bindToLifecycle())
                .subscribe(new SlcObserver<UserVo>(SlcCallbackConfig.newBuilder().setDialogMsg(R.string.user_label_verifying_device).build()) {
                    @Override
                    protected void onSucceed(UserVo data) {
                        if (data == null) {
                            hintInactivated();
                            //registerDevice();
                        } else {
                            switch (data.getStatus()) {
                                case ConstantsUser.Value.USER_STATUS_ACTIVATION:
                                    UserDaoRepository.saveUser(data);
                                    GlobalDataVm.getInstance().userOf.set(data);
                                    ARouter.getInstance().build(ConstantsCommand.Path.PATH_MAIN).navigation(getViewDelegate().getActivityContext());
                                    finish();
                                    break;
                                case ConstantsUser.Value.USER_STATUS_INACTIVATED:
                                case ConstantsUser.Value.USER_STATUS_LOCKING:
                                case ConstantsUser.Value.USER_STATUS_ERASE:
                                case ConstantsUser.Value.USER_STATUS_LOGOUT:
                                case ConstantsUser.Value.USER_STATUS_RESET:
                                    String deviceHintStr = StringUtils.getString(R.string.user_label_equipment_already_service_info_index_out);
                                    try {
                                        deviceHintStr = StringUtils.getString(R.string.user_label_equipment_already_x,
                                                StringUtils.getStringArray(R.array.userStatusArray)[data.getStatus()]);
                                    } catch (IndexOutOfBoundsException e) {
                                    }
                                    new SlcPopup.NativeAlertDialogBuilder(getViewDelegate().getActivityContext())
                                            .setTitle(R.string.label_prompt)
                                            .setMessage(deviceHintStr)
                                            .setPositiveButton(R.string.action_i_know)
                                            .setCancelable(false)
                                            .setOnClickListener((dialog, which) -> {
                                                dialog.dismiss();
                                                AppUtils.exitApp();
                                            })
                                            .create().show();
                                    break;
                            }

                        }
                    }

                    @Override
                    protected void onFailed(int errorCode, String errorMessage) {
                        ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 2000);
                    }
                });
    }

    /**
     * 提示未激活
     */
    private void hintInactivated() {
        new SlcPopup.NativeAlertDialogBuilder(getViewDelegate().getActivityContext())
                .setTitle(R.string.label_prompt)
                .setMessage(StringUtils.getString(R.string.user_label_equipment_already_x,
                        StringUtils.getStringArray(R.array.userStatusArray)[1]))
                .setPositiveButton(R.string.action_i_know)
                .setCancelable(false)
                .setOnClickListener((dialog, which) -> {
                    dialog.dismiss();
                    AppUtils.exitApp();
                })
                .create().show();
    }

    @SuppressWarnings("all")
    private void registerDevice() {
        View contentView = getViewDelegate().getActivityContext().getLayoutInflater().inflate(R.layout.user_dialog_register_device_edit_view, null);
        EditText et_input = contentView.findViewById(R.id.et_input);
        new SlcPopup.NativeAlertDialogBuilder(getViewDelegate().getActivityContext())
                .setTitle(R.string.user_label_register_device)
                .setView(contentView)
                .setDefPositiveAndNegativeButton()
                .setPositiveClickIsAutoDismiss(false)
                .setOnClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                String phoneText = et_input.getText().toString();
                                if (StringUtils.isEmpty(phoneText) || !RegexUtils.isMobileSimple(phoneText)) {
                                    ToastUtils.showShort(R.string.user_label_please_enter_a_valid_phone_number);
                                    return;
                                }
                                dialog.dismiss();
                                AppServiceRepository.terminalRegister(SlcParam.newBuilder()
                                        .put("deviceId", DeviceUtils.getUniqueDeviceId())
                                        .put("deviceBrand", DeviceUtils.getManufacturer())
                                        .put("deviceModel", DeviceUtils.getModel())
                                        .put("phoneNo", phoneText)
                                        .put("iMei", StringUtils.isEmpty(PhoneUtils.getIMEI()) ? "not_obtained" : PhoneUtils.getIMEI())
                                        .put("batteryStatus", 1)
                                        .put("diskSize", "128gb")
                                        .put("memorySize", "8gb")
                                        .put("cpu", "高通")
                                        .put("bluetoothMac", DeviceUtils.getMacAddress())
                                        .put("wifiMac", DeviceUtils.getMacAddress())
                                        .put("cameraStatus", 0)
                                        .put("workLong", ConstantsBase.systemBootTimeByMinute())
                                        .put("iMsi", StringUtils.isEmpty(PhoneUtils.getIMSI()) ? "86" : PhoneUtils.getIMSI())
                                        .put("systemEm", 0)
                                        .build())
                                        .compose(bindToLifecycle())
                                        .subscribe(new SlcObserver<Object>(SlcCallbackConfig.newBuilder()
                                                .setToastRes(R.string.user_label_device_registration_failed)
                                                .setDialogMsg(R.string.user_label_registering_device).build()) {

                                            @Override
                                            protected void onSucceed(Object data) {
                                                showRegisterDeviceSucceed();
                                            }

                                            @Override
                                            protected void onFailed(int errorCode, String errorMessage) {
                                                ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        AppUtils.exitApp();
                                                    }
                                                }, 2000);
                                            }
                                        });
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                finish();
                                break;
                        }
                    }
                }).create().show();
    }

    private void showRegisterDeviceSucceed() {
        new SlcPopup.NativeAlertDialogBuilder(getViewDelegate().getActivityContext())
                .setTitle(R.string.label_prompt)
                .setMessage(R.string.user_label_dialog_hint_registered_device_successfully)
                .setPositiveButton(R.string.action_i_know)
                .setCancelable(false)
                .setOnClickListener((dialog, which) -> {
                    dialog.dismiss();
                    AppUtils.exitApp();
                })
                .create().show();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        ThreadUtils.getMainHandler().removeCallbacksAndMessages(null);
    }
}
