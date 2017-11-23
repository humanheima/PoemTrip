package com.brotherd.poemtrip.viewmodel.model;

import com.brotherd.poemtrip.bean.LoginBean;
import com.brotherd.poemtrip.bean.VerifyCodeBean;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dumingwei on 2017/11/22 0022.
 */

public class RegisterModel {

    public Observable<VerifyCodeBean> getRegisterVerifyCode(String mobile) {
        return NetWork.getApi().getRegisterVerifyCode(mobile)
                .flatMap(new Function<HttpResult<VerifyCodeBean>, Observable<VerifyCodeBean>>() {
                    @Override
                    public Observable<VerifyCodeBean> apply(@NonNull HttpResult<VerifyCodeBean> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LoginBean> register(String mobile, String verifyCode, String password) {
        return NetWork.getApi().register(mobile, verifyCode, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<HttpResult<LoginBean>, Observable<LoginBean>>() {
                    @Override
                    public Observable<LoginBean> apply(@NonNull HttpResult<LoginBean> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                });
    }
}
