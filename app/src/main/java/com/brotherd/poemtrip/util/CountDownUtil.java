package com.brotherd.poemtrip.util;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by dumingwei on 2016/10/8.
 * 倒计时
 */
public class CountDownUtil extends CountDownTimer {

    public static long MILLS_IN_FEATURE = 10 * 1000;
    public static long COUNT_DOWN_INTERVAL = 1000;
    private CountDownListener countDownListener;
    private boolean started;

    public void setCountDownListener(CountDownListener countDownListener) {
        this.countDownListener = countDownListener;
    }

    public CountDownUtil() {
        this(MILLS_IN_FEATURE, COUNT_DOWN_INTERVAL);
    }

    public CountDownUtil(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (countDownListener != null) {
            if (!started) {
                countDownListener.onCountDownStart();
                started = true;
            }
            countDownListener.onCountDownProcessing(millisUntilFinished / 1000);
        }
    }

    @Override
    public void onFinish() {
        if (countDownListener != null) {
            countDownListener.onCountDownEnd();
        }
    }

    public interface CountDownListener {

        void onCountDownStart();

        void onCountDownProcessing(long second);

        void onCountDownEnd();
    }
}
