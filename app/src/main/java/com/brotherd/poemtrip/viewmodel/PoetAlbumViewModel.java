package com.brotherd.poemtrip.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.bean.PoetAlbum;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.viewmodel.model.PoetAlbumModel;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by dumingwei on 2017/11/24 0024.
 */

public class PoetAlbumViewModel extends BaseViewModel {


    public ObservableField<String> poemCover = new ObservableField<>();
    public ObservableList<PoetAlbum.PoemListBean> poemList = new ObservableArrayList<>();
    private Disposable disposable;

    private PoetAlbumModel model;
    private PoetAlbum.PoetBean poet;

    public PoetAlbumViewModel(BaseDataBindingActivity context, PoetAlbumModel model) {
        super(context);
        this.model = model;
    }

    public void getPoet() {
        isLoading.set(true);
        dispose(disposable);
        disposable = model.getPoetAlbum()
                .subscribe(new Consumer<PoetAlbum>() {
                    @Override
                    public void accept(@NonNull PoetAlbum poetAlbum) throws Exception {
                        isLoading.set(false);
                        poet = poetAlbum.getPoet();
                        poemCover.set(poet.getImageUrl());
                        poemList.addAll(poetAlbum.getPoemList());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        isLoading.set(false);
                        Debug.e(TAG, "getPoet error:" + throwable.getMessage());
                        toastMsg.set(throwable.getMessage());
                    }
                });
    }

    @Override
    public void destroy() {
        super.destroy();
        dispose(disposable);
    }
}
