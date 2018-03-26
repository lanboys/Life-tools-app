package com.bing.lan.comm.picker.bean;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by win7 on 2017/5/8.
 */
public class UserRoleListBean {

    private ArrayList<UserRoleBean> list;

    public static UserRoleListBean objectFromData(String str) {

        return new Gson().fromJson(str, UserRoleListBean.class);
    }

    public ArrayList<UserRoleBean> getList() {
        return list;
    }

    public void setList(ArrayList<UserRoleBean> list) {
        this.list = list;
    }

    public static class UserRoleBean implements IPickerViewData {

        /**
         * abbreviation : SRCB
         * fullName : 深圳农村商业银行
         */

        private String level;
        private String fullName;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public static UserRoleBean objectFromData(String str) {

            return new Gson().fromJson(str, UserRoleBean.class);
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
