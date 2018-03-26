package com.mjn.libs.comm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 * 首页顶部广告，以及测试标
 * {
 * "data": {
 * "number": 0,
 * "size": 0,
 * "totalPages": 0,
 * "list": [{
 * "bannerList": [{
 * "advId": "2",
 * "advTxt": "注册就送888元红包啊",
 * "picUrl": "http://ofbumd4go.bkt.clouddn.com/zhucejisong.jpg",
 * "destUrl": "http://59.110.171.181/activity/register_achieve_money"
 * }, {
 * "advId": "6",
 * "advTxt": "新手专享15%收益率",
 * "picUrl": "http://ofbumd4go.bkt.clouddn.com/banner3X.png",
 * "destUrl": "http://59.110.171.181:8081/api/webpage/safe.html"
 * }],
 * "preferenceList": [{
 * "productId": 86,
 * "categoryId": 2,
 * "title": "谢东测试标",
 * "annualYield": 12.0,
 * "addYield": 2.0,
 * "minInvestment": 100000,
 * "financialPeriod": "1个月",
 * "status": "PUBLISHING",
 * "joinDateUnix": 1504831251,
 * "endDateUnix": 1505490053,
 * "remainAmount": 141800000
 * }],
 * "firstUserList": [{
 * "productId": 73,
 * "categoryId": 4,
 * "title": "旺旺财(新手标)",
 * "annualYield": 18.0,
 * "addYield": 8.0,
 * "minInvestment": 100000,
 * "financialPeriod": "1个月",
 * "status": "PUBLISHING",
 * "joinDateUnix": 1501502081,
 * "endDateUnix": 1501847683,
 * "remainAmount": 199900000
 * }]
 * }]
 * },
 * "code": "0",
 * "servicetime": 1508137049562
 * }
 */

public class Home implements Serializable, IHomeItemBean {

    private List<Adv> bannerList;
    private List<Adv> bottomList;
    private List<IProduct> preferenceList;
    private List<IProduct> firstUserList;
    private String platformDescUrl;

    @Override
    public String toString() {
        return "Home{" +
                "bannerList=" + bannerList +
                ", bottomList=" + bottomList +
                ", preferenceList=" + preferenceList +
                ", firstUserList=" + firstUserList +
                ", platformDescUrl='" + platformDescUrl + '\'' +
                ", homeBeanType=" + homeBeanType +
                '}';
    }

    public Home() {
    }

    public String getPlatformDescUrl() {
        return platformDescUrl;
    }

    public void setPlatformDescUrl(String platformDescUrl) {
        this.platformDescUrl = platformDescUrl;
    }

    public void setBannerList(List<Adv> bannerList) {
        this.bannerList = bannerList;
    }

    public List<Adv> getBannerList() {
        return this.bannerList;
    }

    public void setBottomList(List<Adv> bottomList) {
        this.bottomList = bottomList;
    }

    public List<Adv> getBottomList() {
        return bottomList;
    }

    public void setPreferenceList(List<IProduct> preferenceList) {
        this.preferenceList = preferenceList;
    }

    public List<IProduct> getPreferenceList() {
        return this.preferenceList;
    }

    public void setFirstUserList(List<IProduct> firstUserList) {
        this.firstUserList = firstUserList;
    }

    public List<IProduct> getFirstUserList() {
        return this.firstUserList;
    }

    private int homeBeanType;

    @Override
    public void setHomeBeanType(@HomeBeanType int homeBeanType) {
        this.homeBeanType = homeBeanType;
    }

    @Override
    public int getHomeBeanType() {
        return homeBeanType;
    }
}

