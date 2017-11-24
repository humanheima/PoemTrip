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
 * Created by dumingwei on 2017/11/23 0023.
 */

public class LoginModel {

    public Observable<VerifyCodeBean> getVerifyCode(String mobile) {
        return NetWork.getApi().getLoginVerifyCode(mobile)
                .flatMap(new Function<HttpResult<VerifyCodeBean>, Observable<VerifyCodeBean>>() {
                    @Override
                    public Observable<VerifyCodeBean> apply(@NonNull HttpResult<VerifyCodeBean> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LoginBean> accountLogin(String mobile, String password) {
        return NetWork.getApi().login(mobile, password)
                .flatMap(new Function<HttpResult<LoginBean>, ObservableSource<LoginBean>>() {
                    @Override
                    public ObservableSource<LoginBean> apply(@NonNull HttpResult<LoginBean> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LoginBean> fastLogin(String mobile, String verifyCode) {
        return NetWork.getApi().login(mobile, verifyCode)
                .flatMap(new Function<HttpResult<LoginBean>, ObservableSource<LoginBean>>() {
                    @Override
                    public ObservableSource<LoginBean> apply(@NonNull HttpResult<LoginBean> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
