package android.slc.user.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;

import android.slc.extras.user.config.ConstantsUser;
import android.slc.appbase.ui.activity.base.AppMvvmBaseToolBarActivity;
import android.slc.user.R;
import android.slc.user.databinding.UserActivityLoginBinding;
import android.slc.user.vm.LoginVm;

/**
 * @author slc
 * @date 2020-06-19 9:59
 */
@Route(path = ConstantsUser.Path.PATH_LOGIN)
public class LoginActivity extends AppMvvmBaseToolBarActivity<UserActivityLoginBinding, LoginVm> {
    @Override
    protected void bindingVariable() {
        dataBinding.setVm(viewModel);
    }

    @Override
    protected Object setContentView() {
        return R.layout.user_activity_login;
    }

    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        mSlcToolbar.setNavigationIcon(null);
        getAppToolBarDelegate().setBarTitle(R.string.user_label_login);
    }

}
