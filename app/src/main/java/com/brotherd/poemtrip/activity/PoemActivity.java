package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseActivity;
import com.brotherd.poemtrip.model.PoemModel;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.NetWorkUtil;
import com.brotherd.poemtrip.util.RegularUtil;
import com.brotherd.poemtrip.util.Toast;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PoemActivity extends BaseActivity {

    public static final String POEM_ID = "poemId";
    private static final String TAG = "PoemActivity";
    @BindView(R.id.text_poem_title)
    TextView textPoemTitle;
    @BindView(R.id.text_author_dynasty)
    TextView textAuthorDynasty;
    @BindView(R.id.text_annotation_tip)
    TextView textAnnoationTip;
    @BindView(R.id.text_annotation)
    TextView textAnnotation;
    @BindView(R.id.text_description)
    TextView textDescription;
    @BindView(R.id.text_poem_content)
    TextView textPoemContent;

    private long poemId;

    public static void launch(Context context, long poemId) {
        Intent starter = new Intent(context, PoemActivity.class);
        starter.putExtra(POEM_ID, poemId);
        context.startActivity(starter);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_poem;
    }

    @Override
    protected void initData() {
        poemId = getIntent().getLongExtra(POEM_ID, -1);
        if (poemId == -1) {
            return;
        }
        if (NetWorkUtil.hasNetwork()) {
            getPoem();
        } else {
            Toast.showToast(this, getString(R.string.no_network));
        }
    }

    private void getPoem() {
        NetWork.getApi().getPoem(poemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<HttpResult<PoemModel>, ObservableSource<PoemModel>>() {
                    @Override
                    public ObservableSource<PoemModel> apply(@NonNull HttpResult<PoemModel> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                })
                .subscribe(new Consumer<PoemModel>() {
                    @Override
                    public void accept(@NonNull PoemModel poemModel) throws Exception {
                        updateUI(poemModel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Toast.showToast(PoemActivity.this, throwable.getMessage());
                        Debug.e(TAG, "getPoem error:" + throwable.getMessage());
                    }
                });
    }

    private void updateUI(PoemModel poemModel) {
        textPoemTitle.setText(poemModel.getTitle().replaceAll("。", "--"));
        String poet = poemModel.getPoet();
        String dynasty = poemModel.getAge();
        textAuthorDynasty.setText(String.format("%s（%s）", poet, dynasty));
        String content = poemModel.getContent();
        textPoemContent.setText(content.replaceAll("<br />", "").replaceAll("。", "\n").replaceAll("<[\\w/]+>", ""));
        String translation = poemModel.getTranslation();
        if (TextUtils.isEmpty(translation)) {
            textAnnoationTip.setVisibility(View.GONE);
            textAnnotation.setVisibility(View.GONE);
        } else {
            textAnnoationTip.setVisibility(View.VISIBLE);
            textAnnotation.setVisibility(View.VISIBLE);
            textAnnotation.setText(RegularUtil.getPlainString(translation));
        }
        textDescription.setText(RegularUtil.getPlainString(poemModel.getDescription()));
    }

}
