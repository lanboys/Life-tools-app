package com.mjn.libs.comm.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.*;

/**
 * Created by 蓝兵 on 2018/3/24.
 */

public interface IHomeItemBean {

    int getHomeBeanType();

    void setHomeBeanType(@HomeBeanType int homeBeanType);

    @IntDef({
            HOME_ITEM_TYPE_BANNER,
            HOME_ITEM_TYPE_BTN,
            HOME_ITEM_TYPE_PRODUCT,
            HOME_ITEM_TYPE_BOTTOM,
            HOME_ITEM_TYPE_PRE_PRODUCT,
            HOME_ITEM_TYPE_BOTTOM_GUIDE,
            HOME_ITEM_TYPE_HONG_BAO_QIAN_DAO
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HomeBeanType {

        int HOME_ITEM_TYPE_BANNER = 0;//首页广告
        int HOME_ITEM_TYPE_BTN = 1;//按钮布局
        int HOME_ITEM_TYPE_PRODUCT = 2;//首页标的
        int HOME_ITEM_TYPE_BOTTOM = 3;//首页底部
        int HOME_ITEM_TYPE_PRE_PRODUCT = 4;//首页底部
        int HOME_ITEM_TYPE_BOTTOM_GUIDE = 5;//首页底部
        int HOME_ITEM_TYPE_HONG_BAO_QIAN_DAO = 6;//红包签到布局
    }
}
