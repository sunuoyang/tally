package com.mnnyang.tallybook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by mnnyang on 17-5-17.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public FragmentAdapter setTitles(String[] titles) {
        this.titles = titles;
        return this;
    }

    private String titles[];

    public FragmentAdapter addFragment(Fragment fragment) {
        fragments.add(fragment);
        return this;
    }

    public FragmentAdapter setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
        return this;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
