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

    public LoadingDialog(Context context) {
        this(context, R.style.LoadingDialog);
    }

    private LoadingDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
    }

    public static LoadingDialog show(Context context) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.show();
        return loadingDialog;
    }

}
