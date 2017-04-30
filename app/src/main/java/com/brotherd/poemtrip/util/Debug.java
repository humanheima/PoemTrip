package com.brotherd.poemtrip.util;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class Debug {

    private static int DEBUG_LEVEL = 2;
    private static int INFO_LEVEL = 3;
    private static int ERROR_LEVEL = 4;

    private static int OUTPUT_LEVEL = 5;

    public static void d(String tag, String message) {
        if (OUTPUT_LEVEL > DEBUG_LEVEL) {
            android.util.Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (OUTPUT_LEVEL > DEBUG_LEVEL) {
            android.util.Log.i(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (OUTPUT_LEVEL > DEBUG_LEVEL) {
            android.util.Log.e(tag, message);
        }
    }
}
