package com.brotherd.poemtrip.fragment;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseFragment;

public class MineFragment extends BaseFragment {

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {

    }

}
