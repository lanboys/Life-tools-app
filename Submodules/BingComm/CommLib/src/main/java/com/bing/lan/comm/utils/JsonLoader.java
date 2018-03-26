package com.bing.lan.comm.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonLoader {

    // Context mContext;
    //
    // public JsonLoader(Context context) {
    //     mContext = context;
    // }

    public static String loadAssetsJson(Context context,String fileName) {


        AssetManager assets = context.getAssets();

        try {

            InputStreamReader isr = new InputStreamReader(assets.open(fileName), "utf-8");
            //从assets获取json文件
            BufferedReader bfr = new BufferedReader(isr);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bfr.readLine()) != null) {
                stringBuilder.append(line);
            }//将JSON数据转化为字符串
            Log.d("qh", stringBuilder.toString());
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } /*finally {
            //assets.close();
        }*/
        return "error";
    }

    public String getJson(Context context,String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    // public TabContentBean genContentWithLocalJson() {
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     TabContentBean bean =null;
    //     try {
    //          bean = objectMapper.readValue(loadLocalJson(), TabContentBean.class);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return  bean;
    // }
}
