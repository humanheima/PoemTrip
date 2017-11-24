package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseAdapter;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.bean.SearchBean;
import com.brotherd.poemtrip.databinding.ActivitySearchBinding;
import com.brotherd.poemtrip.impl.TextWatcherImpl;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.viewmodel.SearchViewModel;
import com.brotherd.poemtrip.viewmodel.model.SearchModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

public class SearchActivity extends BaseDataBindingActivity<ActivitySearchBinding> implements SearchViewModel.SearchViewModelCallBack {

    private SearchViewModel viewModel;
    private InputMethodManager manager;

    public static void launch(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(new BaseAdapter<SearchBean>() {
            @Override
            protected int getLayoutId() {
                return R.layout.item_search_result;
            }

            @Override
            protected void bindHolder(BaseHolder holder, int position, SearchBean searchBean) {

            }
        });
        viewModel = new SearchViewModel(this, new SearchModel(),this);
        binding.setViewModel(viewModel);
        viewModel.getHotSearch();
        viewModel.getSearchHistory();
    }

    @Override
    protected void bindEvent() {
        binding.tagHotSearch.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.showToast(SearchActivity.this, "hello");
                return true;
            }
        });
        binding.tagSearchHistory.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.showToast(SearchActivity.this, "hello");
                return true;
            }
        });
    }

    @Override
    public boolean hideSoftKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            //先隐藏键盘
            if (manager.isActive()) {
                manager.hideSoftInputFromWindow(binding.etSearch.getApplicationWindowToken(), 0);
            }
            viewModel.search();
        }
        return false;
    }
}
