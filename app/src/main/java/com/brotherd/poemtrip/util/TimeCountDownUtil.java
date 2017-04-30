package com.brotherd.poemtrip.util;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by dumingwei on 2016/10/8.
 * 倒计时
 */
public class TimeCountDownUtil extends CountDownTimer {

    private TextView textView;

    public TimeCountDownUtil(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setClickable(false);
        textView.setText(millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        textView.setText("获取验证码");
        textView.setClickable(true);
    }
}
