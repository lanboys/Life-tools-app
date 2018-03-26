package com.bing.lan.comm.load;

import java.io.File;

/**
 * @author 蓝兵
 */
public interface DownloadImageCallBack {

    void onDownloadImageCallBack(boolean isSuccess, String imageUrl, File file);
}
