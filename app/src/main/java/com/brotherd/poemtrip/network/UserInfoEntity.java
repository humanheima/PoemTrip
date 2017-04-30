package com.brotherd.poemtrip.network;


import com.brotherd.poemtrip.Constant;
import com.brotherd.poemtrip.util.MD5Util;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息
 */
public class UserInfoEntity {

    private static final String INTERFACE_NAME = "user_info";

    public static Map<String, Object> getParam(String userid) {
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        String md5 = MD5Util.md5(INTERFACE_NAME
                .concat(userid)
                .concat(Constant.SECURITY));

        param.put("userid", userid);
        param.put("verify", md5);

        map.put("cmd", INTERFACE_NAME);
        map.put("params", param);
        return map;
    }
}
