package com.bing.lan.comm.date;

import android.text.TextUtils;

import com.bing.lan.comm.utils.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: yxhuang
 * Date: 2017/4/1
 * Email: yxhuang@gmail.com
 */

public class TimeUtil {

    public enum TimePattern {
        //年月日时分秒
        ALL("yyyy-MM-dd HH:mm:ss"),
        //年月日
        YEAR_MONTH_DAY("yyyy-MM-dd"),
        //时分
        HOURS_MIN("HH:mm"),
        //月日时分
        MONTH_DAY_HOUR_MIN("MM-dd HH:mm"),
        //年月
        YEAR_MONTH("yyyy-MM"),
        //年月日时分
        YEAR_MONTH_DAY_HOUR_MIN("yyyy-MM-dd HH:mm");

        private final String pattern;

        TimePattern(String pattern) {
            this.pattern = pattern;
        }

        public String getTimePattern() {
            return pattern;
        }
    }

    protected static final LogUtil log = LogUtil.getLogUtil(TimeUtil.class, LogUtil.LOG_VERBOSE);

    /**
     * 转换成时间
     */
    public static String time(String originTime) {
        if (!TextUtils.isEmpty(originTime)) {
            return timeByPattern(Long.valueOf(originTime), TimePattern.ALL);
        }
        return "";
    }

    /**
     * 转换成时间
     */
    public static String LongToTime(long originTime) {
        if (originTime != 0L) {
            return timeByPattern(originTime, TimePattern.ALL);
        }
        return "";
    }

    public static boolean diffTime(String data1, String data2) {
        try {
            long time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data1).getTime();
            long time2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data2).getTime();
            if (time1 >= time2) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 转换成时间
     */
    public static String timeByPattern(Long originTime, TimePattern pattern) {
        try {
            if (originTime != 0L) {
                SimpleDateFormat format = new SimpleDateFormat(pattern.getTimePattern());
                return format.format(originTime);
            }
        } catch (Exception e) {
            log.e("timeByPattern():  " + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     * String转为时间戳
     */
    public static long longTime(String time) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sd.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * String转为时间戳
     */
    public static long longTime(String time, TimePattern pattern) {
        SimpleDateFormat sd = new SimpleDateFormat(pattern.getTimePattern());
        try {
            return sd.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * yyyy-MM-dd HH:mm:ss 转换 yyyy-MM-dd
     */
    public static String NoHour(String time) {
        Date d;
        String newTime = "";
        try {
            d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            newTime = new SimpleDateFormat("yyyy-MM-dd").format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newTime;
    }

    public static String allTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 获取当前日期的前七天
     */
    public static String[] getCurrentDayBeforeSevenDay(int dayNum) {
        String[] days = new String[dayNum];
        Calendar calendar = Calendar.getInstance();
        for (int i = dayNum - 1; i > -1; i--) {
            calendar.add(Calendar.DATE, -1);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);

            days[i] = month + "月" + day + "日";

            //PayLog.i("TimeUtil", "getCurrentDayBeforeSevenDay i " + i + "  " + days[i] );
        }

        return days;
    }
}
