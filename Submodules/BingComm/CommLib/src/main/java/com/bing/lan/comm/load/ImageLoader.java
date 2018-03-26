package com.bing.lan.comm.load;

import android.content.Context;
import android.widget.ImageView;

import com.bing.lan.comm.app.AppUtil;

import java.io.File;

/**
 * @author 蓝兵
 * @time 2017/2/23  21:55
 */
public class ImageLoader {

    private static volatile ImageLoader instance = null;
    private IBaseLoaderStrategy mStrategy;

    private ImageLoader() {
        mStrategy = new GlideLoadStrategy();
    }

    public static void init(Context context) {
        //GlideLoadStrategy.init(context);
    }

    public static ImageLoader getInstance() {

        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    public void loadImage(ImageView imageView, String url) {
        mStrategy.loadImage(AppUtil.getApplication(), imageView, url);
    }

    public void loadImage(ImageView imageView, String url, int reqWidth, int reqHeight) {
        mStrategy.loadImage(AppUtil.getApplication(), imageView, url, reqWidth, reqHeight);
    }

    public void loadImagePlaceholder(ImageView imageView, String url, int placeholderResId, int errorResId) {
        mStrategy.loadImagePlaceholder(AppUtil.getApplication(), imageView, url, placeholderResId, errorResId);
    }

    public void loadImagePlaceholder(ImageView imageView, String url, int reqWidth, int reqHeight, int placeholderResId, int errorResId) {
        mStrategy.loadImagePlaceholder(AppUtil.getApplication(), imageView, url, reqWidth, reqHeight, placeholderResId, errorResId);
    }

    public void loadBigImage(ImageView imageView, String url) {
        mStrategy.loadBigImage(AppUtil.getApplication(), imageView, url);
    }

    public void loadSmallImage(ImageView imageView, String url) {
        mStrategy.loadSmallImage(AppUtil.getApplication(), imageView, url);
    }

    public void loadImageCallBackFile(ImageView imageView, String url, GetImageCacheCallBack fileDownloadCallBack) {
        loadImageCallBackFile(imageView, url, 0, 0, fileDownloadCallBack, 0);
    }

    public void loadImageCallBackFile(ImageView imageView, String url,
            GetImageCacheCallBack fileDownloadCallBack, int targetAngle) {
        loadImageCallBackFile(imageView, url, 0, 0, fileDownloadCallBack, targetAngle);
    }

    public void loadImageCallBackFile(ImageView imageView, String url, int reqWidth, int reqHeight,
            GetImageCacheCallBack fileDownloadCallBack, int targetAngle) {

        mStrategy.loadImageCallBackFile(AppUtil.getApplication(), imageView, url, reqWidth, reqHeight,
                fileDownloadCallBack, targetAngle);
    }

    public void downloadImageFileOnly(String url, File destFile, DownloadImageCallBack fileDownloadCallBack) {
        mStrategy.downloadImageFileOnly(AppUtil.getApplication(), url, destFile, fileDownloadCallBack);
    }
}
