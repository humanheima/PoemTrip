package com.brotherd.poemtrip.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.brotherd.poemtrip.widget.LoadingDialog;

import butterknife.ButterKnife;

/**
 * Created by dumingwei on 2017/4/30.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        ButterKnife.bind(this);
        initData();
        bindEvent();
    }

    protected abstract int bindLayout();

    protected abstract void initData();

    protected void bindEvent() {

    }

}
