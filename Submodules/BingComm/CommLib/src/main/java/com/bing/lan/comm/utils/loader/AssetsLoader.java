package com.bing.lan.comm.utils.loader;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bing.lan.comm.utils.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetsLoader {

    protected static final LogUtil log = LogUtil.getLogUtil(AssetsLoader.class, LogUtil.LOG_VERBOSE);

    public static String loadJsonString(Context context, String fileName) {
        try {
            AssetManager assets = context.getAssets();
            InputStreamReader isr = new InputStreamReader(assets.open(fileName), "utf-8");
            //从assets获取json文件
            BufferedReader bfr = new BufferedReader(isr);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bfr.readLine()) != null) {
                stringBuilder.append(line);
            }
            bfr.close();
            //将JSON数据转化为字符串
            String string = stringBuilder.toString();
            log.i("loadJsonString(): " + string);
            return string;
        } catch (IOException e) {
            log.e("loadJsonString():  " + e.getLocalizedMessage());
        }

        return "";
    }

    public static Bitmap loadBitmap(Context context, String fileName) {
        try {
            AssetManager assets = context.getAssets();
            InputStream stream = assets.open(fileName);
            //转化为bitmap对象
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            log.e("loadBitmap():  " + e.getLocalizedMessage());
        }
        return null;
    }
}
