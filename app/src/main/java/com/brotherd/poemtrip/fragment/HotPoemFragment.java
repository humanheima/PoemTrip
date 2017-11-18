package com.brotherd.poemtrip.fragment;


import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.activity.PoemActivity;
import com.brotherd.poemtrip.activity.PoetAlbumActivity;
import com.brotherd.poemtrip.adapter.BaseGridViewAdapter;
import com.brotherd.poemtrip.adapter.GridViewAdapter;
import com.brotherd.poemtrip.base.BaseFragment;
import com.brotherd.poemtrip.bean.PoemBean;
import com.brotherd.poemtrip.bean.PoetBean;
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
    private List<PoemBean> poemBeanList;
    private GridViewAdapter adapter;
    //诗人
    private List<PoetBean> poetList;
    private BaseGridViewAdapter<PoetBean> poetAdapter;

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
        poemBeanList = new ArrayList<>();
        poetList = new ArrayList<>();
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
                PoemBean model = poemBeanList.get(position);
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
                    .flatMap(new Function<HttpResult<List<PoemBean>>, ObservableSource<List<PoemBean>>>() {
                        @Override
                        public ObservableSource<List<PoemBean>> apply(@NonNull HttpResult<List<PoemBean>> result) throws Exception {
                            return NetWork.flatResponse(result);
                        }
                    })
                    .subscribe(new Consumer<List<PoemBean>>() {
                        @Override
                        public void accept(@NonNull List<PoemBean> poemBeans) throws Exception {
                            loadingDialog.dismiss();
                            poemBeanList.clear();
                            poemBeanList.addAll(poemBeans);
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
                    .flatMap(new Function<HttpResult<List<PoetBean>>, ObservableSource<List<PoetBean>>>() {
                        @Override
                        public ObservableSource<List<PoetBean>> apply(@NonNull HttpResult<List<PoetBean>> result) throws Exception {
                            return NetWork.flatResponse(result);
                        }
                    })
                    .subscribe(new Consumer<List<PoetBean>>() {
                        @Override
                        public void accept(@NonNull List<PoetBean> poetBeans) throws Exception {
                            loadingDialog.dismiss();
                            poetList.clear();
                            poetList.addAll(poetBeans);
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
            adapter = new GridViewAdapter(getContext(), R.layout.item_grid_view, poemBeanList);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PoemBean model = poemBeanList.get(position);
                    PoemActivity.launch(getContext(), model.getPoemId());
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void updateGridPoet() {
        if (poetAdapter == null) {
            poetAdapter = new BaseGridViewAdapter<PoetBean>(getContext(), R.layout.item_poet_grid_view, poetList) {
                @Override
                public void bindView(CommonViewHolder holder, PoetBean data) {
                    holder.setImageViewUrl(R.id.img_cover, data.getImageUrl());
                    holder.setTextViewText(R.id.text_poet, data.getPoetName());
                }
            };
            gridViewPoet.setAdapter(poetAdapter);
            gridViewPoet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PoetBean model = poetList.get(position);
                    PoetAlbumActivity.launch(getContext(), model.getPoetId());
                }
            });
        } else {
            poetAdapter.notifyDataSetChanged();
        }
    }
}
