package com.bing.lan.comm.picker.bean;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * @author 蓝兵
 * @email lan_bing2013@163.com
 * @time 2017/5/26  21:33
 */
public class ProvinceBean implements IPickerViewData {

    /**
     * code : 110000
     * level : 1
     * name : 北京
     * child : [{"code":"110100","level":2,"name":"北京市","child":[{"code":"110101","level":3,"name":"东城区"},{"code":"110102","level":3,"name":"西城区"},{"code":"110105","level":3,"name":"朝阳区"},{"code":"110106","level":3,"name":"丰台区"},{"code":"110107","level":3,"name":"石景山区"},{"code":"110108","level":3,"name":"海淀区"},{"code":"110109","level":3,"name":"门头沟区"},{"code":"110111","level":3,"name":"房山区"},{"code":"110112","level":3,"name":"通州区"},{"code":"110113","level":3,"name":"顺义区"},{"code":"110114","level":3,"name":"昌平区"},{"code":"110115","level":3,"name":"大兴区"},{"code":"110116","level":3,"name":"怀柔区"},{"code":"110117","level":3,"name":"平谷区"},{"code":"110228","level":3,"name":"密云县"},{"code":"110229","level":3,"name":"延庆县"}]}]
     */

    private String code;
    private int level;
    private String name;
    private ArrayList<CityBean> child;

    public static ProvinceBean objectFromData(String str) {

        return new Gson().fromJson(str, ProvinceBean.class);
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

    public ArrayList<CityBean> getChild() {
        return child;
    }

    public void setChild(ArrayList<CityBean> child) {
        this.child = child;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
