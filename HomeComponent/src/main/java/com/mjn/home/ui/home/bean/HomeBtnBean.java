package com.mjn.home.ui.home.bean;

import com.mjn.libs.comm.bean.IHomeItemBean;

/**
 * Created by 蓝兵 on 2018/3/26.
 */

public class HomeBtnBean implements IHomeItemBean {

    private int homeBeanType;

    // 平台简介url
    private String platformDescUrl;

    public HomeBtnBean(String platformDescUrl, int homeBeanType) {
        this.platformDescUrl = platformDescUrl;
        this.homeBeanType = homeBeanType;
    }

    @Override
    public int getHomeBeanType() {
        return homeBeanType;
    }

    @Override
    public void setHomeBeanType(@HomeBeanType int homeBeanType) {
        this.homeBeanType = homeBeanType;
    }

    @Override
    public String toString() {
        return "HomeBtnBean{" +
                "homeBeanType=" + homeBeanType +
                ", platformDescUrl='" + platformDescUrl + '\'' +
                '}';
    }

    public String getPlatformDescUrl() {
        return platformDescUrl;
    }

    public void setPlatformDescUrl(String platformDescUrl) {
        this.platformDescUrl = platformDescUrl;
    }
}
