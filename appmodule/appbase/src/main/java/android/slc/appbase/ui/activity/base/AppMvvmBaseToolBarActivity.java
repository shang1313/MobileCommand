package android.slc.appbase.ui.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.appbase.ui.widget.AppToolBarDelegate;
import android.slc.toolbar.ISlcToolBarDelegate;
import android.slc.toolbar.SlcToolBarDelegate;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;

import android.slc.appbase.R;
import android.slc.appbase.vm.AppBaseViewModel;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;

public abstract class AppMvvmBaseToolBarActivity<V extends ViewDataBinding, VM extends AppBaseViewModel> extends AppMvvmBaseActivity<V, VM> {
    protected Toolbar mSlcToolbar;

    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        mSlcToolBarDelegate.setToolBarElevation(0);
        mSlcToolbar = mSlcToolBarDelegate.getToolBar();
        mSlcToolbar.setNavigationIcon(R.mipmap.ic_app_back);
        mSlcToolbar.setNavigationOnClickListener(l -> onBackPressed());
        mSlcToolBarDelegate.getTitleTextView().setTextSize(18);
        mSlcToolBarDelegate.getSubTitleTextView().setTextSize(12);
        mSlcToolBarDelegate.setToolBarElevation(0);
        mSlcToolBarDelegate.getAppBarLayout().setBackgroundColor(Color.WHITE);
    }

    @Override
    protected ISlcToolBarDelegate initSlcToolBarDelegate() {
        return new AppToolBarDelegate.AppBuilder(this).build();
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
        return getIntent().getStringExtra(ConstantsBase.Key.KEY_TITLE_LABEL);
    }
}
