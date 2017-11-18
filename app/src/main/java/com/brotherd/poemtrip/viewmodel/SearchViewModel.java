package com.brotherd.poemtrip.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.bean.SearchBean;
import com.brotherd.poemtrip.util.ListUtil;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.viewmodel.model.SearchModel;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public class SearchViewModel extends BaseViewModel {

    public ObservableList<SearchBean> hotSearchData = new ObservableArrayList<>();
    public ObservableList<SearchBean> searchHistoryData = new ObservableArrayList<>();
    private SearchModel model;


    public SearchViewModel(BaseDataBindingActivity context, SearchModel model) {
        super(context);
        this.model = model;
    }

    public void getHotSearch() {
        model.getHotSearch()
                .subscribe(new Consumer<List<SearchBean>>() {
                    @Override
                    public void accept(List<SearchBean> searchBeans) throws Exception {
                        if (ListUtil.notEmpty(searchBeans)) {
                            hotSearchData.addAll(searchBeans);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.showToast(context, throwable.getMessage());
                    }
                });
    }

    public void getSearchHistory() {
        model.getSearchHistory()
                .subscribe(new Consumer<List<SearchBean>>() {
                    @Override
                    public void accept(List<SearchBean> searchBeans) throws Exception {
                        if (ListUtil.notEmpty(searchBeans)) {
                            searchHistoryData.addAll(searchBeans);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.showToast(context, throwable.getMessage());
                    }
                });
    }

}
