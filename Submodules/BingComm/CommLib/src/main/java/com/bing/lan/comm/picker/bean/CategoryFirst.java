package com.bing.lan.comm.picker.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;

/**
 * Created by win7 on 2017/5/2.
 */
public class CategoryFirst implements IPickerViewData {

    @Override
    public String toString() {
        return "CategoryFirst{" +
                "categoryId=" + categoryId +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", childClassify=" + childClassify +
                '}';
    }

    /**
     * categoryId : 1
     * childClassify : [{"categoryId":1001,"childClassify":[{"categoryId":1001001,"level":3,"name":"中餐"},{"categoryId":1001002,"level":3,"name":"西餐"},{"categoryId":1001003,"level":3,"name":"日韩菜"},{"categoryId":1001004,"level":3,"name":"火锅烧烤"},{"categoryId":1001005,"level":3,"name":"海鲜"},{"categoryId":1001006,"level":3,"name":"风味特色菜"},{"categoryId":1001007,"level":3,"name":"创意菜"}],"level":2,"name":"高档餐厅"},{"categoryId":1002,"childClassify":[{"categoryId":1002001,"level":3,"name":"中餐"},{"categoryId":1002002,"level":3,"name":"西餐"},{"categoryId":1002003,"level":3,"name":"日韩菜"},{"categoryId":1002004,"level":3,"name":"火锅烧烤"},{"categoryId":1002005,"level":3,"name":"海鲜"},{"categoryId":1002006,"level":3,"name":"风味特色菜"},{"categoryId":1002007,"level":3,"name":"创意菜"}],"level":2,"name":"大众餐馆"},{"categoryId":1003,"childClassify":[{"categoryId":1003001,"level":3,"name":"中餐"},{"categoryId":1003002,"level":3,"name":"西餐"},{"categoryId":1003003,"level":3,"name":"日韩菜"},{"categoryId":1003004,"level":3,"name":"火锅烧烤"},{"categoryId":1003005,"level":3,"name":"海鲜"},{"categoryId":1003006,"level":3,"name":"风味特色菜"}],"level":2,"name":"速食快餐"},{"categoryId":1004,"childClassify":[{"categoryId":1004001,"level":3,"name":"中餐"},{"categoryId":1004002,"level":3,"name":"西餐"},{"categoryId":1004003,"level":3,"name":"日韩菜"},{"categoryId":1004004,"level":3,"name":"火锅烧烤"},{"categoryId":1004005,"level":3,"name":"海鲜"},{"categoryId":1004006,"level":3,"name":"风味特色菜"}],"level":2,"name":"自助餐厅"},{"categoryId":1005,"childClassify":[{"categoryId":1005001,"level":3,"name":"中餐"},{"categoryId":1005002,"level":3,"name":"西餐"},{"categoryId":1005003,"level":3,"name":"日韩菜"},{"categoryId":1005004,"level":3,"name":"火锅烧烤"},{"categoryId":1005005,"level":3,"name":"海鲜"},{"categoryId":1005006,"level":3,"name":"风味特色菜"},{"categoryId":1005007,"level":3,"name":"创意菜"}],"level":2,"name":"私房会所"},{"categoryId":1006,"childClassify":[{"categoryId":1006001,"level":3,"name":"咖啡甜品"},{"categoryId":1006002,"level":3,"name":"面包糕点"},{"categoryId":1006003,"level":3,"name":"休闲饮品"},{"categoryId":1006004,"level":3,"name":"水果生鲜"},{"categoryId":1006005,"level":3,"name":"特色小食"},{"categoryId":1006006,"level":3,"name":"其它"}],"level":2,"name":"小食饮品"}]
     * level : 1
     * name : 餐饮食品
     */

    private int categoryId;
    private int level;
    private String name;
    private ArrayList<CategorySecond> childClassify;

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

    public ArrayList<CategorySecond> getChildClassify() {
        return childClassify;
    }

    public void setChildClassify(ArrayList<CategorySecond> childClassify) {
        this.childClassify = childClassify;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}

