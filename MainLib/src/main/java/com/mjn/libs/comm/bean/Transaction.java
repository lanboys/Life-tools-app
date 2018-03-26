package com.mjn.libs.comm.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */


public class Transaction implements Serializable {
    private Date tradingTime;
    private String type;
    private Long amount;

    public Transaction() {
    }

    public void setTradingTime(Date tradingTime) {
        this.tradingTime = tradingTime;
    }

    public Date getTradingTime() {
        return this.tradingTime;
    }

    public void setType(String type) {
        if (type != null) {
            this.type = type;
        }
    }

    public String getType() {
        return this.type;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return this.amount;
    }
}

