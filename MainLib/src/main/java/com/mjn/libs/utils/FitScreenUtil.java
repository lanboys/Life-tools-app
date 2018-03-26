package com.mjn.libs.utils;

import android.view.View;
import android.view.ViewGroup;


/**
 * 屏幕按比例平分
 */
public class FitScreenUtil {
    protected static double mScrrenW;
    protected static double mScrrenH;

    protected static double viewW;
    protected static double viewH;

    protected static double newW;
    protected static double newH;


    public static void setParams(View view, int temp, ViewGroup.LayoutParams params2) {
        int[] mSreenSize = UIUtils.getScrren(AppConfig.context);
        mScrrenH = mSreenSize[1];
        mScrrenW = mSreenSize[0];
        ViewGroup.LayoutParams params = view.getLayoutParams();
    }

    /**
     * 适配
     *
     * @param view
     * @param temp
     */
    public static void setParams(View view, int temp) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int[] mSreenSize = UIUtils.getScrren(AppConfig.context);
        mScrrenH = mSreenSize[1];
        mScrrenW = mSreenSize[0];
        LogUtil.d("mScrrenW=" + mScrrenW);
        LogUtil.d("mScrrenH=" + mScrrenH);
        if (view == null) {
            return;
        }
        viewW = view.getLayoutParams().width;
        viewH = view.getLayoutParams().height;

        switch (temp) {
            case 1:
                // vie
                newW = mScrrenW - 20;
                newH = newW * viewH / viewW;
                break;


            default:
        }
    }

}
