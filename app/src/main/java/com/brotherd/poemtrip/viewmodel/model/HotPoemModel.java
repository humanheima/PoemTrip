package com.brotherd.poemtrip.viewmodel.model;

import com.brotherd.poemtrip.bean.PoemBean;
import com.brotherd.poemtrip.bean.PoetBean;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dumingwei on 2017/11/24 0024.
 */

public class HotPoemModel {

    public Observable<List<PoemBean>> getHotPoem() {
        return NetWork.getApi().getHotPoem()
                .flatMap(new Function<HttpResult<List<PoemBean>>, Observable<List<PoemBean>>>() {
                    @Override
                    public Observable<List<PoemBean>> apply(@NonNull HttpResult<List<PoemBean>> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<PoetBean>> getHotPoet() {
        return NetWork.getApi().getHotPoet()
                .flatMap(new Function<HttpResult<List<PoetBean>>, Observable<List<PoetBean>>>() {
                    @Override
                    public Observable<List<PoetBean>> apply(@NonNull HttpResult<List<PoetBean>> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
