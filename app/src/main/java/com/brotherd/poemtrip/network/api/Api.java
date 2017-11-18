package com.brotherd.poemtrip.network.api;

import com.brotherd.poemtrip.bean.LoginBean;
import com.brotherd.poemtrip.bean.PoemBean;
import com.brotherd.poemtrip.bean.PoetAlbum;
import com.brotherd.poemtrip.bean.PoetBean;
import com.brotherd.poemtrip.bean.VerifyCodeBean;
import com.brotherd.poemtrip.network.HttpResult;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dumingwei on 2017/4/30.
 */
public interface Api {

    @FormUrlEncoded
    @POST("/login")
    Observable<HttpResult<LoginBean>> login(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("/fastLogin")
    Observable<HttpResult<LoginBean>> fastLogin(@Field("phone") String phone, @Field("verifyCode") String password);

    @GET("/getLoginVerifyCode")
    Observable<HttpResult<VerifyCodeBean>> getLoginVerifyCode(@Query("phone") String phone);

    @GET("/getRegisterVerifyCode")
    Observable<HttpResult<VerifyCodeBean>> getRegisterVerifyCode(@Query("phone") String phone);

    @FormUrlEncoded
    @POST("/register")
    Observable<HttpResult<LoginBean>> register(@Field("phone") String phone, @Field("verifyCode") String verifyCode, @Field("password") String password);

    @POST("/login")
    Observable<HttpResult<Object>> getData(@Body Map map);

    @GET("/getPoem")
    Observable<HttpResult<PoemBean>> getPoem(@Query("poemId") long poemId);

    @GET("/getHotPoem")
    Observable<HttpResult<List<PoemBean>>> getHotPoem();

    @GET("/getHotPoet")
    Observable<HttpResult<List<PoetBean>>> getHotPoet();

    /**
     * 获取诗人的专辑
     *
     * @param poetId
     * @return
     */
    @GET("/getPoet")
    Observable<HttpResult<PoetAlbum>> getPoetAlbum(@Query("poetId") long poetId);
}
