package com.brotherd.poemtrip.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brotherd.poemtrip.R;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class TitleLayout extends RelativeLayout {

    private ImageView imgBack;
    private TextView textTitle;
    //标题栏右边的操作
    private TextView textAction;
    private String title;
    private String action;
    private OnCustomActionListener actionListener;

    public void setActionListener(OnCustomActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public TitleLayout(Context context) {
        this(context, null);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCustomAttr(context, attrs);
        initView(context);
    }


    private void initCustomAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout);
        title = ta.getString(R.styleable.TitleLayout_title);
        action = ta.getString(R.styleable.TitleLayout_action);
        ta.recycle();
    }

    private void initView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.rl_title, this);
        imgBack = (ImageView) view.findViewById(R.id.img_back);
        imgBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
        textTitle = (TextView) view.findViewById(R.id.text_title);
        textAction = (TextView) view.findViewById(R.id.text_register);

        if (!TextUtils.isEmpty(title)) {
            textTitle.setText(title);
        }
        if (!TextUtils.isEmpty(action)) {
            textAction.setText(action);
            if (actionListener != null) {
                textAction.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionListener.onCustomAction();
                    }
                });
            }
        }
    }

    public interface OnCustomActionListener {
        void onCustomAction();
    }

}
