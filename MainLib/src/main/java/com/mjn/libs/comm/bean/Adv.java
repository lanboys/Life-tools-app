package com.mjn.libs.comm.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/16.
 */

public class Adv implements Serializable, IHomeItemBean {

    /**
     * advId : 2
     * advTxt : 注册就送888元红包啊
     * picUrl : http://ofbumd4go.bkt.clouddn.com/zhucejisong.jpg
     * destUrl : http://59.110.171.181/activity/register_achieve_money
     * needLogin : 0
     */
    private int homeBeanType;

    @Override
    public void setHomeBeanType(@HomeBeanType int homeBeanType) {
        this.homeBeanType = homeBeanType;
    }

    @Override
    public int getHomeBeanType() {
        return homeBeanType;
    }

    private String advId;
    private String advTxt;
    private String picUrl;
    private String destUrl;
    private int needLogin;

    public String getAdvId() {
        return advId;
    }

    public void setAdvId(String advId) {
        this.advId = advId;
    }

    public String getAdvTxt() {
        return advTxt;
    }

    public void setAdvTxt(String advTxt) {
        this.advTxt = advTxt;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDestUrl() {
        return destUrl;
    }

    public void setDestUrl(String destUrl) {
        this.destUrl = destUrl;
    }

    public int getNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(int needLogin) {
        this.needLogin = needLogin;
    }
}

