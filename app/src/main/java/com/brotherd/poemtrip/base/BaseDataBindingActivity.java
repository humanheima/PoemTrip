package com.brotherd.poemtrip.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.brotherd.poemtrip.widget.LoadingDialog;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public abstract class BaseDataBindingActivity<V extends ViewDataBinding> extends AppCompatActivity {

    private LoadingDialog loadingDialog;
    protected V binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.show(this);
        } else if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

}
