package android.slc.appbase.ui.fragment.base;

import android.graphics.Color;
import android.os.Bundle;
import android.slc.appbase.ui.widget.AppToolBarDelegate;
import android.slc.code.vm.BaseViewModel;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.toolbar.ISlcToolBarDelegate;
import android.slc.toolbar.SlcToolBarDelegate;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;

import android.slc.appbase.R;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;

public abstract class AppMvvmBaseToolBarFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends AppMvvmBaseFragment<V, VM> {
    protected Toolbar mSlcToolbar;

    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        mSlcToolbar = mSlcToolBarDelegate.getToolBar();
        mSlcToolbar.setNavigationIcon(R.mipmap.ic_app_back);
        mSlcToolbar.setNavigationOnClickListener(l -> getActivityContext().onBackPressed());
        mSlcToolBarDelegate.getTitleTextView().setTextSize(18);
        mSlcToolBarDelegate.getSubTitleTextView().setTextSize(12);
        mSlcToolBarDelegate.setToolBarElevation(0);
        mSlcToolBarDelegate.getAppBarLayout().setBackgroundColor(Color.WHITE);
    }

    @Override
    protected ISlcToolBarDelegate initSlcToolBarDelegate() {
        return new AppToolBarDelegate.AppBuilder(getContentView()).build();
    }

    protected AppToolBarDelegate getAppToolBarDelegate() {
        return (AppToolBarDelegate) mSlcToolBarDelegate;
    }

    protected String getTitleByIntent(String title) {
        String titleByIntent = getTitleByIntent();
        if (!StringUtils.isEmpty(titleByIntent)) {
            title = titleByIntent;
        }
        return title;
    }

    protected String getTitleByIntent(@StringRes int titleId) {
        return getTitleByIntent(StringUtils.getString(titleId));
    }

    protected String getTitleByIntent() {
        return getArguments().getString(ConstantsBase.Key.KEY_TITLE_LABEL);
    }
}
