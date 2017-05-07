package com.brotherd.poemtrip.fragment;


import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.activity.PoemActivity;
import com.brotherd.poemtrip.adapter.GridViewAdapter;
import com.brotherd.poemtrip.base.BaseFragment;
import com.brotherd.poemtrip.model.PoemModel;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.Images;
import com.brotherd.poemtrip.util.NetWorkUtil;
import com.brotherd.poemtrip.util.Toast;
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

public class HotPoemFragment extends BaseFragment {

    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.simpleBanner)
    SimpleBanner simpleBanner;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.grid_view)
    NoScrollGridView gridView;
    @BindView(R.id.ll_change_hot_poem)
    LinearLayout llChangeHotPoem;

    private List<PoemModel> poemModelList;
    private GridViewAdapter adapter;
    private List<String> bannerImages;
    private List<String> bannerTitles;

    public HotPoemFragment() {
    }

    public static HotPoemFragment newInstance() {
        HotPoemFragment fragment = new HotPoemFragment();
        return fragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_hot_poem;
    }

    @Override
    protected void initData() {
        loadingDialog = new LoadingDialog(getContext());
        bannerImages = new ArrayList<>();
        bannerTitles = new ArrayList<>();
        poemModelList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            bannerImages.add(Images.imageUrls[i]);
            bannerTitles.add("");
        }
        simpleBanner.setImages(bannerImages)
                .setBannerTitles(bannerTitles)
                .start();
        adapter = new GridViewAdapter(getContext(), R.layout.item_grid_view, poemModelList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoemModel model = poemModelList.get(position);
                PoemActivity.launch(getContext(), model.getPoemId());
            }
        });
        if (NetWorkUtil.hasNetwork()) {
            getHotPoem();
        } else {
            Toast.showToast(getContext(), getString(R.string.no_network));
        }
    }

    @Override
    protected void bindEvent() {
        llChangeHotPoem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHotPoem();
            }
        });
    }

    private void getHotPoem() {
        if (NetWorkUtil.hasNetwork()) {
            loadingDialog.show();
            NetWork.getApi().getHotPoem()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Function<HttpResult<List<PoemModel>>, ObservableSource<List<PoemModel>>>() {
                        @Override
                        public ObservableSource<List<PoemModel>> apply(@NonNull HttpResult<List<PoemModel>> result) throws Exception {
                            return NetWork.flatResponse(result);
                        }
                    })
                    .subscribe(new Consumer<List<PoemModel>>() {
                        @Override
                        public void accept(@NonNull List<PoemModel> poemModels) throws Exception {
                            loadingDialog.dismiss();
                            poemModelList.clear();
                            poemModelList = poemModels;
                            updateUI();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            loadingDialog.dismiss();
                            Toast.showToast(getActivity(), throwable.getMessage());
                            Debug.e(TAG, "getPoem error:" + throwable.getMessage());
                        }
                    });
        } else {
            Toast.showToast(getContext(), getString(R.string.no_network));
        }
    }

    private void updateUI() {
        adapter = new GridViewAdapter(getContext(), R.layout.item_grid_view, poemModelList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoemModel model = poemModelList.get(position);
                PoemActivity.launch(getContext(), model.getPoemId());
            }
        });
    }

}
