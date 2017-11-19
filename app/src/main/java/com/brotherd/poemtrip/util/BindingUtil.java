package com.brotherd.poemtrip.util;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.adapter.BaseRecyclerViewAdapter;
import com.brotherd.poemtrip.base.BaseAdapter;
import com.brotherd.poemtrip.bean.SearchBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by dumingwei on 2017/11/18 0018.
 */

public class BindingUtil {

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
        tv.setText(Html.fromHtml(text));
    }

}
