package com.bing.lan.comm.picker.bean;

import java.util.ArrayList;

/**
 * Created by win7 on 2017/5/2.
 */
public class CategoryBean {

    private ArrayList<CategoryFirst> data;

    @Override
    public String toString() {
        return "CategoryBean{" +
                "data=" + data +
                '}';
    }

    public static CategoryBean objectFromData(String str) {

        return new com.google.gson.Gson().fromJson(str, CategoryBean.class);
    }

    public ArrayList<CategoryFirst> getData() {
        return data;
    }

    public void setData(ArrayList<CategoryFirst> data) {
        this.data = data;
    }
}
