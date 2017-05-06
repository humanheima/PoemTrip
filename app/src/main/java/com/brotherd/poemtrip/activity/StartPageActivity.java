package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseActivity;

public class StartPageActivity extends BaseActivity {

    private static int WHAT = 7;
    private static int DELAY = 2000;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                MainActivity.launch(StartPageActivity.this);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }
    };

    public static void launch(Context context) {
        Intent starter = new Intent(context, StartPageActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_start_page;
    }

    @Override
    protected void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.obtainMessage(WHAT).sendToTarget();
            }
        }, DELAY);
    }
}
