package com.brotherd.poemtrip.network.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class LoginEntity {

    public static Map<String, Object> getParams(String phone, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);
        return params;
    }
}
