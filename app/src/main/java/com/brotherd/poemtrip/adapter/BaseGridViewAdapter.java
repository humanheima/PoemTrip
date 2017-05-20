package com.brotherd.poemtrip.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brotherd.poemtrip.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by dumingwei on 2017/4/20.
 * 通用的
 */
public abstract class BaseGridViewAdapter<T> extends ArrayAdapter<T> {

    private List<T> dataList;
    private Context context;
    private int resource;

    public BaseGridViewAdapter(Context context, int resource, List<T> dataList) {
        super(context, resource, dataList);
        this.dataList = dataList;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T data = dataList.get(position);
        View view;
        CommonViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false);
            holder = new CommonViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (CommonViewHolder) view.getTag();
        }
        bindView(holder, data);
        return view;
    }

    public abstract void bindView(CommonViewHolder holder, T data);

    public class CommonViewHolder {

        private SparseArray<View> mViews;
        View itemView;

        public CommonViewHolder(View itemView) {
            mViews = new SparseArray<>();
            this.itemView = itemView;
        }

        /**
         * 通过ViewId获取控件
         *
         * @param viewId
         * @param <T>
         * @return
         */
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public CommonViewHolder setTextViewText(int viewId, String text) {
            TextView textView = getView(viewId);
            textView.setText(text);
            return this;
        }

        public CommonViewHolder setImageViewResource(int viewId, int resId) {
            ImageView imageView = getView(viewId);
            imageView.setImageResource(resId);
            return this;
        }

        public CommonViewHolder setImageViewUrl(int viewId, String url) {
            ImageView imageView = getView(viewId);
            Glide.with(context).load(url).placeholder(R.drawable.ic_default_poet).error(R.drawable.ic_default_poet).into(imageView);
            return this;
        }
    }
}
