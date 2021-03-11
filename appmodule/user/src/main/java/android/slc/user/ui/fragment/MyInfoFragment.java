package android.slc.user.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.slc.appbase.ui.fragment.base.AppMvvmBaseFragment;
import android.slc.appbase.ui.fragment.main.MainBaseFragment;
import android.slc.appbase.utils.GlideUtils;
import android.slc.appbase.utils.UserAvatarTarget;
import android.slc.commonlibrary.util.compat.SlcBarCompatUtils;
import android.slc.user.R;
import android.slc.user.databinding.UserFragmentMyInfoBinding;
import android.slc.user.vm.MyInfoVm;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.slc.appdatabase.user.User;

/**
 * @author slc
 * @date 2021/1/12 16:51
 */
public class MyInfoFragment extends AppMvvmBaseFragment<UserFragmentMyInfoBinding, MyInfoVm> {
    @Override
    protected void bindingVariable() {
        dataBinding.setVm(viewModel);
    }

    @Override
    protected boolean barIsLight() {
        return false;
    }

    @Override
    protected void registerLiveEvent() {
        super.registerLiveEvent();
        viewModel.userOf.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                User user = viewModel.userOf.get();
                if (user != null) {
                    dataBinding.tvIcon.setText(StringUtils.isEmpty(user.getUserName()) ? "" : user.getUserName().substring(0, 1));
                    Glide.with(MyInfoFragment.this).load(user.getAvatar()).apply(GlideUtils.getHeadRequestOptions())
                            .into(new UserAvatarTarget(dataBinding.ivIcon, dataBinding.tvIcon));
                }
            }
        });
    }

    @Override
    protected Object setContentView() {
        return R.layout.user_fragment_my_info;
    }

    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        /// getAppToolBarDelegate().setBarTitle(R.string.user_label_mine);
    }
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mBaseActivityDelegate != null) {
            mBaseActivityDelegate.setBarLightModel(barIsLight());//初始化状态栏风格
        }
        SlcBarCompatUtils.setStatusBarColor(getActivity(), ColorUtils.getColor(R.color.colorAccent));
    }
}
