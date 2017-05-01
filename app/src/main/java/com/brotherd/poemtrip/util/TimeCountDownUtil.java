package com.brotherd.poemtrip.util;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by dumingwei on 2016/10/8.
 * 倒计时
 */
public class TimeCountDownUtil extends CountDownTimer {

    public static long MILLS_IN_FEATURE = 10 * 1000;
    public static long COUNT_DOWN_INTERVAL = 1000;
    private TextView textView;
    private CountDownListener countDownListener;

    public void setCountDownListener(CountDownListener countDownListener) {
        this.countDownListener = countDownListener;
    }

    public TimeCountDownUtil(TextView textView) {
        this(MILLS_IN_FEATURE, COUNT_DOWN_INTERVAL, textView);
    }

    public TimeCountDownUtil(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (countDownListener!=null){
            countDownListener.onCountDownStart();
        }
        textView.setText(millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        if (countDownListener!=null){
            countDownListener.onCountDownEnd();
        }
    }

    public interface CountDownListener {

        void onCountDownStart();

        void onCountDownEnd();
    }
}
