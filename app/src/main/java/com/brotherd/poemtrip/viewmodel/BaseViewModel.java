package com.brotherd.poemtrip.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;

import com.brotherd.poemtrip.base.BaseDataBindingActivity;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public abstract class BaseViewModel<T extends BaseViewModel.BaseViewModelCallBack> {

    protected ObservableBoolean isLoading = new ObservableBoolean();
    protected T callBack;
    protected BaseDataBindingActivity context;

    public BaseViewModel(BaseDataBindingActivity context) {
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
