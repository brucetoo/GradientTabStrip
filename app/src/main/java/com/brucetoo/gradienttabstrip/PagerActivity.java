package com.brucetoo.gradienttabstrip;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by Bruce Too
 * On 8/19/15.
 * At 18:43
 */
public class PagerActivity extends FragmentActivity {

    private PagerSlidingTabStrip strip;
    private ViewPager pager;
    private SimpleAdapter adapter;
    private String[] strs = new String[]{"首页","手游","端游","娱乐","首页","手游","端游","娱乐"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        strip = (PagerSlidingTabStrip) findViewById(R.id.strip);
        pager = (ViewPager) findViewById(R.id.pager);

        adapter = new SimpleAdapter(getSupportFragmentManager());

        //设置选中的颜色
        strip.setTextColor(Color.parseColor("#df322e"));
        //设置文本大小
        strip.setTextSize(40);
        //指示器颜色
        strip.setIndicatorColor(Color.parseColor("#df322e"));
        //指示器高度
        strip.setIndicatorHeight(4);
        //下划线高度
        strip.setUnderlineHeight(1);
        //拉伸？
        strip.setShouldExpand(true);
        //tab间的分割线 -- 透明表示没有
        strip.setDividerColor(android.R.color.transparent);

        pager.setAdapter(adapter);
        strip.setViewPager(pager);
    }

    class SimpleAdapter extends FragmentPagerAdapter {

        public SimpleAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new SingleFragment();
        }

        @Override
        public int getCount() {
            return strs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strs[position];
        }
    }
}
