package android.slc.appbase.ui.fragment.main;

import android.os.Bundle;
import android.slc.appbase.ui.fragment.base.AppMvvmBaseToolBarFragment;
import android.slc.code.vm.BaseViewModel;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

/**
 * @author slc
 * @date 2021/1/4 11:49
 */
public abstract class MainBaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends AppMvvmBaseToolBarFragment<V, VM> {
    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        mSlcToolbar.setNavigationIcon(null);
    }
}
