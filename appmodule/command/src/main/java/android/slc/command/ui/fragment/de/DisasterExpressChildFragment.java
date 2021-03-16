package android.slc.command.ui.fragment.de;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.slc.appbase.ui.fragment.base.AppMvvmBaseFragment;
import android.slc.appbase.vm.GlobalDataVm;
import android.slc.command.R;
import android.slc.command.databinding.CommandFragmentDisasterExpressChildBinding;
import android.slc.command.vm.DisasterExpressVm;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.slc.appdatabase.user.User;

import java.util.LinkedList;

public class DisasterExpressChildFragment extends AppMvvmBaseFragment<CommandFragmentDisasterExpressChildBinding, DisasterExpressVm> {

    private FrameLayout fl_video;    // 用来显示视频的布局

    private LinkedList<WebView> pages;

    @Override
    protected void bindingVariable() {

    }

    @Override
    protected Object setContentView() {
        return R.layout.command_fragment_disaster_express_child;
    }

    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        fl_video = dataBinding.flVideo;
        pages = new LinkedList<>();

        User user = GlobalDataVm.getInstance().getUser();
        String url = "http://101.132.144.109:8085/mobile/#/index?mobileId=" + user.getDeviceId();

        loadInNewPage(url);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (pages.size() > 0) {
            WebView webView = pages.removeLast();
            if (webView != null)
                webView.destroy();
            showLastPage();
            return true;
        } else {
            return super.onBackPressedSupport();
        }

    }

    /**
     * 在新标签页加载页面
     */
    private void loadInNewPage(String url) {
        WebView webView = new WebView(getActivityContext());
        initWebSettings(webView);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("client.html")) {
                    loadInNewPage(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //忽略证书
                handler.proceed();
            }

            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
            }
        });
        webView.loadUrl(url);
        pages.add(webView);
        showLastPage();
    }

    private void showLastPage() {
        dataBinding.flWebView.removeAllViews();
        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        dataBinding.flWebView.addView(pages.getLast(), p);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSettings(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        settings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        settings.setAllowFileAccessFromFileURLs(false);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        settings.setAllowUniversalAccessFromFileURLs(false);
        //开启JavaScript支持
        settings.setJavaScriptEnabled(true);
        // 支持缩放
        settings.setSupportZoom(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
    }

    class MyWebChromeClient extends WebChromeClient {

        private View mCustomView;    //用于全屏渲染视频的View
        private WebChromeClient.CustomViewCallback mCustomViewCallback;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            //如果view 已经存在，则隐藏
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }

            mCustomView = view;
            mCustomView.setVisibility(View.VISIBLE);
            mCustomViewCallback = callback;
            fl_video.addView(view);
            fl_video.setVisibility(View.VISIBLE);
            fl_video.bringToFront();
            //设置横屏
            getActivityContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(View.GONE);
            fl_video.removeView(mCustomView);
            mCustomView = null;
            fl_video.setVisibility(View.GONE);
            mCustomViewCallback.onCustomViewHidden();
            getActivityContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        }
    }

}
