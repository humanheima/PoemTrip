package com.brotherd.poemtrip.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.util.Toast;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public abstract class BaseViewModel<T extends BaseViewModel.BaseViewModelCallBack> {

    protected ObservableBoolean isLoading = new ObservableBoolean();
    protected ObservableField<String> toastMsg = new ObservableField<>();
    protected T callBack;
    protected BaseDataBindingActivity context;

    protected String TAG;

    public BaseViewModel(BaseDataBindingActivity context) {
        TAG = getClass().getName();
        this.context = context;
        isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (isLoading.get()) {
                    BaseViewModel.this.context.showLoading();
                } else {
                    BaseViewModel.this.context.hideLoading();
                }
            }
        });
        toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (!TextUtils.isEmpty(toastMsg.get())){
                    Toast.showToast(toastMsg.get());
                }
            }
        });
    }

    protected void dispose(Disposable disposable) {
        if (null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void destroy() {
        context = null;
    }

    public interface BaseViewModelCallBack {


    }

    public void back() {
        if (context != null) {
            context.finish();
        }
    }
}
