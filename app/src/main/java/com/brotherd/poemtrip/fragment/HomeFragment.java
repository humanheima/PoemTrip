package com.brotherd.poemtrip.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.adapter.ViewPagerAdapter;
import com.brotherd.poemtrip.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private BaseFragment hotPoemFragment;
    private ViewPagerAdapter adapter;
    private List<BaseFragment> fragmentList;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        fragmentList = new ArrayList<>();
        hotPoemFragment = HotPoemFragment.newInstance();
        fragmentList.add(hotPoemFragment);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
    }

}
