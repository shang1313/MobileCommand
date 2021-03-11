package android.slc.appbase.ui.widget;

import android.app.Activity;
import android.slc.appbase.R;
import android.slc.toolbar.ISlcToolBarDelegate;
import android.slc.toolbar.SlcToolBarDelegate;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.StringRes;

/**
 * 在SlcToolBarDelegate的基础上增加了居中的title
 */
public class AppToolBarDelegate extends SlcToolBarDelegate {
    private TextView centerTitle;

    public AppToolBarDelegate(View view, int appBarLayoutId, int toolbarId) {
        super(view, appBarLayoutId, toolbarId);
    }

    @Override
    protected void init(int appBarLayoutId, int toolbarId) {
        super.init(appBarLayoutId, toolbarId);
        centerTitle = mToolbar.findViewById(R.id.tv_my_title);
    }

    public TextView getCenterTitle() {
        return centerTitle;
    }

    public void setBarTitle(String title) {
        if (centerTitle != null) {
            centerTitle.setText(title);
        } else {
            mToolbar.setTitle(title);
        }
    }

    public void setBarTitle(@StringRes int titleRes) {
        if (centerTitle != null) {
            centerTitle.setText(titleRes);
        } else {
            mToolbar.setTitle(titleRes);
        }
    }

    public static class AppBuilder extends Builder<AppBuilder> {

        public AppBuilder(Activity activity) {
            super(activity);
        }

        public AppBuilder(View view) {
            super(view);
        }

        public ISlcToolBarDelegate build() {
            return new AppToolBarDelegate(mContentView, mAppBarLayoutId, mToolbarId);
        }
    }
}
