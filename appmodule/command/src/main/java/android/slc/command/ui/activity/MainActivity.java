package android.slc.command.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.slc.appbase.ui.activity.base.AppMvvmBaseActivity;
import android.slc.code.ui.fragment.BaseFragment;
import android.slc.command.R;
import android.slc.command.data.config.ConstantsCommand;
import android.slc.command.databinding.CommandActivityMainBinding;
import android.slc.command.ui.fragment.CommonFunctionsFragment;
import android.slc.command.ui.fragment.DisasterExpressFragment;
import android.slc.command.ui.fragment.ImageResourceFragment;
import android.slc.command.vm.MainVm;
import android.slc.extras.user.repository.local.MyInfoFragmentService;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ClickUtils;

@Route(path = ConstantsCommand.Path.PATH_MAIN)
public class MainActivity extends AppMvvmBaseActivity<CommandActivityMainBinding, MainVm> {
    private BaseFragment disasterExpressFragment, imageResourceFragment, commonFunctionsFragment, myInfoFragment, currentFragment;

    @Override
    protected void bindingVariable() {
        dataBinding.setVm(viewModel);
    }

    @Override
    protected Object setContentView() {
        return R.layout.command_activity_main;
    }

    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        disasterExpressFragment = new DisasterExpressFragment();
        imageResourceFragment = new ImageResourceFragment();
        commonFunctionsFragment = new CommonFunctionsFragment();
        myInfoFragment = ARouter.getInstance().navigation(MyInfoFragmentService.class).getFragment();
        loadMultipleRootFragment(R.id.fl_content, 0,
                disasterExpressFragment,
                imageResourceFragment,
                commonFunctionsFragment,
                myInfoFragment);
        dataBinding.rgSelect.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_disaster_express) {
                showHideFragment(disasterExpressFragment, currentFragment);
                currentFragment = disasterExpressFragment;
            } else if (checkedId == R.id.rb_image_resource) {
                showHideFragment(imageResourceFragment, currentFragment);
                currentFragment = imageResourceFragment;
            } else if (checkedId == R.id.rb_common_functions) {
                showHideFragment(commonFunctionsFragment, currentFragment);
                currentFragment = commonFunctionsFragment;
            } else if (checkedId == R.id.rb_mine) {
                showHideFragment(myInfoFragment, currentFragment);
                currentFragment = myInfoFragment;
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getActivityContext().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getActivityContext().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                dataBinding.bottomBar.setVisibility(View.GONE);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getActivityContext().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getActivityContext().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                dataBinding.bottomBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onBackPressedSupport() {
        ClickUtils.back2HomeFriendly("再次点击退出");
    }

}
