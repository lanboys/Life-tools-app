package com.bing.lan.comm.picker.bean;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by 蓝兵 on 2017/8/2.
 */

public class TimeBean {

    private ArrayList<TimeListBean> timeList;

    public static TimeBean objectFromData(String str) {

        return new Gson().fromJson(str, TimeBean.class);
    }

    public ArrayList<TimeListBean> getTimeList() {
        return timeList;
    }

    public void setTimeList(ArrayList<TimeListBean> timeList) {
        this.timeList = timeList;
    }

    public static class TimeListBean implements IPickerViewData {

        /**
         * longTime : 1262304000000
         * time : 00
         */

        private Long longTime;
        private String time;

        public Long getLongTime() {
            return longTime;
        }

        public void setLongTime(Long longTime) {
            this.longTime = longTime;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String getPickerViewText() {
            return time + ":00";
        }
    }
}
