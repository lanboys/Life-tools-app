package com.bing.lan.comm.picker.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by win7 on 2017/5/2.
 */
public class CategoryThird implements IPickerViewData {

    @Override
    public String toString() {
        return "CategoryThird{" +
                "categoryId=" + categoryId +
                ", level=" + level +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * categoryId : 1001001
     * level : 3
     * name : 中餐
     */

    private int categoryId;
    private int level;
    private String name;

    public CategoryThird() {
    }

    public CategoryThird(int categoryId, int level, String name) {
        this.categoryId = categoryId;
        this.level = level;
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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


