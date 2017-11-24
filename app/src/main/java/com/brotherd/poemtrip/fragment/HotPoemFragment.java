package com.brotherd.poemtrip.fragment;


import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.activity.PoemActivity;
import com.brotherd.poemtrip.activity.PoetAlbumActivity;
import com.brotherd.poemtrip.adapter.BaseGridViewAdapter;
import com.brotherd.poemtrip.adapter.GridViewAdapter;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.base.BaseFragment;
import com.brotherd.poemtrip.bean.PoemBean;
import com.brotherd.poemtrip.bean.PoetBean;
import com.brotherd.poemtrip.databinding.FragmentHotPoemBinding;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.NetWorkUtil;
import com.brotherd.poemtrip.util.ScreenUtil;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.viewmodel.HotPoemViewModel;
import com.brotherd.poemtrip.widget.LoadingDialog;
import com.brotherd.poemtrip.widget.NoScrollGridView;
import com.brotherd.poemtrip.widget.banner.banner.SimpleBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HotPoemFragment extends BaseFragment<FragmentHotPoemBinding> {

    private final String TAG = getClass().getSimpleName();

    private GridViewAdapter adapter;
    private BaseGridViewAdapter<PoetBean> poetAdapter;

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
    }

    private void initGridPoem() {
        if (adapter == null) {
            adapter = new GridViewAdapter(getContext(), R.layout.item_grid_view, new ArrayList<PoemBean>(0));
            binding.gridViewPoem.setAdapter(adapter);
            binding.gridViewPoem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    viewModel.launchPoemActivity(position);
                }
            });
        }
    }

    private void initGridPoet() {
        if (poetAdapter == null) {
            poetAdapter = new BaseGridViewAdapter<PoetBean>(getContext(), R.layout.item_poet_grid_view, new ArrayList<PoetBean>(0)) {
                @Override
                public void bindView(CommonViewHolder holder, PoetBean data) {
                    holder.setImageViewUrl(R.id.img_cover, data.getImageUrl());
                    holder.setTextViewText(R.id.text_poet, data.getPoetName());
                }
            };
            binding.gridViewPoet.setAdapter(poetAdapter);
            binding.gridViewPoet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    viewModel.launchPoetAlbumActivity(position);
                }
            });
        }
    }
}
