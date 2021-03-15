package android.slc.command.ui.fragment.de;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.http.SslError;
import android.os.Bundle;
import android.slc.appbase.ui.fragment.base.AppMvvmBaseFragment;
import android.slc.command.R;
import android.slc.command.databinding.CommandFragmentDisasterExpressBinding;
import android.slc.command.vm.DisasterExpressVm;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ClientCertRequest;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

public class DisasterExpressChildFragment extends AppMvvmBaseFragment<CommandFragmentDisasterExpressBinding, DisasterExpressVm> {

    private FrameLayout mLayout;    // 用来显示视频的布局
    private View mCustomView;	//用于全屏渲染视频的View
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private WebView mWvContent;

    @Override
    protected void bindingVariable() {

    }

    @Override
    protected Object setContentView() {
        return R.layout.command_fragment_disaster_express_child;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        mLayout = findViewById(R.id.fl_video);
        mWvContent = findViewById(R.id.wv_content);

        WebSettings settings = mWvContent.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
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

        mWvContent.setWebChromeClient(new MyWebChromeClient());

        mWvContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //super.onReceivedSslError(view, handler, error);
                LogUtils.d(error);
                handler.proceed();
            }

            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
            }
        });

        mWvContent.loadUrl("http://101.132.144.109:8085/mobile/#/index");
        LogUtils.d("http://101.132.144.109:8085/mobile/#/index");
    }

    @Override
    public boolean onBackPressedSupport() {
        if (mWvContent.canGoBack()) {
            mWvContent.goBack();
            return true;
        } else {
            return super.onBackPressedSupport();
        }

    }

    class MyWebChromeClient extends WebChromeClient {

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
            mLayout.addView(mCustomView);
            mLayout.setVisibility(View.VISIBLE);
            mLayout.bringToFront();

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
            mLayout.removeView(mCustomView);
            mCustomView = null;
            mLayout.setVisibility(View.GONE);
            try {
                mCustomViewCallback.onCustomViewHidden();
            } catch (Exception e) {
            }
            getActivityContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //清空所有cookie
        CookieSyncManager.createInstance(getActivityContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        mWvContent.setWebChromeClient(null);
        mWvContent.setWebViewClient(null);
        mWvContent.getSettings().setJavaScriptEnabled(false);
        mWvContent.clearCache(true);
        mWvContent.destroy();
    }

}
