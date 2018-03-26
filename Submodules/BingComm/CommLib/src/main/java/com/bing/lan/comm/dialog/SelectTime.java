package com.bing.lan.comm.dialog;

/**
 * Author: yxhuang
 * Date: 2017/3/31
 * Email: yxhuang@gmail.com
 */

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  设置筛选时间
 */
public class SelectTime {

    @IntDef({START_IIME, END_TIME})
    @Retention(RetentionPolicy.SOURCE)
    public @interface setSelectTime{}


    /**
     *  开始时间
     */
    public static final int START_IIME = 1;

    /**
     * 结束时间
     */
    public static final int END_TIME = 2;

}
