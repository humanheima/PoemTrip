package com.brotherd.poemtrip.util;

import android.content.Context;

import com.brotherd.poemtrip.App;
import com.brotherd.poemtrip.R;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class Toast {

    private static android.widget.Toast toast;

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = android.widget.Toast.makeText(context.getApplicationContext(),
                    content,
                    android.widget.Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void showToast(String content) {
        if (toast == null) {
            toast = android.widget.Toast.makeText(App.getInstance(),
                    content,
                    android.widget.Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void showToast(int stringId) {
        if (toast == null) {
            toast = android.widget.Toast.makeText(App.getInstance(),
                    App.getInstance().getString(stringId),
                    android.widget.Toast.LENGTH_SHORT);
        } else {
            toast.setText(App.getInstance().getString(stringId));
        }
        toast.show();
    }
}
