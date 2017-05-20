package com.brotherd.poemtrip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.brotherd.poemtrip.inter.ItemTypeCallBack;

import java.util.List;

/**
 * Created by dumingwei on 2017/4/19.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> implements ItemTypeCallBack<T> {

    protected Context context;
    private List<T> datas;

    public BaseRecyclerViewAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        return getHolderType(position, datas.get(position));
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return BaseRecyclerViewHolder.get(context, parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        bindViewHolder(holder, datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public abstract void bindViewHolder(BaseRecyclerViewHolder holder, T t);
}
