package android.slc.appbase.ui.activity;

import android.os.Bundle;
import android.slc.appbase.ui.activity.base.AppMvvmBaseToolBarActivity;
import android.slc.appbase.vm.BaseCommonListShelfVm;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slc
 * @date 2020-08-25 15:25
 */
public abstract class BaseCommonListShelfActivity<V extends ViewDataBinding, VM extends BaseCommonListShelfVm> extends AppMvvmBaseToolBarActivity<V, VM> {
    protected List<Fragment> fragmentList = new ArrayList<>();

    /**
     * 获取viewpager
     *
     * @return
     */
    protected abstract ViewPager getViewPager();

    /**
     * 获取滑动的索引
     *
     * @return
     */
    protected abstract MagicIndicator getMagicIndicator();

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
    protected abstract void initTable();

    /**
     * 绑定索引和viewpager
     */
    protected void bindTablePager() {
        ViewPagerHelper.bind(getMagicIndicator(), getViewPager());
    }

    /**
     * 初始化fragment
     */
    protected abstract void initFragment();

    /**
     * 绑定pager
     */
    protected void bindPager() {
        getViewPager().setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
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
