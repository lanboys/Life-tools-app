package com.bing.lan.comm.load;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

public interface IBaseLoaderStrategy {

    void loadImage(Context ctx, ImageView imageView, String url);

    void loadImage(Context ctx, ImageView imageView, String url, int reqWidth, int reqHeight);

    void loadImagePlaceholder(Context ctx, ImageView imageView, String url, int placeholderResId, int errorResId);

    void loadImagePlaceholder(Context ctx, ImageView imageView, String url, int reqWidth, int reqHeight, int placeholderResId, int errorResId);

    void loadSmallImage(Context ctx, ImageView imageView, String url);

    void loadBigImage(Context ctx, ImageView imageView, String url);

    //void loadImageCallBackFile(Context ctx, ImageView imageView, String imageUrl,
    //        DownloadImageCallBack fileDownloadCallBack, int targetAngle);

    void loadImageCallBackFile(Context ctx, ImageView imageView, String imageUrl, int reqWidth, int reqHeight,
            GetImageCacheCallBack fileDownloadCallBack, int targetAngle);

    void downloadImageFileOnly(Context ctx, String imageUrl, File destFile,
            DownloadImageCallBack fileDownloadCallBack);
}
