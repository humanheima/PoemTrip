package com.brotherd.poemtrip.util;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class RegularUtil {

    public static boolean checkPhone(String phone) {
        return phone.matches("1\\d{10}");
    }
}
