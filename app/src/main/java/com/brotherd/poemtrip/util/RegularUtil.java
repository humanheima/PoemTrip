package com.brotherd.poemtrip.util;

import android.text.TextUtils;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class RegularUtil {

    public static boolean checkPhone(String phone) {
        return phone.matches("1\\d{10}");
    }

    public static String getPlainString(String original) {
        if (TextUtils.isEmpty(original)) {
            return "";
        }
        return original.replaceAll("<br />", "\n").replaceAll("<[\\w/]+>", "");
    }
}
