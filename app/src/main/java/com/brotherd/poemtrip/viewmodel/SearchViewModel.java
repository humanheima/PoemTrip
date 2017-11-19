package com.brotherd.poemtrip.viewmodel;

import android.database.sqlite.SQLiteDatabase;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;

import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.bean.SearchBean;
import com.brotherd.poemtrip.util.ListUtil;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.viewmodel.model.SearchModel;

import org.litepal.LitePal;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public class SearchViewModel extends BaseViewModel {

    private static final String TAG = "SearchViewModel";
    public ObservableList<SearchBean> hotSearchData = new ObservableArrayList<>();
    public ObservableList<SearchBean> searchHistoryData = new ObservableArrayList<>();
    public ObservableList<SearchBean> searchResult = new ObservableArrayList<>();
    public ObservableField<String> searchContent = new ObservableField<>();
    public ObservableInt visibility = new ObservableInt(View.VISIBLE);
    public ObservableInt historyVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt rvVisibility = new ObservableInt(View.VISIBLE);
    private SearchModel model;

    public SearchViewModel(BaseDataBindingActivity context, SearchModel model) {
        super(context);
        this.model = model;
        SQLiteDatabase db = LitePal.getDatabase();
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
                        Log.e(TAG, "getHotSearch" + throwable.getMessage());
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
                            for (SearchBean searchBean : searchBeans) {
                                Log.e(TAG, searchBean.getTitle() + searchBean.getTimeStamp());
                            }
                            searchHistoryData.addAll(searchBeans);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "getSearchHistory" + throwable.getMessage());
                        //Toast.showToast(context, throwable.getMessage());
                    }
                });
    }

    public void search() {
        model.search(searchContent.get())
                .subscribe(new Consumer<List<SearchBean>>() {
                    @Override
                    public void accept(List<SearchBean> searchBeans) throws Exception {
                        if (ListUtil.notEmpty(searchBeans)) {
                            searchResult.addAll(searchBeans);
                            rvVisibility.set(View.VISIBLE);
                            visibility.set(View.GONE);
                        } else {
                            rvVisibility.set(View.GONE);
                            visibility.set(View.VISIBLE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.showToast(context, throwable.getMessage());
                    }
                });
    }

    public void clear() {
        searchContent.set(null);
    }

    public void clearHistory() {
        model.clearHistory();
        searchHistoryData.clear();
        historyVisibility.set(View.GONE);
    }

}
