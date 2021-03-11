package android.slc.appbase.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.slc.appbase.R;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.appbase.databinding.AppActivityWebViewBinding;
import android.slc.appbase.ui.activity.base.AppMvvmBaseActivity;
import android.slc.appbase.ui.activity.base.AppMvvmBaseToolBarActivity;
import android.slc.appbase.ui.fragment.GlobalWebViewFragment;
import android.slc.appbase.vm.AppBaseViewModel;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class GlobalWebViewActivity extends AppMvvmBaseToolBarActivity<AppActivityWebViewBinding, AppBaseViewModel> {
    private String url;

    public static void show(Context context, String url) {
        Intent intent = new Intent(context, GlobalWebViewActivity.class);
        intent.putExtra(ConstantsBase.Key.KEY_INTENT_DATA, url);
        context.startActivity(intent);
    }

    public static void show(Context context, String url, String title) {
        Intent intent = new Intent(context, GlobalWebViewActivity.class);
        intent.putExtra(ConstantsBase.Key.KEY_INTENT_DATA, url);
        intent.putExtra(ConstantsBase.Key.KEY_TITLE_LABEL, title);
        context.startActivity(intent);
    }

    @Override
    protected void initViewBefore() {
        super.initViewBefore();
        url = getIntent().getStringExtra(ConstantsBase.Key.KEY_INTENT_DATA);
    }

    @Override
    protected void bindingVariable() {

    }

    @Override
    protected Object setContentView() {
        return R.layout.app_activity_web_view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        getAppToolBarDelegate().setBarTitle(getTitleByIntent(R.string.app_label_details));
        GlobalWebViewFragment globalWebViewFragment = new GlobalWebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantsBase.Key.KEY_INTENT_DATA, url);
        globalWebViewFragment.setArguments(bundle);
        loadRootFragment(R.id.fl_content, globalWebViewFragment);
    }
}
