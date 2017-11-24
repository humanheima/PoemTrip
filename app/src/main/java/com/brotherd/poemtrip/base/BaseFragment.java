package com.brotherd.poemtrip.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brotherd.poemtrip.widget.LoadingDialog;

/**
 * Created by dumingwei on 2017/5/1.
 */
public abstract class BaseFragment<V extends ViewDataBinding> extends Fragment {

    protected LoadingDialog loadingDialog;
    protected V binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        initData();
        bindEvent();
        return binding.getRoot();
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected void bindEvent() {

    }

    protected void showLoading() {
        ((BaseDataBindingActivity) getActivity()).showLoading();
    }

    protected void hideLoading() {
        ((BaseDataBindingActivity) getActivity()).hideLoading();
    }

}
