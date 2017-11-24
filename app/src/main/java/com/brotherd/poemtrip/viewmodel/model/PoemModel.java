package com.brotherd.poemtrip.viewmodel.model;

import com.brotherd.poemtrip.activity.PoemActivity;
import com.brotherd.poemtrip.bean.PoemBean;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.Toast;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dumingwei on 2017/11/24 0024.
 */

public class PoemModel {

    private long poemId;

    public PoemModel(long poemId) {
        this.poemId = poemId;
    }

    public Observable<PoemBean> getPoem() {
        return NetWork.getApi().getPoem(poemId)
                .flatMap(new Function<HttpResult<PoemBean>, Observable<PoemBean>>() {
                    @Override
                    public Observable<PoemBean> apply(@NonNull HttpResult<PoemBean> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
