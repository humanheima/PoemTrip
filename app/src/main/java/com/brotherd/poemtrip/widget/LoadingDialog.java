package com.brotherd.poemtrip.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;

import com.brotherd.poemtrip.R;


/**
 * Created by dumingwei on 2017/4/21.
 */
public class LoadingDialog extends AlertDialog {

    private LoadingDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public LoadingDialog(Context context) {
        this(context, R.style.LoadingDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
    }

    @Override
    public void show() {
        super.show();
    }
}
