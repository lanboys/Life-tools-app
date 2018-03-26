package com.mjn.libs.comm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class PayInfo implements Serializable {
    private List<Quota> quotas;
    private List<UserCoupon> coupons;
    private List<UserCoupon> luckeyMoneys;
    private List<IBankCard> bankCards;

    public PayInfo() {
    }

    public void setQuotas(List<Quota> quotas) {
        this.quotas = quotas;
    }

    public List<Quota> getQuotas() {
        return this.quotas;
    }

    public void setCoupons(List<UserCoupon> coupons) {
        this.coupons = coupons;
    }

    public List<UserCoupon> getCoupons() {
        return this.coupons;
    }

    public void setLuckeyMoneys(List<UserCoupon> luckeyMoneys) {
        this.luckeyMoneys = luckeyMoneys;
    }

    public List<UserCoupon> getLuckeyMoneys() {
        return this.luckeyMoneys;
    }

    public void setBankCards(List<IBankCard> bankCards) {
        this.bankCards = bankCards;
    }

    public List<IBankCard> getBankCards() {
        return this.bankCards;
    }
}

