package android.slc.user.data.repository.local;

import android.content.Context;
import android.slc.code.ui.fragment.BaseFragment;
import android.slc.extras.user.config.ConstantsUser;
import android.slc.extras.user.repository.local.MyInfoFragmentService;
import android.slc.user.ui.fragment.MyInfoFragment;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * @author slc
 * @date 2021/1/12 17:07
 */
@Route(path = ConstantsUser.Path.PATH_MY_INFO_FRAGMENT_SERVICE)
public class MyInfoFragmentServiceImpl implements MyInfoFragmentService {
    @Override
    public void init(Context context) {

    }

    @Override
    public BaseFragment getFragment() {
        return new MyInfoFragment();
    }
}
