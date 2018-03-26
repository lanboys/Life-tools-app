package com.mjn.libs.comm.bean;

import java.io.Serializable;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class UserSignDateInfo implements Serializable {
    private int date;
    private int signStatus;
    private String award10Url;
    private String award18Url;

    public UserSignDateInfo() {
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getDate() {
        return this.date;
    }

    public void setSignStatus(int signStatus) {
        this.signStatus = signStatus;
    }

    public int getSignStatus() {
        return this.signStatus;
    }

    public void setAward10Url(String award10Url) {
        if (award10Url != null) {
            this.award10Url = award10Url;
        }
    }

    public String getAward10Url() {
        return this.award10Url;
    }

    public void setAward18Url(String award18Url) {
        if (award18Url != null) {
            this.award18Url = award18Url;
        }
    }

    public String getAward18Url() {
        return this.award18Url;
    }
}
