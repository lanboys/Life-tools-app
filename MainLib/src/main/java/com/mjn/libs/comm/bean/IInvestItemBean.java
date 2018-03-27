package com.mjn.libs.comm.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.mjn.libs.comm.bean.IInvestItemBean.InvestType.INVEST_TYPE_ITEM;
import static com.mjn.libs.comm.bean.IInvestItemBean.InvestType.INVEST_TYPE_TOP_ITEM;

/**
 * Created by 蓝兵 on 2018/3/24.
 */

public interface IInvestItemBean {

    int getInvestBeanType();

    void setInvestBeanType(@InvestType int investType);

    @IntDef({
            INVEST_TYPE_ITEM,
            INVEST_TYPE_TOP_ITEM
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface InvestType {

        public static final int INVEST_TYPE_ITEM = 0;//列表内容
        public static final int INVEST_TYPE_TOP_ITEM = 1;//顶部新手标布局
    }
}
