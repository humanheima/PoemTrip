package com.brotherd.poemtrip.util;

import android.content.Context;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class Toast {

    private static android.widget.Toast toast;

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = android.widget.Toast.makeText(context,
                    content,
                    android.widget.Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
