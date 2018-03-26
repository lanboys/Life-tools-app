package com.mjn.libs.utils;


import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ImageLoaderUtil {

    public static void showImage(ImageView imageView, String url, int defaultImagRes) {
        Glide.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .centerCrop()
                .placeholder(defaultImagRes)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(defaultImagRes)
                .into(imageView);
    }

    public static void showImageInside(ImageView imageView, String url, int defaultImagRes) {
        Glide.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .fitCenter()
                .placeholder(defaultImagRes)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(defaultImagRes)
                .into(imageView);
    }

    public static void showImage(ImageView imageView, Uri uri, int defaultImagRes) {
        Glide.with(imageView.getContext())
                .load(uri)
                .dontAnimate()
                .centerCrop()
                .thumbnail(0.1f)
                .placeholder(defaultImagRes)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(defaultImagRes)
                .into(imageView);
    }

    public static void showImageNormal(ImageView imageView, String url, int defaultImagRes) {
        Glide.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(defaultImagRes)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(defaultImagRes)
                .into(imageView);
    }


}
