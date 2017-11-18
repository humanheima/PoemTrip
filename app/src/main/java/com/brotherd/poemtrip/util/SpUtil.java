package com.brotherd.poemtrip.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.brotherd.poemtrip.App;
import com.brotherd.poemtrip.bean.LoginBean;

/**
 * Created by dumingwei on 2017/2/5.
 */
public class SpUtil {

    private static SpUtil spUtil;
    private static SharedPreferences hmSpref;
    private static final String SP_NAME = "poemTripSpref";
    private static SharedPreferences.Editor editor;

    private static final String LOGIN_MODEL = "loginModel";

    private SpUtil() {
        hmSpref = App.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = hmSpref.edit();
    }

    public static SpUtil getInstance() {
        if (spUtil == null) {
            synchronized (SpUtil.class) {
                if (spUtil == null) {
                    spUtil = new SpUtil();
                }
            }
        }
        return spUtil;
    }

    public void putLoginModel(LoginBean model) {
        editor.putString(LOGIN_MODEL, GsonUtil.toString(model));
        editor.commit();
    }

    public LoginBean getLoginModel() {
        return GsonUtil.toObject(hmSpref.getString(LOGIN_MODEL, null), LoginBean.class);
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}
