package android.slc.appbase.ui.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.slc.code.ui.activity.MvvmActivity;
import android.slc.code.vm.BaseViewModel;
import android.slc.commonlibrary.util.compat.SlcBarCompatUtils;
import android.slc.toolbar.ISlcToolBarDelegate;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

public abstract class AppMvvmBaseActivity<V extends ViewDataBinding,VM extends BaseViewModel> extends MvvmActivity<V,VM> implements NavigationCallback {
    @Override
    protected void initViewBefore() {
        super.initViewBefore();
    }

    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        KeyboardUtils.fixAndroidBug5497(this);
    }

    @Override
    protected boolean initBarStyle() {
        return true;
    }

    @Override
    protected void syncBarStyle() {
        super.syncBarStyle();
        SlcBarCompatUtils.setStatusBarColor(this, Color.WHITE);
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
