package com.brotherd.poemtrip.util;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.activity.PoemActivity;
import com.brotherd.poemtrip.adapter.BaseGridViewAdapter;
import com.brotherd.poemtrip.adapter.BaseRecyclerViewAdapter;
import com.brotherd.poemtrip.adapter.GridViewAdapter;
import com.brotherd.poemtrip.base.BaseAdapter;
import com.brotherd.poemtrip.bean.PoemBean;
import com.brotherd.poemtrip.bean.PoetBean;
import com.brotherd.poemtrip.bean.SearchBean;
import com.brotherd.poemtrip.widget.banner.banner.SimpleBanner;
import com.bumptech.glide.Glide;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public class BindingUtil {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        ImageUtil.loadImage(view.getContext(), url, view);
    }

    @BindingAdapter({"tagData"})
    public static void setTagData(TagFlowLayout tagFlowLayout, List<SearchBean> data) {
        if (tagFlowLayout.getAdapter() == null) {
            tagFlowLayout.setAdapter(new TagAdapter<SearchBean>(data) {
                @Override
                public View getView(FlowLayout parent, int position, SearchBean searchBean) {
                    TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tag_tv,
                            parent, false);
                    tv.setText(searchBean.getTitle());
                    return tv;
                }
            });
        } else {
            tagFlowLayout.getAdapter().notifyDataChanged();
        }
    }

    @BindingAdapter({"searchData"})
    public static void setSearchData(RecyclerView rv, List<SearchBean> data) {
        if (null != rv.getAdapter()) {
            ((BaseAdapter) rv.getAdapter()).setData(data);
        }
    }

    @BindingAdapter({"htmlText"})
    public static void setHtmlText(TextView tv, String text) {
        if (!TextUtils.isEmpty(text)) {
            text = text.replaceAll("<h2>", "<font color='#3bcc64'>");
            text = text.replaceAll("</h2>", "</font>");
        }
        tv.setText(Html.fromHtml(text));
    }

    @BindingAdapter({"bannerData"})
    public static void setBannerData(SimpleBanner banner, SimpleBanner.BannerData bannerData) {
        if (null != banner) {
            banner.setBannerData(bannerData);
        }
    }

    @BindingAdapter({"hotPoem"})
    public static void setHotPoem(RecyclerView rv, List<PoemBean> poemList) {
        if (null != rv.getAdapter()) {
            ((BaseAdapter<PoemBean>) rv.getAdapter()).setData(poemList);
        }
    }

    @BindingAdapter({"hotPoet"})
    public static void setHotPoet(RecyclerView rv, List<PoetBean> poetList) {
        if (null != rv.getAdapter()) {
            ((BaseAdapter<PoetBean>) rv.getAdapter()).setData(poetList);
        }
    }

    @BindingAdapter({"data"})
    public static void setData(RecyclerView recyclerView, List<?> poetList) {
        if (null != recyclerView.getAdapter()) {
            ((BaseRecyclerViewAdapter) recyclerView.getAdapter()).setData(poetList);
        }
    }

}
