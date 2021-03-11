package android.slc.user.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.slc.attachment.bean.Progress;
import android.slc.appbase.data.entity.AppVersions;
import android.slc.network.SimpleDownloadByOk;
import android.slc.network.SimpleDownloadByOkListener;
import android.slc.user.R;
import android.slc.widget.RectProgressView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PathUtils;

import android.slc.appbase.data.api.ApiConfig;
import android.slc.user.data.config.ConstantsUser;

import java.io.File;
import java.text.DecimalFormat;

@Route(path = ConstantsUser.Path.PATH_UP_DATA_ACTIVITY)
public class UpDataActivity extends Activity implements View.OnClickListener {
    private DecimalFormat df = new DecimalFormat("#.##");
    private TextView tv_title;
    private LinearLayout negative_parent;
    private LinearLayout positive_parent;
    private Button btn_negative;
    private RectProgressView btn_positive;
    private TextView tv_msg;
    private AppVersions upDate;
    private int state;
    private File appFile;

    public static void showUpDataActivity(Context context, AppVersions tbAppVersions) {
        Intent intent = new Intent(context, UpDataActivity.class);
        intent.putExtra(ConstantsUser.Key.KEY_UP_DATE, tbAppVersions);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dialog_update);
        setFinishOnTouchOutside(false);
        initView();
        initData();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        negative_parent = findViewById(R.id.negative_parent);
        btn_negative = findViewById(R.id.btn_negative);
        btn_negative.setOnClickListener(this);
        btn_positive = findViewById(R.id.btn_positive);
        btn_positive.setOnClickListener(this);
        tv_title = findViewById(R.id.tv_title);
        tv_msg = findViewById(R.id.tv_msg);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        layoutParams.width = screenWidth - screenWidth / 6;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(layoutParams);
    }

    private void initData() {
        upDate = (AppVersions) getIntent().getSerializableExtra(ConstantsUser.Key.KEY_UP_DATE);
        if (upDate.isForceUpdate()) {
            negative_parent.setVisibility(View.GONE);
        }
        tv_title.setText(R.string.title_the_latest_version);
        tv_msg.setText(upDate.getUpdateContent().replace("\\n", "\n"));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_negative) {
            finish();
        } else if (id == R.id.btn_positive) {
            switch (state) {
                case Progress.NONE:
                    negative_parent.setVisibility(View.GONE);
                    tv_title.setText(R.string.title_be_updating);
                    btn_positive.setText(getString(R.string.action_get_the_installation_package));
                    checkUpDate();
                    break;
                case Progress.FINISH:
                    //TODO 安装
                    AppUtils.installApp(appFile);
                    break;
            }
        }
    }

    private void checkUpDate() {
        SimpleDownloadByOk.newBuilder(ApiConfig.BASE_URL + "file/download?id=" + upDate.getFileId(), PathUtils.getExternalAppDownloadPath(), "app-release.apk")
                .toCommonlyConfig()
                .build()
                .getDownloadTask().enqueue(new SimpleDownloadByOkListener() {
            @Override
            protected void started(@NonNull com.liulishuo.okdownload.DownloadTask task) {
            }

            @Override
            protected void progress(@NonNull com.liulishuo.okdownload.DownloadTask task, int percentage, long currentOffset, long totalLength) {
                state = Progress.LOADING;
                btn_positive.setProgress(percentage);
                btn_positive.setText(getString(R.string.label_completed_size, df.format(percentage) + "%"));
            }

            @Override
            protected void completed(@NonNull com.liulishuo.okdownload.DownloadTask task) {
                state = Progress.FINISH;
                appFile = task.getFile();
                btn_positive.setProgress(btn_positive.getMax());
                btn_positive.setText(getString(R.string.action_install_now));
                AppUtils.installApp(appFile);
            }

            @Override
            protected void error(@NonNull com.liulishuo.okdownload.DownloadTask task, @NonNull Exception e) {
                state = Progress.NONE;
                btn_positive.setProgress(-1);
                btn_positive.setText(getString(R.string.action_download_on_error_click_retry));
            }

        });
    }

    @Override
    public void onBackPressed() {
        if (upDate == null || !upDate.isForceUpdate()) {
            super.onBackPressed();
        }
    }
}
