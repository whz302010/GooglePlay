package com.example.acer_pc.googleplay.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.acer_pc.googleplay.R;
import com.example.acer_pc.googleplay.fragment.BaseFragment;
import com.example.acer_pc.googleplay.fragment.FragmentFactory;
import com.example.acer_pc.googleplay.utils.UIUtils;
import com.example.acer_pc.googleplay.view.PagerTab;

public class MainActivity extends BaseActivity {

    private ViewPager mViewpager;
    private PagerTab mPagerTab;
    private String[] mTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mTitleList= UIUtils.getStringArray(R.array.tab_names);

        mPagerTab.setViewPager(mViewpager);
        mViewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));

mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//加载数据
        BaseFragment fragment = FragmentFactory.creatFragment(position);
        fragment.loadData();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList[position];
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.creatFragment(position);
        }

        @Override
        public int getCount() {
            return mTitleList.length;
        }
    }
}
