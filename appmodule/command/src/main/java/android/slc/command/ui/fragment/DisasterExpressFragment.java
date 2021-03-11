package android.slc.command.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.slc.appbase.ui.fragment.base.AppMvvmBaseFragment;
import android.slc.command.R;
import android.slc.command.databinding.CommandFragmentDisasterExpressBinding;
import android.slc.command.ui.fragment.de.DisasterExpressChildFragment;
import android.slc.command.ui.fragment.de.MyImageFragment;
import android.slc.command.vm.DisasterExpressVm;
import android.slc.commonlibrary.util.compat.SlcBarCompatUtils;
import android.slc.widget.SlcClickProxy;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class DisasterExpressFragment extends AppMvvmBaseFragment<CommandFragmentDisasterExpressBinding, DisasterExpressVm> {
    private List<Fragment> fragmentList = new ArrayList<>();
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
        initTable();
        bindTablePager();
        initFragment();
        bindPager();
    }

    /**
     * 初始化索引和table
     */
    protected void initTable() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivityContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setSmoothScroll(true);
        String[] titleArray = StringUtils.getStringArray(R.array.comm_disaster_express_array);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleArray.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                SimplePagerTitleView homeQueryPagerTitleView = new SimplePagerTitleView(context);
                homeQueryPagerTitleView.setSelectedColor(ColorUtils.getColor(R.color.colorAccent));
                homeQueryPagerTitleView.setNormalColor(ColorUtils.getColor(R.color.androidTextColorSecondary));
                homeQueryPagerTitleView.setTextSize(16);
                homeQueryPagerTitleView.setTag(index);
                homeQueryPagerTitleView.setText(titleArray[index]);
                homeQueryPagerTitleView.setBackgroundResource(R.drawable.bg_action_color_transparent);
                homeQueryPagerTitleView.setOnClickListener(new SlcClickProxy(v -> {
                    dataBinding.viewPager.setCurrentItem(index);
                }));
                return homeQueryPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(ColorUtils.getColor(R.color.colorAccent));
                indicator.setLineHeight(SizeUtils.dp2px(2));
                return indicator;
            }
        });
        dataBinding.magicIndicator.setNavigator(commonNavigator);
    }

    /**
     * 绑定索引和viewpager
     */
    protected void bindTablePager() {
        ViewPagerHelper.bind(dataBinding.magicIndicator, dataBinding.viewPager);
    }

    /**
     * 初始化fragment
     */
    protected void initFragment() {
        fragmentList.add(new DisasterExpressChildFragment());
        fragmentList.add(new MyImageFragment());
    }

    /**
     * 绑定pager
     */
    protected void bindPager() {
        dataBinding.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        SlcBarCompatUtils.setStatusBarColor(getActivity(), Color.WHITE);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        private FragmentManager fm;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public Fragment instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            fm.beginTransaction().show(fragment).commit();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Fragment fragment = fragmentList.get(position);
            fm.beginTransaction().hide(fragment).commit();
        }
    }
}
