package com.mjn.libs.comm.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.mjn.libs.comm.bean.ISelectDiscountItemBean.SelectDiscountBeanType.DISCOUNT_ITEM_TYPE_COUPON;
import static com.mjn.libs.comm.bean.ISelectDiscountItemBean.SelectDiscountBeanType.DISCOUNT_ITEM_TYPE_RED_PACKAGE;

/**
 * Created by 蓝兵 on 2018/3/24.
 */

public interface ISelectDiscountItemBean {

    int getSelectDiscountBeanType();

    void setSelectDiscountBeanType(@SelectDiscountBeanType int homeBeanType);

    @IntDef({
            DISCOUNT_ITEM_TYPE_COUPON,
            DISCOUNT_ITEM_TYPE_RED_PACKAGE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface SelectDiscountBeanType {

        int DISCOUNT_ITEM_TYPE_COUPON = 0;//优惠券
        int DISCOUNT_ITEM_TYPE_RED_PACKAGE = 1;//红包
    }
}
