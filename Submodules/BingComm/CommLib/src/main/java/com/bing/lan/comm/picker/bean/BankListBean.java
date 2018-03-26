package com.bing.lan.comm.picker.bean;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by win7 on 2017/5/8.
 */
public class BankListBean {

    private ArrayList<BanksBean> banks;

    public static BankListBean objectFromData(String str) {

        return new Gson().fromJson(str, BankListBean.class);
    }

    public ArrayList<BanksBean> getBanks() {
        return banks;
    }

    public void setBanks(ArrayList<BanksBean> banks) {
        this.banks = banks;
    }

    public static class BanksBean implements IPickerViewData {

        /**
         * abbreviation : SRCB
         * fullName : 深圳农村商业银行
         */

        private String abbreviation;
        private String fullName;

        public BanksBean(String abbreviation, String fullName) {
            this.abbreviation = abbreviation;
            this.fullName = fullName;
        }

        public BanksBean() {
        }

        public static BanksBean objectFromData(String str) {

            return new Gson().fromJson(str, BanksBean.class);
        }


        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        @Override
        public String getPickerViewText() {
            return fullName;
        }
    }
}
