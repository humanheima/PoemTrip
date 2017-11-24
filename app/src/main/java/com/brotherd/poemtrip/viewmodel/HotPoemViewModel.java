package com.brotherd.poemtrip.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.activity.PoemActivity;
import com.brotherd.poemtrip.activity.PoetAlbumActivity;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.bean.PoemBean;
import com.brotherd.poemtrip.bean.PoetBean;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.Images;
import com.brotherd.poemtrip.util.NetWorkUtil;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.viewmodel.model.HotPoemModel;
import com.brotherd.poemtrip.widget.banner.banner.SimpleBanner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dumingwei on 2017/11/24 0024.
 */

public class HotPoemViewModel extends BaseViewModel {

    public ObservableField<SimpleBanner.BannerData> bannerData = new ObservableField<>();
    public ObservableList<PoemBean> poemList = new ObservableArrayList<>();
    public ObservableList<PoetBean> poetList = new ObservableArrayList<>();

    private Disposable poemDispos;
    private Disposable poetDispos;
    private HotPoemModel model;

    public HotPoemViewModel(BaseDataBindingActivity context) {
        super(context);
        model = new HotPoemModel();
        List<String> bannerImages = new ArrayList<>();
        List<String> bannerTitles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bannerImages.add(Images.imageUrls[i]);
            bannerTitles.add("");
        }
        bannerData.set(new SimpleBanner.BannerData(bannerImages, bannerTitles));
        getHotPoem();
        getHotPoet();
    }

    public void getHotPoem() {
        if (NetWorkUtil.hasNetwork()) {
            isLoading.set(true);
            dispose(poemDispos);
            poemDispos = model.getHotPoem().subscribe(new Consumer<List<PoemBean>>() {
                @Override
                public void accept(@NonNull List<PoemBean> poemBeans) throws Exception {
                    isLoading.set(false);
                    poemList.clear();
                    poemList.addAll(poemBeans);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    isLoading.set(false);
                    Toast.showToast(throwable.getMessage());
                    Debug.e(TAG, "getPoem error:" + throwable.getMessage());
                }
            });
        } else {
            Toast.showToast(R.string.no_network);
        }
    }

    public void getHotPoet() {
        if (NetWorkUtil.hasNetwork()) {
            isLoading.set(true);
            dispose(poetDispos);
            poetDispos = model.getHotPoet()
                    .subscribe(new Consumer<List<PoetBean>>() {
                        @Override
                        public void accept(@NonNull List<PoetBean> poetBeans) throws Exception {
                            isLoading.set(false);
                            poetList.clear();
                            poetList.addAll(poetBeans);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            isLoading.set(false);
                            Toast.showToast(throwable.getMessage());
                            Debug.e(TAG, "getPoet error:" + throwable.getMessage());
                        }
                    });
        } else {
            Toast.showToast(R.string.no_network);
        }
    }

    public void launchPoemActivity(int position) {
        PoemBean model = poemList.get(position);
        PoemActivity.launch(context, model.getPoemId());
    }

    public void launchPoetAlbumActivity(int position) {
        PoetBean model = poetList.get(position);
        PoetAlbumActivity.launch(context, model.getPoetId());
    }

    @Override
    public void destroy() {
        super.destroy();
        dispose(poemDispos);
        dispose(poetDispos);
    }
}
