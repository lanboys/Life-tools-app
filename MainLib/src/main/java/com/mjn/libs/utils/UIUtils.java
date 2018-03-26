package com.mjn.libs.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by MAC on 16/4/15.
 */
public class UIUtils {
    /**
     * 获取手机屏幕的分辨率
     *
     * @return
     */
    public static int[] getScrren(Context mContext) {
        int[] xy = new int[2];
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
        int height = wm.getDefaultDisplay().getHeight();// 屏幕高度
        xy[0] = width;
        xy[1] = height;
        return xy;
    }

    public static int getScrrenWidth(Context context) {

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScrrenHeight(Context context) {

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
