package android.slc.appbase.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.slc.appbase.R;
import android.slc.appbase.data.config.ConstantsBase;
import android.slc.appbase.databinding.AppActivityWebViewBinding;
import android.slc.appbase.databinding.AppFragmentWebViewBinding;
import android.slc.appbase.ui.activity.base.AppMvvmBaseToolBarActivity;
import android.slc.appbase.ui.fragment.base.AppMvvmBaseFragment;
import android.slc.appbase.vm.AppBaseViewModel;
import android.util.Xml;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.FileIOUtils;

import java.nio.charset.StandardCharsets;

public class GlobalWebViewFragment extends AppMvvmBaseFragment<AppFragmentWebViewBinding, AppBaseViewModel> {
    private String url;
    private boolean isLocalHtml;

    @Override
    protected void initViewBefore() {
        super.initViewBefore();
        Bundle bundle = getArguments();
        url = bundle.getString(ConstantsBase.Key.KEY_INTENT_DATA);
        isLocalHtml = bundle.getBoolean(ConstantsBase.Key.KEY_LOCAL_HTML);
    }

    @Override
    protected void bindingVariable() {

    }

    @Override
    protected Object setContentView() {
        return R.layout.app_fragment_web_view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        WebSettings settings = dataBinding.webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        //自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //自动缩放
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);
        //支持获取手势焦点
        dataBinding.webView.requestFocusFromTouch();
        dataBinding.webView.setWebViewClient(new WebViewClient());
        if (isLocalHtml) {
            dataBinding.webView.loadData(url, "text/html", "UTF-8");
        } else {
            dataBinding.webView.loadUrl(url);
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (dataBinding.webView.canGoBack()) {
            dataBinding.webView.goBack();
            return true;
        } else {
            return super.onBackPressedSupport();
        }
    }

}
