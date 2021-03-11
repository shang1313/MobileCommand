package android.slc.command.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.slc.appbase.ui.fragment.base.AppMvvmBaseToolBarFragment;
import android.slc.appbase.utils.Escape;
import android.slc.appbase.vm.GlobalDataVm;
import android.slc.command.R;
import android.slc.command.databinding.CommandFragmentDisasterExpressBinding;
import android.slc.command.vm.DisasterExpressVm;
import android.slc.commonlibrary.util.compat.SlcBarCompatUtils;
import android.slc.slcdialog.SlcPopup;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.slc.appdatabase.user.User;

public class ImageResourceFragment extends AppMvvmBaseToolBarFragment<CommandFragmentDisasterExpressBinding, DisasterExpressVm> {
    @Override
    protected boolean barIsLight() {
        return true;
    }

    @Override
    protected void bindingVariable() {

    }

    @Override
    protected Object setContentView() {
        return R.layout.command_fragment_image_resource;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mSlcToolbar.setNavigationIcon(null);
        getAppToolBarDelegate().setBarTitle(R.string.comm_label_video_meeting);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        SlcBarCompatUtils.setStatusBarColor(getActivity(), Color.WHITE);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        WebView webView = findViewById(R.id.web_view);

        WebSettings settings = webView.getSettings();
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

        User user = GlobalDataVm.getInstance().getUser();
        String myUrl = "http://101.132.144.109:8085/temp/a.html?a=" + user.getDeviceId() + "&n=" + Escape.escape(user.getUserName());
        webView.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if ("https://m.ovmeet.com:3000/login.html".equals(url)) {
                    view.loadUrl(myUrl);
                } else
                    view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if ("https://m.ovmeet.com:3000/login.html".equals(url)) {
                    view.loadUrl(myUrl);
                } else
                    view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                ToastUtils.showShort(message);
                return false;
            }
        });
        LogUtils.d(user.getUserName(), user.getId(), user.getDeviceId());

        Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS).withListener(
                new CompositeMultiplePermissionsListener(new BaseMultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        super.onPermissionsChecked(report);
                        if (report.areAllPermissionsGranted()) {
                            webView.loadUrl(myUrl);
                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            new SlcPopup.NativeAlertDialogBuilder(getActivityContext())
                                    .setMessage(R.string.label_base_permission_video_meeting_hint)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.action_i_know)
                                    .setOnClickListener((dialog, which) -> {
                                        webView.loadUrl(myUrl);
                                    })
                                    .create().show();
                        }
                    }
                })).check();

    }


}
