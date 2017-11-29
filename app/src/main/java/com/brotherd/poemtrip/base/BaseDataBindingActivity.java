package com.brotherd.poemtrip.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;

import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.widget.LoadingDialog;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public abstract class BaseDataBindingActivity<V extends ViewDataBinding> extends AppCompatActivity
        implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final String TAG = "BaseDataBindingActivity";
    private View activityRootView;
    private LoadingDialog loadingDialog;
    protected V binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        initData();
        bindEvent();
    }

    @Override
    public void onGlobalLayout() {
        if (isKeyboardShown(activityRootView)) {
            Debug.e(TAG, "软键盘弹起");
        } else {
            Debug.e(TAG, "软键盘关闭");
        }
    }

    private boolean isKeyboardShown(View rootView) {
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        //设定一个认为是软键盘弹起的阈值
        final int softKeyboardHeight = (int) (100 * dm.density);
        //得到屏幕可见区域的大小
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        //rootView 的bottom和当前屏幕可见区域（包括状态栏）bottom的差值
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight;
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected void bindEvent() {
    }

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

    @Override
    protected void onDestroy() {
        activityRootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        super.onDestroy();
    }
}
