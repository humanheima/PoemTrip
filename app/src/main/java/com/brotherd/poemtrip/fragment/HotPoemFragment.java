package com.brotherd.poemtrip.fragment;


import android.support.v7.widget.GridLayoutManager;
import android.widget.LinearLayout;

import com.android.databinding.library.baseAdapters.BR;
import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseAdapter;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.base.BaseFragment;
import com.brotherd.poemtrip.bean.PoemBean;
import com.brotherd.poemtrip.bean.PoetBean;
import com.brotherd.poemtrip.databinding.FragmentHotPoemBinding;
import com.brotherd.poemtrip.util.ScreenUtil;
import com.brotherd.poemtrip.viewmodel.HotPoemViewModel;

public class HotPoemFragment extends BaseFragment<FragmentHotPoemBinding> {

    private final String TAG = getClass().getSimpleName();

    private HotPoemViewModel viewModel;

    public HotPoemFragment() {
    }

    public static HotPoemFragment newInstance() {
        HotPoemFragment fragment = new HotPoemFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot_poem;
    }

    @Override
    protected void initData() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.simpleBanner.getLayoutParams();
        params.height = ScreenUtil.getWidth(getContext()) / 2;
        binding.simpleBanner.setLayoutParams(params);
        initGridPoem();
        initGridPoet();
        viewModel = new HotPoemViewModel(((BaseDataBindingActivity) getActivity()));
        binding.setViewModel(viewModel);
        viewModel.getHotPoem();
        viewModel.getHotPoet();
    }

    private void initGridPoem() {
        binding.rvPoem.setLayoutManager(new GridLayoutManager(getContext(), 3));
        //binding.rvPoem.addItemDecoration(new CcDividerGridItemDecoration(getContext(), 0));
        binding.rvPoem.setNestedScrollingEnabled(false);
        binding.rvPoem.setAdapter(new BaseAdapter<PoemBean>() {
            @Override
            protected int getLayoutId() {
                return R.layout.item_grid_view;
            }

            @Override
            protected void createHolder(BaseHolder holder) {
                holder.getBinding().setVariable(BR.clickHandler, new HotPoemViewModel.ClickHandler(getContext()));
            }
        });
    }

    private void initGridPoet() {
        binding.rvPoet.setLayoutManager(new GridLayoutManager(getContext(), 3));
        //binding.rvPoet.addItemDecoration(new CcDividerGridItemDecoration(getContext(), 0));
        binding.rvPoet.setNestedScrollingEnabled(false);
        binding.rvPoet.setAdapter(new BaseAdapter<PoetBean>() {
            @Override
            protected int getLayoutId() {
                return R.layout.item_poet_grid_view;
            }

            @Override
            protected void createHolder(BaseHolder holder) {
                holder.getBinding().setVariable(BR.clickHandler, new HotPoemViewModel.ClickHandler(getContext()));
            }
        });
    }

}
