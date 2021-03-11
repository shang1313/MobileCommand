package android.slc.command.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.slc.appbase.ui.fragment.base.AppMvvmBaseToolBarFragment;
import android.slc.appbase.vm.GlobalDataVm;
import android.slc.command.R;
import android.slc.command.databinding.CommandFragmentDisasterExpressBinding;
import android.slc.command.vm.DisasterExpressVm;
import android.slc.commonlibrary.util.compat.SlcBarCompatUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.slc.appdatabase.user.User;

public class CommonFunctionsFragment extends AppMvvmBaseToolBarFragment<CommandFragmentDisasterExpressBinding, DisasterExpressVm> {
    @Override
    protected boolean barIsLight() {
        return true;
    }

    @Override
    protected void bindingVariable() {

    }

    @Override
    protected Object setContentView() {
        return R.layout.command_fragment_common_functions;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mSlcToolbar.setNavigationIcon(null);
        mSlcToolbar.setBackgroundColor(ColorUtils.getColor(R.color.colorPrimary));
        getAppToolBarDelegate().setBarTitle(R.string.comm_label_common_functions);
        getAppToolBarDelegate().getCenterTitle().setTextColor(Color.WHITE);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        WebView webView = findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.loadUrl("http://101.132.144.109:8085/speedtest-master/index.html");
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        SlcBarCompatUtils.setStatusBarColor(getActivity(), ColorUtils.getColor(R.color.colorPrimary));
        SlcBarCompatUtils.setStatusBarLightMode(getActivity(), false);
    }
}
