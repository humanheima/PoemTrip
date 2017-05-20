package com.brotherd.poemtrip.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.brotherd.poemtrip.util.FontManager;

/**
 * Created by dumingwei on 2017/5/8.
 */
public class CustomFontTextView extends AppCompatTextView {

    public CustomFontTextView(Context context) {
        this(context, null);
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontManager.getTypeface(context);
        setTypeface(customFont);
    }
}
