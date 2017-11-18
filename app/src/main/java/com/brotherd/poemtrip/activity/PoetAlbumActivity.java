package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.adapter.BaseRecyclerViewAdapter;
import com.brotherd.poemtrip.adapter.BaseRecyclerViewHolder;
import com.brotherd.poemtrip.base.BaseActivity;
import com.brotherd.poemtrip.inter.OnItemClickListener;
import com.brotherd.poemtrip.bean.PoetAlbum;
import com.brotherd.poemtrip.network.HttpResult;
import com.brotherd.poemtrip.network.NetWork;
import com.brotherd.poemtrip.util.Debug;
import com.brotherd.poemtrip.util.ImageUtil;
import com.brotherd.poemtrip.util.NetWorkUtil;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.widget.LoadingDialog;
import com.brotherd.poemtrip.widget.MixtureTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PoetAlbumActivity extends BaseActivity {

    private static final String TAG = "PoetAlbumActivity";
    public static final String POET_ID = "poetId";
    @BindView(R.id.img_poet)
    ImageView imgPoet;
    @BindView(R.id.text_poet)
    MixtureTextView textPoet;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private int poetId;
    private LoadingDialog loadingDialog;
    private PoetAlbum.PoetBean poet;
    private List<PoetAlbum.PoemListBean> poemList;

    public static void launch(Context context, int poetId) {
        Intent starter = new Intent(context, PoetAlbumActivity.class);
        starter.putExtra(POET_ID, poetId);
        context.startActivity(starter);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_poet_album;
    }

    @Override
    protected void initData() {
        poetId = getIntent().getIntExtra(POET_ID, -1);
        poemList = new ArrayList<>();
        loadingDialog=new LoadingDialog(this);
        if (NetWorkUtil.hasNetwork()) {
            getPoet();
        } else {
            Toast.showToast(this, getString(R.string.no_network));
        }
    }

    private void getPoet() {
        loadingDialog.show();
        NetWork.getApi().getPoetAlbum(poetId)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<HttpResult<PoetAlbum>, ObservableSource<PoetAlbum>>() {
                    @Override
                    public ObservableSource<PoetAlbum> apply(@NonNull HttpResult<PoetAlbum> result) throws Exception {
                        return NetWork.flatResponse(result);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PoetAlbum>() {
                    @Override
                    public void accept(@NonNull PoetAlbum poetAlbum) throws Exception {
                        loadingDialog.dismiss();
                        poet = poetAlbum.getPoet();
                        poemList.addAll(poetAlbum.getPoemList());
                        updateUI();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        loadingDialog.dismiss();
                        Debug.e(TAG, "getPoet error:" + throwable.getMessage());
                        Toast.showToast(PoetAlbumActivity.this, throwable.getMessage());
                    }
                });
    }

    private void updateUI() {
        ImageUtil.loadImage(this, poet.getImageUrl(), imgPoet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new BaseRecyclerViewAdapter<PoetAlbum.PoemListBean>(this, poemList) {
            @Override
            public void bindViewHolder(BaseRecyclerViewHolder holder, final PoetAlbum.PoemListBean poem) {
                holder.setTextViewText(R.id.text_poem_name, poem.getTitle());
                holder.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        PoemActivity.launch(PoetAlbumActivity.this, poem.getPoetId());
                    }
                });
            }

            @Override
            public int getHolderType(int position, PoetAlbum.PoemListBean poemListBean) {
                return R.layout.item_poet_album;
            }
        });
    }

}
