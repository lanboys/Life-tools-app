package com.mjn.libs.comm.bean;

import java.io.Serializable;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */
public class Order implements Serializable {
    private Integer orderId;
    private String phone;

    public Order() {
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setPhone(String phone) {
        if (phone != null) {
            this.phone = phone;
        }
    }

    public String getPhone() {
        return this.phone;
    }
}