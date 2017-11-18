package com.brotherd.poemtrip.activity;

import android.view.View;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.databinding.ActivitySearchBinding;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.viewmodel.SearchViewModel;
import com.brotherd.poemtrip.viewmodel.model.SearchModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

public class SearchActivity extends BaseDataBindingActivity<ActivitySearchBinding> {

    private SearchViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
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
        viewModel = new SearchViewModel(this, new SearchModel());
        binding.setViewModel(viewModel);
        viewModel.getHotSearch();
        viewModel.getSearchHistory();

    }
}
