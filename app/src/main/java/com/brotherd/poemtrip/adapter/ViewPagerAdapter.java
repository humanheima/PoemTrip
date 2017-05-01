package com.brotherd.poemtrip.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.brotherd.poemtrip.base.BaseFragment;

import java.util.List;

/**
 * Created by dumingwei on 2017/5/1.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> fragmentList;

    public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
