package com.brotherd.poemtrip.util;

import android.content.SharedPreferences;

/**
 * Created by dumingwei on 2017/2/5.
 */
public class SpUtil {

    private static SpUtil spUtil;
    private static SharedPreferences hmSpref;
    private static final String SP_NAME = "yl_spref";
    private static SharedPreferences.Editor editor;

    private static final String USER_ID = "user_id";

    private static final String LOGIN_MODEL = "loginModel";
    private static final String USER_INFO = "userInfo";
    private static final String SELLER_INFO = "sellerInfo";
    //非教材单价
    private static final String NO_TEXTBOOK_PRICE = "noTextBookPrice";

    /*private SpUtil() {
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

    public void putLoginModel(LoginModel model) {
        editor.putString(LOGIN_MODEL, JsonUtil.toString(model));
        editor.commit();
    }

    public LoginModel getLoginModel() {
        return JsonUtil.toObject(hmSpref.getString(LOGIN_MODEL, null), LoginModel.class);
    }

    public void putUserInfo(UserInfoModel userInfo) {
        editor.putString(USER_INFO, JsonUtil.toString(userInfo));
        editor.commit();
    }

    public UserInfoModel getUserInfo() {
        return JsonUtil.toObject(hmSpref.getString(USER_INFO, null), UserInfoModel.class);
    }

    public void putSellerInfo(SellerInfoModel sellerInfo) {
        editor.putString(SELLER_INFO, JsonUtil.toString(sellerInfo));
        editor.commit();
    }

    public SellerInfoModel getSellerInfo() {
        return JsonUtil.toObject(hmSpref.getString(SELLER_INFO, null), SellerInfoModel.class);
    }

    public void putNoTextBookPriceModel(NoTextBookPriceModel noTextBookPriceModel) {
        editor.putString(NO_TEXTBOOK_PRICE, JsonUtil.toString(noTextBookPriceModel));
        editor.commit();
    }

    public NoTextBookPriceModel getNoTextPriceModel() {
        return JsonUtil.toObject(hmSpref.getString(NO_TEXTBOOK_PRICE, null), NoTextBookPriceModel.class);
    }
*/
    public void clear() {
        editor.clear();
        editor.commit();
    }
}
