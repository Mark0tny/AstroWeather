package com.kotu.astroweather;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


public class PagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }


    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SunFragment.newInstance("Sun Fragment", 0);
            case 1:
                return MoonFragment.newInstance("Moon Fragment", 1);
            case 2:
                return SettingsFragment.newInstance("Settings Fragment", 2);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getItem(position).getClass().getSimpleName();
    }

}

