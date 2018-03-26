package com.bing.lan.comm.picker.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;

/**
 * Created by win7 on 2017/5/2.
 */
public class CategorySecond implements IPickerViewData {

    @Override
    public String toString() {
        return "CategorySecond{" +
                "categoryId=" + categoryId +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", childClassify=" + childClassify +
                '}';
    }

    /**
     * categoryId : 1001
     * childClassify : [{"categoryId":1001001,"level":3,"name":"中餐"},{"categoryId":1001002,"level":3,"name":"西餐"},{"categoryId":1001003,"level":3,"name":"日韩菜"},{"categoryId":1001004,"level":3,"name":"火锅烧烤"},{"categoryId":1001005,"level":3,"name":"海鲜"},{"categoryId":1001006,"level":3,"name":"风味特色菜"},{"categoryId":1001007,"level":3,"name":"创意菜"}]
     * level : 2
     * name : 高档餐厅
     */

    private int categoryId;
    private int level;
    private String name;
    private ArrayList<CategoryThird> childClassify;

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

    public ArrayList<CategoryThird> getChildClassify() {
        return childClassify;
    }

    public void setChildClassify(ArrayList<CategoryThird> childClassify) {
        this.childClassify = childClassify;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
