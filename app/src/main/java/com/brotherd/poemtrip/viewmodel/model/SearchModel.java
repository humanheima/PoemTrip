package com.brotherd.poemtrip.viewmodel.model;

import com.brotherd.poemtrip.bean.SearchBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public class SearchModel {

    public Observable<List<SearchBean>> getHotSearch() {
        List<SearchBean> data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            SearchBean bean = new SearchBean();
            bean.setPoemId(i);
            bean.setPoetId(i);
            if (i % 3 == 0) {
                bean.setTitle("李白" + i);
            } else {
                bean.setTitle("杜甫" + i);
            }
            data.add(bean);
        }
        return Observable.just(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<SearchBean>> getSearchHistory() {
        List<SearchBean> data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            SearchBean bean = new SearchBean();
            bean.setPoemId(i);
            bean.setPoetId(i);
            if (i % 3 == 0) {
                bean.setTitle("白居易" + i);
            } else {
                bean.setTitle("孟浩然" + i);
            }
            data.add(bean);
        }
        return Observable.just(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
