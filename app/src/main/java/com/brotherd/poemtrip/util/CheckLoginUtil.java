package com.brotherd.poemtrip.util;

import com.brotherd.poemtrip.model.LoginModel;

/**
 * Created by dumingwei on 2017/5/20.
 */
public class CheckLoginUtil {

    /**
     * @return true 已登录 false 未登录
     */
    public static boolean haveLogin() {
        LoginModel model = SpUtil.getInstance().getLoginModel();
        return model != null;
    }
}
