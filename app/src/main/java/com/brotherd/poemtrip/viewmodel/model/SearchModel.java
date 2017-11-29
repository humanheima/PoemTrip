package com.brotherd.poemtrip.viewmodel.model;

import android.util.Log;

import com.brotherd.poemtrip.bean.SearchBean;
import com.brotherd.poemtrip.util.ListUtil;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public class SearchModel {

    private static final String TAG = "SearchModel";

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

    /**
     * 数据库里最多存储20条记录
     *
     * @return
     */
    public Observable<List<SearchBean>> getSearchHistory() {
        return Observable.create(new ObservableOnSubscribe<List<SearchBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SearchBean>> e) throws Exception {
                List<SearchBean> history = DataSupport.where().order("timeStamp").find(SearchBean.class);
                if (ListUtil.notEmpty(history)) {
                    e.onNext(history);
                    e.onComplete();
                } else {
                    e.onError(new Throwable("no history"));
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<SearchBean>> search(String searchContent) {
        saveSearchHistory(searchContent);
        List<SearchBean> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            SearchBean bean = new SearchBean();
            bean.setPoemId(i);
            bean.setPoetId(i);
            if (i % 3 == 0) {
                bean.setTitle("白居易<font color='#3bcc64'>诗人</font>" + i);
            } else {
                bean.setTitle("孟浩然<font color='#3bcc64'>诗人</font>" + i);
            }
            data.add(bean);
        }
        return Observable.just(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void saveSearchHistory(final String title) {
        Log.e(TAG, "saveSearchHistory");
        SearchBean bean = DataSupport.where("title = ?", title).findFirst(SearchBean.class);
        if (null != bean) {
            bean.setTimeStamp(System.currentTimeMillis());
            bean.setTitle(title);
            int row = bean.update(bean.getId());
            Log.e(TAG, "row=" + row);
        } else {
            bean = new SearchBean();
            bean.setTimeStamp(System.currentTimeMillis());
            bean.setTitle(title);
            boolean success = bean.save();
            Log.e(TAG, "success ?" + success);
        }
    }

    public void clearHistory() {
        DataSupport.deleteAll(SearchBean.class);
    }

}
