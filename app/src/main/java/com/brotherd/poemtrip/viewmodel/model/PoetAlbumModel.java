package com.brotherd.poemtrip.viewmodel.model;

import com.brotherd.poemtrip.bean.PoetAlbum;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dumingwei on 2017/11/24 0024.
 */

public class PoetAlbumModel {

    private long poetId;

    public PoetAlbumModel(long poetId) {
        this.poetId = poetId;
    }

    public Observable<PoetAlbum> getPoetAlbum() {
        return NetWork.getApi().getPoetAlbum(poetId)
                .flatMap(new Function<HttpResult<PoetAlbum>, Observable<PoetAlbum>>() {
                    @Override
                    public Observable<PoetAlbum> apply(@NonNull HttpResult<PoetAlbum> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}
