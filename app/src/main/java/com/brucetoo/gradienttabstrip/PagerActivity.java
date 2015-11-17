package com.brucetoo.gradienttabstrip;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

/**
 * Created by Bruce Too
 * On 8/19/15.
 * At 18:43
 */
public class PagerActivity extends FragmentActivity {

    private PagerSlidingTabStrip strip;
    private ViewPager pager;
    private SimpleAdapter adapter;
    private String[] strs = new String[]{"WHAT","THE","FUCK","DAY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        strip = (PagerSlidingTabStrip) findViewById(R.id.strip);
        pager = (ViewPager) findViewById(R.id.pager);

        adapter = new SimpleAdapter(getSupportFragmentManager());

        //tab text color
        strip.setTextColor(Color.parseColor("#b0f5ff"));
        //tab chose text color
        strip.setTabChoseTextColor(Color.parseColor("#ffffff"));
        //tab text size
        strip.setTextSize(13);
        //tab chose text size
        strip.setTabChoseTextSize(17);
        //indicator color
        strip.setIndicatorColor(Color.parseColor("#ffffff"));
        //indicator height
        strip.setIndicatorHeight(4);
        //underline height
        strip.setUnderlineHeight(1);
        //expand?
        strip.setShouldExpand(true);
        //divider between tab
        strip.setDividerColor(android.R.color.transparent);

        strip.setBackgroundColor(Color.parseColor("#24d2ea"));

        pager.setAdapter(adapter);
        strip.setViewPager(pager);
    }

    private int sp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, getResources().getDisplayMetrics());
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
