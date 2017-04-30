package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseActivity;

public class SetPasswordNickNameActivity extends BaseActivity {

    public static void launch(Context context) {
        Intent starter = new Intent(context, SetPasswordNickNameActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_set_password_nick_name;
    }

    @Override
    protected void initData() {

    }
}
