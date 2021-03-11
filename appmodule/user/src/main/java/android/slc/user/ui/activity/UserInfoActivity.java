package android.slc.user.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import android.slc.appbase.ui.activity.base.AppMvvmBaseToolBarActivity;
import android.slc.user.R;
import android.slc.user.data.config.ConstantsUser;
import android.slc.user.vm.UserInfoVm;
import android.slc.user.databinding.UserActivityUserInfoBinding;

/**
 * @author slc
 * @date 2020-07-28 15:47
 */
@Route(path = ConstantsUser.Path.PATH_USER_INFO)
public class UserInfoActivity extends AppMvvmBaseToolBarActivity<UserActivityUserInfoBinding, UserInfoVm> {
    @Override
    protected void bindingVariable() {
        dataBinding.setVm(viewModel);
    }

    @Override
    protected Object setContentView() {
        return R.layout.user_activity_user_info;
    }

    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        getAppToolBarDelegate().setBarTitle(R.string.app_label_personal_information);
        mSlcToolbar.inflateMenu(R.menu.user_menu_user_info);
        mSlcToolBarDelegate.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_pass_word) {
                viewModel.editPassWord();
            }
            return false;
        });
    }
}
