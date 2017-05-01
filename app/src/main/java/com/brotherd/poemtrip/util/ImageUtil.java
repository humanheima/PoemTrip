package com.brotherd.poemtrip.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by dumingwei on 2017/5/1.
 */
public class ImageUtil {

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .into(imageView);

    }
}
