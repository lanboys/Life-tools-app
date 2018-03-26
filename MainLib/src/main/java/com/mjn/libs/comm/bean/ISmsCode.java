package com.mjn.libs.comm.bean;

import java.io.Serializable;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class ISmsCode implements Serializable {
    private String isAuthIDCard;

    public ISmsCode() {
    }

    public void setIsAuthIDCard(String isAuthIDCard) {
        if (isAuthIDCard != null) {
            this.isAuthIDCard = isAuthIDCard;
        }
    }

    public String getIsAuthIDCard() {
        return this.isAuthIDCard;
    }
}