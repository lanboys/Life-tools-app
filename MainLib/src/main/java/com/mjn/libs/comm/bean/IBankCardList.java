package com.mjn.libs.comm.bean;


import java.io.Serializable;
import java.util.List;

/**
 * @auther zhuwengang
 * @tiem 2017/11/29
 */

public class IBankCardList implements Serializable {
    private List<IBankCard> bankCardList;
    private String supportBanks;
    private List<Quota> quotas;

    public IBankCardList() {
    }

    public void setBankCardList(List<IBankCard> bankCardList) {
        this.bankCardList = bankCardList;
    }

    public List<IBankCard> getBankCardList() {
        return this.bankCardList;
    }

    public void setSupportBanks(String supportBanks) {
        if (supportBanks != null) {
            this.supportBanks = supportBanks;
        }
    }

    public String getSupportBanks() {
        return this.supportBanks;
    }

    public void setQuotas(List<Quota> quotas) {
        this.quotas = quotas;
    }

    public List<Quota> getQuotas() {
        return this.quotas;
    }
}
