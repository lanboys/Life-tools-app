package com.bing.lan.comm.photoSelect;

import android.widget.ImageView;

import java.io.File;

/**
 * Created by win7 on 2017/4/25.
 */
public interface OnPhotoSelectListener {

    void onPhotoSelect(ImageView imageView, File source);
}
