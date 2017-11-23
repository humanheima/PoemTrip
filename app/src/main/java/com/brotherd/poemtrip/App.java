package com.brotherd.poemtrip;

import android.app.Application;
import android.content.Context;

import com.brotherd.poemtrip.util.FontManager;
import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePal;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class App extends Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
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
