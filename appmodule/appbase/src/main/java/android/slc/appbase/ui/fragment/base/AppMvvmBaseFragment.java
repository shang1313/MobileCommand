package android.slc.appbase.ui.fragment.base;

import android.os.Bundle;
import android.slc.code.ui.fragment.MvvmFragment;
import android.slc.code.vm.BaseViewModel;
import android.slc.toolbar.ISlcToolBarDelegate;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

public abstract class AppMvvmBaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends MvvmFragment<V, VM>
        implements NavigationCallback {

    protected void onBindView(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected boolean barIsLight() {
        return mBaseActivityDelegate.barIsLight();
    }

    protected void setText(@IdRes int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }

    protected void setText(@IdRes int id, @StringRes int stringRes) {
        ((TextView) findViewById(id)).setText(stringRes);
    }

    @Override
    protected ISlcToolBarDelegate initSlcToolBarDelegate() {
        return null;
    }

    @Override
    public void onFound(Postcard postcard) {
        LogUtils.iTag("onFound", "已找到");
    }

    @Override
    public void onLost(Postcard postcard) {
        ToastUtils.showShort("未找到activity");
        LogUtils.iTag("onLost", "未找到");
    }

    @Override
    public void onArrival(Postcard postcard) {
        LogUtils.iTag("onArrival", "已到达");
    }

    @Override
    public void onInterrupt(Postcard postcard) {
        ToastUtils.showShort("被中断");
    }

}
