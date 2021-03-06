package com.mjn.libs.utils;

/**
 * Created by sld on 16/3/17.
 */

import android.os.CountDownTimer;

/**
 * 计算获取验证码按钮的时间，并更新按钮文字
 *
 */
public class TimeCount extends CountDownTimer {

    private CountDownTimerListener countDownTimerListener;

    public TimeCount(long millisInFuture, long countDownInterval, CountDownTimerListener listener) {
        super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        countDownTimerListener = listener;
        countDownTimerListener.initTimer();
    }

    @Override
    public void onFinish() {// 计时完毕时触发
        countDownTimerListener.finishTimer();
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程显示
        countDownTimerListener.updateTimer(millisUntilFinished);
    }

    public interface CountDownTimerListener {
        /**
         * 初始化
         */
        public void initTimer();
        /**
         * 计时结束
         */
        public void finishTimer();
        /**
         * 更新控件
         */
        public void updateTimer(long millisUntilFinished);
    }
}