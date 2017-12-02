package com.brotherd.poemtrip.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brotherd.poemtrip.BR;

import java.util.List;

/**
 * Created by dumingwei on 2017/11/19 0019.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {

    private List<T> data;

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        BaseHolder baseHolder = new BaseHolder(view);
        createHolder(baseHolder);
        return baseHolder;
    }

    protected abstract int getLayoutId();

    protected void createHolder(BaseHolder baseHolder) {

    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        holder.binding.setVariable(BR.data, data.get(position));
        bindHolder(holder, position, data.get(position));
        holder.binding.executePendingBindings();
    }

    protected void bindHolder(BaseHolder holder, int position, T t) {
    }

    @Override
    public int getItemCount() {
        return null == data ? 0 : data.size();
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class BaseHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {

        private V binding;

        public BaseHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public V getBinding() {
            return binding;
        }
    }


}
