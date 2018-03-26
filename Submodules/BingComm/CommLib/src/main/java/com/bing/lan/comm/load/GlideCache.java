package com.bing.lan.comm.load;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * @author 蓝兵
 */
public class GlideCache implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置图片的显示格式ARGB_8888(指图片大小为32bit)
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //设置磁盘缓存目录（和创建的缓存目录相同）
        ///storage/emulated/0/GlideCache/9870ab25533b4287065712cd07959148c5a8757a8c73fa2ed94ebb6d3a755221.0
        String downloadDirectoryPath = context.getExternalCacheDir().getAbsolutePath();
        // context.getExternalFilesDir("GlideCache");

        //设置缓存的大小为100M
        int cacheSize = 100 * 1000 * 1000;
        DiskLruCacheFactory factory = new DiskLruCacheFactory(downloadDirectoryPath, "GlideCache",cacheSize);
        // DiskLruCacheFactory factory = new DiskLruCacheFactory(downloadDirectoryPath, cacheSize);

        builder.setDiskCache(factory);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}