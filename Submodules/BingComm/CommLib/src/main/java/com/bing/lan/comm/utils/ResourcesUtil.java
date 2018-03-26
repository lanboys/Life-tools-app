package com.bing.lan.comm.utils;

import android.content.res.Resources;

/**
 * @author 蓝兵
 */
public class ResourcesUtil {

    //public static float dp2px(float dip) {
    //    // denstity*dip=px;
    //    float density = Resources.getSystem().getDisplayMetrics().density;
    //    return (dip * density + .5f);
    //}
    //
    //public static float px2dp(float px) {
    //    // denstity*dip=px;
    //    float density = Resources.getSystem().getDisplayMetrics().density;
    //    return (px / density + .5f);
    //}

    public static float px2dp(float pxValue) {
        return (pxValue / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dp2px(int dip) {
        return (int) (dip * Resources.getSystem().getDisplayMetrics().density + .5f);
    }
}
