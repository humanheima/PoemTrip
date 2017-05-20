package com.brotherd.poemtrip;

import android.app.Application;

import com.brotherd.poemtrip.util.FontManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class App extends Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        instance = this;
        FontManager.init(this);
    }

    public static Application getInstance() {
        return instance;
    }
}
