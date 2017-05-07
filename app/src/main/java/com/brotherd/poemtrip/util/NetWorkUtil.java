package com.brotherd.poemtrip.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.brotherd.poemtrip.App;

/**
 * Created by dumingwei on 2017/5/6.
 */
public class NetWorkUtil {

    public static boolean hasNetwork() {
        ConnectivityManager manager = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }
}
