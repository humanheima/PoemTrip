package com.brotherd.poemtrip.util;

import android.content.Context;
import android.widget.ImageView;

import com.brotherd.poemtrip.R;
import com.bumptech.glide.Glide;

/**
 * Created by dumingwei on 2017/5/1.
 */
public class ImageUtil {

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.ic_default_poet)
                .dontAnimate()
                .into(imageView);

    }

}
