package com.bing.lan.comm.picker.bean;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;

/**
 * @author 蓝兵
 * @email lan_bing2013@163.com
 * @time 2017/5/26  21:34
 */
public class AreaBean implements IPickerViewData {

    /**
     * code : 110101
     * level : 3
     * name : 东城区
     */

    private String code;
    private int level;
    private String name;

    public AreaBean() {
    }

    public AreaBean(String code, int level, String name) {
        this.code = code;
        this.level = level;
        this.name = name;
    }

    public static AreaBean objectFromData(String str) {

        return new Gson().fromJson(str, AreaBean.class);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
