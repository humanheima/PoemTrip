package com.brotherd.poemtrip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.bean.PoemBean;
import com.brotherd.poemtrip.util.ImageUtil;
import com.brotherd.poemtrip.widget.SquareImageView;

import java.util.List;

/**
 * Created by dumingwei on 2017/4/20.
 */
public class GridViewAdapter extends ArrayAdapter<PoemBean> {

    private List<PoemBean> poemBeanList;
    private Context context;
    private int resource;

    public GridViewAdapter(Context context, int resource, List<PoemBean> poemBeanList) {
        super(context, resource, poemBeanList);
        this.poemBeanList = poemBeanList;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PoemBean poemBean = poemBeanList.get(position);
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.imgCover = (SquareImageView) view.findViewById(R.id.img_cover);
            holder.textTitle = (TextView) view.findViewById(R.id.text_title);
            holder.textPoet = (TextView) view.findViewById(R.id.text_poet);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        String imageUrl = poemBean.getImageUrl();
        ImageUtil.loadImage(context, imageUrl, holder.imgCover);
        holder.textTitle.setText(poemBean.getTitle().replaceAll("。","--"));
        holder.textPoet.setText(poemBean.getPoet());
        return view;
    }

    class ViewHolder {
        SquareImageView imgCover;
        TextView textTitle;
        TextView textPoet;
    }
}
