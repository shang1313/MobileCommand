package android.slc.user.vm;

import android.app.Application;
import android.slc.appbase.data.api.main.callback.SlcObserver;
import android.slc.appbase.vm.AppBaseViewModel;
import android.slc.appbase.vm.GlobalDataVm;
import android.slc.code.ui.views.ViewDelegate;
import android.slc.extras.command.config.ConstantsCommand;
import android.slc.extras.user.entity.UserVo;
import android.slc.extras.user.repository.local.UserSp;
import android.slc.or.SlcCallbackConfig;
import android.slc.user.R;
import android.slc.user.data.repository.remote.UserServiceDelegate;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * @author slc
 * @date 2020-06-19 10:00
 */
public class LoginVm extends AppBaseViewModel {
    public ObservableBoolean isAutoLogin = new ObservableBoolean(UserSp.getIsAutoLogin());
    public ObservableBoolean isSavePassword = new ObservableBoolean(UserSp.getIsSavePassword());
    public ObservableField<String> userName = new ObservableField<>(UserSp.getCurrentUser());
    public ObservableField<String> password = new ObservableField<>(UserSp.getCurrentPassword());

    public LoginVm(@NonNull Application application) {
        super(application);
    }

    @Override
    public void initViewDelegate(ViewDelegate viewDelegate) {
        super.initViewDelegate(viewDelegate);
        isAutoLogin.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (isAutoLogin.get()) {
                    isSavePassword.set(true);
                }
            }
        });
    }

    public void login() {
        if (StringUtils.isEmpty(userName.get())) {
            ToastUtils.showShort(R.string.user_label_input_account);
            return;
        }
        if (StringUtils.isEmpty(password.get())) {
            ToastUtils.showShort(R.string.user_label_input_password);
            return;
        }
        UserServiceDelegate.login(userName.get(), password.get())
                .compose(getRxLifecycleDelegate().bindToLifecycle())
                .subscribe(new SlcObserver<UserVo>(SlcCallbackConfig.newBuilder()
                        .setDialogMsg(R.string.user_label_logging_in)
                        .setToastRes(R.string.user_toast_login_login_failed).build()) {
                    @Override
                    protected void onSucceed(UserVo data) {
                        UserSp.setAutoLogin(isAutoLogin.get());
                        UserSp.saveCurrentUser(userName.get());
                        UserSp.saveCurrentPassword(password.get());
                        UserSp.setSavePassword(isSavePassword.get());
                        GlobalDataVm.getInstance().userOf.set(data);
                        ToastUtils.showShort(R.string.user_toast_login_login_successful);
                        ARouter.getInstance().build(ConstantsCommand.Path.PATH_MAIN).navigation(getViewDelegate().getActivityContext());
                        finish();
                    }

                    @Override
                    protected void onFailed(int errorCode, String errorMessage) {
                    }
                });
    }

}
