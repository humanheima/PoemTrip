package com.brotherd.poemtrip.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;

/**
 * Created by dumingwei on 2016/11/24.
 */
public class ScreenUtil {

    private ScreenUtil() {
    }

    private static class Data {
        private int px;
        private float dp;

        private Data(int px, float density) {
            this.px = px;
            this.dp = px / density;
        }
    }

    /**
     * 获取屏幕
     */
    public static Display getDisplay(Context context) {
        Display d = ((Activity) context).getWindowManager().getDefaultDisplay();
        return d;
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    /**
     * 屏幕宽度
     */
    public static Data width(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        getDisplay(context).getMetrics(dm);
        return new Data(dm.widthPixels, dm.density);
    }

    /**
     * 屏幕宽度
     */
    public static int getWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        getDisplay(context).getMetrics(dm);
        return dm.widthPixels;
    }
}
