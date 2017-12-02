package com.brotherd.poemtrip.viewmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.databinding.adapters.TextViewBindingAdapter;
import android.opengl.Visibility;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.brotherd.poemtrip.activity.PoemActivity;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.bean.SearchBean;
import com.brotherd.poemtrip.util.ListUtil;
import com.brotherd.poemtrip.viewmodel.model.SearchModel;

import org.litepal.LitePal;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public class SearchViewModel extends BaseViewModel<SearchViewModel.SearchViewModelCallBack> {

    private static final String TAG = "SearchViewModel";
    public ObservableList<SearchBean> hotSearchData = new ObservableArrayList<>();
    public ObservableList<SearchBean> searchHistoryData = new ObservableArrayList<>();
    public ObservableList<SearchBean> searchResult = new ObservableArrayList<>();
    public ObservableField<String> searchContent = new ObservableField<>();
    public ObservableInt visibility = new ObservableInt(View.VISIBLE);
    public ObservableInt historyVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt rvVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt layoutEmptyVisibility = new ObservableInt(View.GONE);

    public TextViewBindingAdapter.AfterTextChanged afterTextChanged = new TextViewBindingAdapter.AfterTextChanged() {
        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                rvVisibility.set(View.GONE);
                visibility.set(View.VISIBLE);
                getSearchHistory();
            } else {
                search();
            }
        }
    };

    public View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                return callBack.hideSoftKeyAndSearch();
            }
            return false;
        }
    };

    private SearchModel model;

    public SearchViewModel(BaseDataBindingActivity context, SearchModel model, SearchViewModel.SearchViewModelCallBack callBack) {
        super(context);
        this.model = model;
        this.callBack = callBack;
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
                        toastMsg.set(throwable.getMessage());
                    }
                });
    }

    public void getSearchHistory() {
        model.getSearchHistory()
                .subscribe(new Consumer<List<SearchBean>>() {
                    @Override
                    public void accept(List<SearchBean> searchBeans) throws Exception {
                        if (ListUtil.notEmpty(searchBeans)) {
                            historyVisibility.set(View.VISIBLE);
                            for (SearchBean searchBean : searchBeans) {
                                Log.e(TAG, searchBean.getTitle() + searchBean.getTimeStamp());
                            }
                            searchHistoryData.clear();
                            searchHistoryData.addAll(searchBeans);
                        } else {
                            historyVisibility.set(View.GONE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        historyVisibility.set(View.GONE);
                        //toastMsg.set(throwable.getMessage());
                        Log.e(TAG, "getSearchHistory" + throwable.getMessage());
                    }
                });
    }

    public void search() {
        if (TextUtils.isEmpty(searchContent.get())) {
            return;
        }
        callBack.hideSoftKey();
        model.search(searchContent.get())
                .subscribe(new Consumer<List<SearchBean>>() {
                    @Override
                    public void accept(List<SearchBean> searchBeans) throws Exception {
                        searchResult.clear();
                        if (ListUtil.notEmpty(searchBeans)) {
                            searchResult.addAll(searchBeans);
                            rvVisibility.set(View.VISIBLE);
                            visibility.set(View.GONE);
                            layoutEmptyVisibility.set(View.GONE);
                        } else {
                            rvVisibility.set(View.GONE);
                            visibility.set(View.VISIBLE);
                            layoutEmptyVisibility.set(View.VISIBLE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        layoutEmptyVisibility.set(View.VISIBLE);
                        toastMsg.set(throwable.getMessage());
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

    public interface SearchViewModelCallBack extends BaseViewModel.BaseViewModelCallBack {

        boolean hideSoftKeyAndSearch();

        boolean hideSoftKey();
    }

    public static class ClickHandler {

        private Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void onClick(long poemId) {
            PoemActivity.launch(context, poemId);
        }
    }

}
