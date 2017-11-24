package com.brotherd.poemtrip.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;

import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.bean.PoemBean;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.RegularUtil;
import com.brotherd.poemtrip.viewmodel.model.PoemModel;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by dumingwei on 2017/11/24 0024.
 */

public class PoemViewModel extends BaseViewModel {

    public ObservableInt annoationTipVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt annoationVisibility = new ObservableInt(View.VISIBLE);

    public ObservableField<String> poemTitle = new ObservableField<>();
    public ObservableField<String> authorDynasty = new ObservableField<>();
    public ObservableField<String> annotation = new ObservableField<>();
    public ObservableField<String> poemContent = new ObservableField<>();
    public ObservableField<String> description = new ObservableField<>();

    private PoemModel model;

    public PoemViewModel(BaseDataBindingActivity context, PoemModel model) {
        super(context);
        this.model = model;
    }

    public void getPoem() {
        isLoading.set(true);
        model.getPoem()
                .subscribe(new Consumer<PoemBean>() {
                    @Override
                    public void accept(@NonNull PoemBean poemBean) throws Exception {
                        isLoading.set(false);
                        updateUI(poemBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        isLoading.set(false);
                        toastMsg.set(throwable.getMessage());
                        Debug.e(TAG, "getPoem error:" + throwable.getMessage());
                    }
                });
    }

    private void updateUI(PoemBean poemBean) {
        poemTitle.set(poemBean.getTitle().replaceAll("。", "--"));
        String poet = poemBean.getPoet();
        String dynasty = poemBean.getAge();
        authorDynasty.set(String.format("%s（%s）", poet, dynasty));
        String content = poemBean.getContent();
        poemContent.set(content.replaceAll("<br />", "").replaceAll("。", "\n").replaceAll("<[\\w/]+>", ""));
        String translation = poemBean.getTranslation();
        if (TextUtils.isEmpty(translation)) {
            annoationTipVisibility.set(View.GONE);
            annoationVisibility.set(View.GONE);
        } else {
            annoationTipVisibility.set(View.VISIBLE);
            annoationVisibility.set(View.VISIBLE);
            annotation.set(RegularUtil.getPlainString(translation));
        }
        description.set(RegularUtil.getPlainString(poemBean.getDescription()));
    }
}
