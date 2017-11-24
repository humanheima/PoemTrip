package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.adapter.BaseRecyclerViewAdapter;
import com.brotherd.poemtrip.adapter.BaseRecyclerViewHolder;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.bean.PoetAlbum;
import com.brotherd.poemtrip.databinding.ActivityPoetAlbumBinding;
import com.brotherd.poemtrip.inter.OnItemClickListener;
import com.brotherd.poemtrip.viewmodel.PoetAlbumViewModel;
import com.brotherd.poemtrip.viewmodel.model.PoetAlbumModel;

import java.util.ArrayList;

public class PoetAlbumActivity extends BaseDataBindingActivity<ActivityPoetAlbumBinding> {

    private static final String TAG = "PoetAlbumActivity";
    public static final String POET_ID = "poetId";
    private PoetAlbumViewModel viewModel;

    public static void launch(Context context, int poetId) {
        Intent starter = new Intent(context, PoetAlbumActivity.class);
        starter.putExtra(POET_ID, poetId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_poet_album;
    }

    @Override
    protected void initData() {
        int poetId = getIntent().getIntExtra(POET_ID, -1);
        initView();
        viewModel = new PoetAlbumViewModel(this, new PoetAlbumModel(poetId));
        binding.setViewModel(viewModel);
        viewModel.getPoet();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(new BaseRecyclerViewAdapter<PoetAlbum.PoemListBean>(this, new ArrayList<PoetAlbum.PoemListBean>(0)) {
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
