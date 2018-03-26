package com.mjn.home.ui.home.bean;

import com.mjn.libs.comm.bean.Adv;
import com.mjn.libs.comm.bean.IHomeItemBean;

import java.util.List;

/**
 * Created by 蓝兵 on 2018/3/24.
 */

public class BannerBean implements IHomeItemBean {

    private List<Adv> bannerList;
    private int homeBeanType;

    public BannerBean(List<Adv> bannerList, int homeBeanType) {
        this.bannerList = bannerList;
        this.homeBeanType = homeBeanType;
    }

    @Override
    public void setHomeBeanType(@HomeBeanType int homeBeanType) {
        this.homeBeanType = homeBeanType;
    }

    @Override
    public int getHomeBeanType() {
        return homeBeanType;
    }

    public List<Adv> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Adv> bannerList) {
        this.bannerList = bannerList;
    }
}
