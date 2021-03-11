package android.slc.extras.user.repository.local;

import android.slc.appbase.ui.fragment.main.MainBaseFragment;
import android.slc.code.ui.fragment.BaseFragment;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author slc
 * @date 2021/1/12 17:06
 */
public interface MyInfoFragmentService extends IProvider {
    BaseFragment getFragment();
}
