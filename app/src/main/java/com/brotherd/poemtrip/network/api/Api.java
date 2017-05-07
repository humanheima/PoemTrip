package com.brotherd.poemtrip.network.api;

import com.brotherd.poemtrip.model.LoginModel;
import com.brotherd.poemtrip.model.PoemModel;
import com.brotherd.poemtrip.model.VerifyCodeModel;
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
    Observable<HttpResult<LoginModel>> login(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("/fastLogin")
    Observable<HttpResult<LoginModel>> fastLogin(@Field("phone") String phone, @Field("verifyCode") String password);

    @GET("/getLoginVerifyCode")
    Observable<HttpResult<VerifyCodeModel>> getLoginVerifyCode(@Query("phone") String phone);

    @GET("/getRegisterVerifyCode")
    Observable<HttpResult<VerifyCodeModel>> getRegisterVerifyCode(@Query("phone") String phone);

    @FormUrlEncoded
    @POST("/register")
    Observable<HttpResult<LoginModel>> register(@Field("phone") String phone, @Field("verifyCode") String verifyCode, @Field("password") String password);

    @POST("/login")
    Observable<HttpResult<Object>> getData(@Body Map map);

    @GET("/getPoem")
    Observable<HttpResult<PoemModel>> getPoem(@Query("poemId") long poemId);

    @GET("/getHotPoem")
    Observable<HttpResult<List<PoemModel>>> getHotPoem();
}
