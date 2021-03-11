package android.slc.command.ui.activity;

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

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ActivityUtils;

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
    public void onBackPressedSupport() {
        ActivityUtils.startHomeActivity();
    }

}
