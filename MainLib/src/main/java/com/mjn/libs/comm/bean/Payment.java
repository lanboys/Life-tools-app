package com.mjn.libs.comm.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class Payment implements Serializable {
    private Long amount;
    private String desc;
    private String title;
    private Byte status;
    private Date paymentDate;

    public Payment() {
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return this.amount;
    }

    public void setDesc(String desc) {
        if (desc != null) {
            this.desc = desc;
        }
    }

    public String getDesc() {
        return this.desc;
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getStatus() {
        return this.status;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getPaymentDate() {
        return this.paymentDate;
    }
}

