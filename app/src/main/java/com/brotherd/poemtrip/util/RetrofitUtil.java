package com.brotherd.poemtrip.util;

import com.brotherd.poemtrip.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class RetrofitUtil {

    private final static String TAG = "RetrofitUtil";
    private static final Retrofit RETROFIT;

    static {

        OkHttpClient.Builder okHttpBuild = new OkHttpClient.Builder();

        okHttpBuild.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                Buffer buffer = new Buffer();
                Debug.e(TAG, request.method() + "-->" + request.url());
                if (request.body() != null) {
                    request.body().writeTo(buffer);
                }
                Debug.e(TAG, buffer.readUtf8());
                Debug.e(TAG, request.toString());
                if (response.body() != null) {
                    BufferedSource source = response.body().source();
                    source.request(Long.MAX_VALUE);
                    Debug.e(TAG, source.buffer().clone().readUtf8());
                } else {
                    Debug.e(TAG, "response body is null");
                }

                return response;
            }
        });

        OkHttpClient client = okHttpBuild.readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        RETROFIT = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private RetrofitUtil() {
    }

    public static <T> T create(Class<T> service) {
        return RETROFIT.create(service);
    }
}
