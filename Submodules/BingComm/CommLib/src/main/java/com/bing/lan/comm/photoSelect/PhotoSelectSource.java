package com.bing.lan.comm.photoSelect;

/**
 * @author 蓝兵
 * @email lan_bing2013@163.com
 * @time 2017/7/16  21:36
 */

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.bing.lan.comm.photoSelect.PhotoSelectSource.SELECT_ALBUM;
import static com.bing.lan.comm.photoSelect.PhotoSelectSource.SELECT_ALL;
import static com.bing.lan.comm.photoSelect.PhotoSelectSource.SELECT_CAMERA;

/**
 * 选择照片来源的类型
 */

@IntDef({SELECT_CAMERA, SELECT_ALBUM,/* CANCEL, */SELECT_ALL})
@Retention(RetentionPolicy.SOURCE)
public @interface PhotoSelectSource {

    public static final int SELECT_CAMERA = 0;         // 拍照
    public static final int SELECT_ALBUM = 1;      // 相册
    // public static final int CANCEL = 2;             // 取消
    public static final int SELECT_ALL = 2;             // 用户选择

    //@IntDef({SELECT_CAMERA, SELECT_ALBUM,/* CANCEL, */SELECT_ALL})
    //@Retention(RetentionPolicy.SOURCE)
    //public @interface PhotoFlavour {
    //
    //}
}