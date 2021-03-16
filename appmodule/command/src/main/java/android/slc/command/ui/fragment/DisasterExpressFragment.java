package android.slc.command.ui.fragment;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.slc.appbase.ui.fragment.base.AppMvvmBaseFragment;
import android.slc.command.R;
import android.slc.command.databinding.CommandFragmentDisasterExpressBinding;
import android.slc.command.ui.fragment.de.DisasterExpressChildFragment;
import android.slc.command.ui.fragment.de.MyImageFragment;
import android.slc.command.vm.DisasterExpressVm;
import android.slc.commonlibrary.util.compat.SlcBarCompatUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

public class DisasterExpressFragment extends AppMvvmBaseFragment<CommandFragmentDisasterExpressBinding, DisasterExpressVm> {

    private DisasterExpressChildFragment disasterExpressChildFragment;
    private MyImageFragment myImageFragment;

    @Override
    protected boolean barIsLight() {
        return true;
    }

    @Override
    protected void bindingVariable() {
        dataBinding.setVm(viewModel);
    }

    @Override
    protected Object setContentView() {
        return R.layout.command_fragment_disaster_express;
    }

    @Override
    protected void onBindView(@Nullable Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
        initFragment();
        initTable();
    }

    /**
     * 初始化索引和table
     */
    protected void initTable() {
        dataBinding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getChildFragmentManager().beginTransaction().hide(myImageFragment).show(disasterExpressChildFragment).commit();
                } else {
                    getChildFragmentManager().beginTransaction().hide(disasterExpressChildFragment).show(myImageFragment).commit();
                    myImageFragment.reLoad();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 初始化fragment
     */
    protected void initFragment() {
        disasterExpressChildFragment = new DisasterExpressChildFragment();
        myImageFragment = new MyImageFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, disasterExpressChildFragment)
                .add(R.id.fragment_container, myImageFragment)
                .hide(myImageFragment)
                .show(disasterExpressChildFragment)
                .commit();
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        SlcBarCompatUtils.setStatusBarColor(getActivityContext(), Color.WHITE);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                dataBinding.tab.setVisibility(View.GONE);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                dataBinding.tab.setVisibility(View.VISIBLE);
                break;
        }
    }
}
