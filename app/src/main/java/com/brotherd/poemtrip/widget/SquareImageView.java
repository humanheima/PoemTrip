package com.brotherd.poemtrip.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by dumingwei on 2017/5/6.
 */
public class SquareImageView extends ImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //double goldRatioHeight = widthMeasureSpec * 1.61;
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
