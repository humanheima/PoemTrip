package com.brotherd.poemtrip.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.brotherd.poemtrip.network.api.Api;
import com.brotherd.poemtrip.util.GsonUtil;
import com.brotherd.poemtrip.util.RetrofitUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class NetWork {

    private static Api api;

    public static Api getApi() {
        if (api == null) {
            api = RetrofitUtil.create(Api.class);
        }
        return api;
    }

    public static <T> Observable<T> getData(Map body, final Class<T> classType) {
        return getApi().getData(body)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<HttpResult<Object>, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(@NonNull HttpResult<Object> objectHttpResult) throws Exception {
                        return flatResponse(objectHttpResult);
                    }
                })
                .map(new Function<Object, T>() {
                    @Override
                    public T apply(@NonNull Object o) throws Exception {
                        String temp = GsonUtil.toString(o);
                        if (TextUtils.isEmpty(temp) || "[]".equals(temp) || "\"\"".equals(temp)) {
                            return null;
                        }
                        return GsonUtil.toObject(temp, classType);
                    }
                });
    }

    public static <T> Observable<List<T>> getDataList(Map body, final Class<T> classType) {
        return getApi().getData(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<HttpResult<Object>, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(@NonNull HttpResult<Object> objectHttpResult) throws Exception {
                        return flatResponse(objectHttpResult);
                    }
                })
                .map(new Function<Object, List<T>>() {
                    @Override
                    public List<T> apply(@NonNull Object o) throws Exception {
                        String temp = GsonUtil.toString(o);
                        if (TextUtils.isEmpty(temp) || "[]".equals(temp) || "\"\"".equals(temp)) {
                            return null;
                        }
                        return GsonUtil.toList(temp, classType);
                    }
                });
    }

    /**
     * 默认返回Object
     *
     * @param body
     * @return
     */
    public static Observable<Object> getData(Map body) {
        return getApi().getData(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<HttpResult<Object>, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(HttpResult<Object> objectHttpResult) throws Exception {
                        return flatResponse(objectHttpResult);
                    }
                });
    }

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public static <T> Observable<T> flatResponse(final HttpResult<T> response) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                if (response.isSuccess()) {
                    if (!e.isDisposed()) {
                        e.onNext(response.getBody());
                    }
                } else {
                    if (!e.isDisposed()) {
                        e.onError(new APIException(response.getRet(), response.getResultMessage()));
                    }
                    return;
                }
                if (!e.isDisposed()) {
                    e.onComplete();
                }
            }
        });
    }

    /**
     * 自定义异常，当接口返回的{link Response#code}不为{link Constant#RESULT_SUCCESS}时，需要跑出此异常
     * eg：登陆时验证码错误；参数为传递等
     */
    public static class APIException extends Exception {

        public int code;

        public String message;

        public APIException(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public int getCode() {
            return code;
        }
    }
}
