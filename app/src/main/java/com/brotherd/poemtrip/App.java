package com.brotherd.poemtrip;

import android.app.Application;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class App extends Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Application getInstance() {
        return instance;
    }
}
