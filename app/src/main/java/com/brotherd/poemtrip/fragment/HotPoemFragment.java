package com.brotherd.poemtrip.fragment;


import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.activity.PoemActivity;
import com.brotherd.poemtrip.adapter.CommonGridViewAdapter;
import com.brotherd.poemtrip.adapter.GridViewAdapter;
import com.brotherd.poemtrip.base.BaseFragment;
import com.brotherd.poemtrip.model.PoemModel;
import com.brotherd.poemtrip.model.PoetModel;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.Images;
import com.brotherd.poemtrip.util.NetWorkUtil;
import com.brotherd.poemtrip.util.ScreenUtil;
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
    @BindView(R.id.ll_poet_more)
    LinearLayout llPoetMore;
    @BindView(R.id.grid_view_poet)
    NoScrollGridView gridViewPoet;
    @BindView(R.id.ll_change_hot_poet)
    LinearLayout llChangeHotPoet;

    private List<String> bannerImages;
    private List<String> bannerTitles;
    //诗
    private List<PoemModel> poemModelList;
    private GridViewAdapter adapter;
    //诗人
    private List<PoetModel> poetList;
    private CommonGridViewAdapter<PoetModel> poetAdapter;

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
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) simpleBanner.getLayoutParams();
        params.height = ScreenUtil.getWidth(getContext()) / 2;
        simpleBanner.setLayoutParams(params);
        loadingDialog = new LoadingDialog(getContext());
        bannerImages = new ArrayList<>();
        bannerTitles = new ArrayList<>();
        poemModelList = new ArrayList<>();
        poetList=new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bannerImages.add(Images.imageUrls[i]);
            bannerTitles.add("");
        }
        simpleBanner.setImages(bannerImages)
                .setBannerTitles(bannerTitles)
                .start();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoemModel model = poemModelList.get(position);
                PoemActivity.launch(getContext(), model.getPoemId());
            }
        });
        if (NetWorkUtil.hasNetwork()) {
            getHotPoem();
            getHotPoet();
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
        llChangeHotPoet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHotPoet();
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
                            poemModelList.addAll(poemModels);
                            updateGridPoem();
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

    private void getHotPoet() {
        if (NetWorkUtil.hasNetwork()) {
            loadingDialog.show();
            NetWork.getApi().getHotPoet()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Function<HttpResult<List<PoetModel>>, ObservableSource<List<PoetModel>>>() {
                        @Override
                        public ObservableSource<List<PoetModel>> apply(@NonNull HttpResult<List<PoetModel>> result) throws Exception {
                            return NetWork.flatResponse(result);
                        }
                    })
                    .subscribe(new Consumer<List<PoetModel>>() {
                        @Override
                        public void accept(@NonNull List<PoetModel> poetModels) throws Exception {
                            loadingDialog.dismiss();
                            poetList.clear();
                            poetList.addAll(poetModels);
                            updateGridPoet();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            loadingDialog.dismiss();
                            Toast.showToast(getActivity(), throwable.getMessage());
                            Debug.e(TAG, "getPoet error:" + throwable.getMessage());
                        }
                    });
        } else {
            Toast.showToast(getContext(), getString(R.string.no_network));
        }
    }

    private void updateGridPoem() {
        if (adapter == null) {
            adapter = new GridViewAdapter(getContext(), R.layout.item_grid_view, poemModelList);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PoemModel model = poemModelList.get(position);
                    PoemActivity.launch(getContext(), model.getPoemId());
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void updateGridPoet() {
        if (poetAdapter == null) {
            poetAdapter = new CommonGridViewAdapter<PoetModel>(getContext(), R.layout.item_poet_grid_view, poetList) {
                @Override
                public void bindView(CommonViewHolder holder, PoetModel data) {
                    holder.setImageViewUrl(R.id.img_cover, data.getImageUrl());
                    holder.setTextViewText(R.id.text_poet, data.getPoetName());
                }
            };
            gridViewPoet.setAdapter(poetAdapter);
        } else {
            poetAdapter.notifyDataSetChanged();
        }
    }
}
