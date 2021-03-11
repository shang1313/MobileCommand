package android.slc.user.ui.activity;

import android.slc.appbase.ui.activity.base.AppMvvmBaseActivity;
import android.slc.user.R;
import android.slc.user.databinding.UserActivityWelcomeBinding;
import android.slc.user.vm.WelcomeVm;

import com.blankj.utilcode.util.BarUtils;

/**
 * @author slc
 * @date 2020-07-10 11:09
 */
public class WelcomeActivity extends AppMvvmBaseActivity<UserActivityWelcomeBinding, WelcomeVm> {

    @Override
    protected Object setContentView() {
        return R.layout.user_activity_welcome;
    }

    @Override
    protected void bindingVariable() {
        dataBinding.setVm(viewModel);
    }

    @Override
    protected void initViewBefore() {
        super.initViewBefore();
        if (!isTaskRoot()) {
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        viewModel.checkPermission();
    }

    @Override
    protected void initViewLater() {
        super.initViewLater();
        viewModel.init();
    }

    /**
     * 同步syncBarStyle
     */
    @Override
    protected void syncBarStyle() {
    }
}
