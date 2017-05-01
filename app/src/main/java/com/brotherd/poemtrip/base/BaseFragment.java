package com.brotherd.poemtrip.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dumingwei on 2017/5/1.
 */
public abstract class BaseFragment extends Fragment {

    protected Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(bindLayout(), container, false);
        unbinder=ButterKnife.bind(this, view);
        initData();
        bindEvent();
        return view;
    }

    protected abstract int bindLayout();

    protected abstract void initData();

    protected void bindEvent() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
