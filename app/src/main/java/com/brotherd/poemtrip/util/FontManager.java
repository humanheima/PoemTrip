package com.brotherd.poemtrip.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by dumingwei on 2017/5/8.
 */
public class FontManager {

    private static Typeface typeface;

    public static void init(Context context) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fzqk.ttf");
    }

    public static Typeface getTypeface(Context context) {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fzqk.ttf");
        }
        return typeface;
    }

    public static void changeFonts(TextView textView) {
        textView.setTypeface(typeface);
    }

    public static void changeFonts(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (child instanceof Button) {
                ((Button) child).setTypeface(typeface);
            } else if (child instanceof EditText) {
                ((EditText) child).setTypeface(typeface);
            } else if (child instanceof TextView) {
                ((TextView) child).setTypeface(typeface);
            }
        }
    }

}
