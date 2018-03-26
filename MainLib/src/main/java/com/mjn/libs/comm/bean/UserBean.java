package com.mjn.libs.comm.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/8.
 */

public class UserBean implements Serializable {


    /**
     * coupon :
     * token : 1cb4ef0f53614a94a17f4775014db4be
     * userId : 4809
     */

    private String coupon;
    private String token;
    private long userId;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        if (coupon != null) {
            this.coupon = coupon;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if (token != null) {
            this.token = token;
        }
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
