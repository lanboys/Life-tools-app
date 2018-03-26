package com.bing.lan.comm.load;

import com.bing.lan.comm.utils.BitmapUtil;

import java.io.File;

/**
 * @author 蓝兵
 */
public interface GetImageCacheCallBack {

    void onGetImageCacheCallBack(boolean isSuccess, String imageUrl, File file, BitmapUtil.BitmapResult bitmapResult);
}
