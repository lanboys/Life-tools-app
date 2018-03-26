package com.bing.lan.comm.picker;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bing.lan.comm.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.bigkoo.pickerview.TimePickerView.Type.YEAR_MONTH_DAY;

/**
 * Created by win7 on 2017/4/20.
 */
public class TimePickerUtil {

    private TimePickerView pvCustomTime;

    public void setListener(PickerItemSelectListener listener) {
        mListener = listener;
    }

    public TimePickerUtil(PickerItemSelectListener listener) {
        mListener = listener;
    }

    public TimePickerUtil(PickerItemSelectListener listener, TimePickerView.Type mYearMonthDay) {
        mListener = listener;
        this.mYearMonthDay = mYearMonthDay;
    }

    PickerItemSelectListener mListener;

    public interface PickerItemSelectListener {

        void onTimePickerSelect(String date, View v);
    }

    public void selectTime(Context context) {
        if (pvCustomTime == null) {
            initCustomTimePicker(context);
        }

        if (pvCustomTime != null) {
            pvCustomTime.show();
        }
    }

    private void initCustomTimePicker(Context context) {

        /**
         * https://github.com/Bigkoo/Android-PickerView
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2010, 1, 1, 0, 0, 0);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2025, 12, 31, 0, 0, 0);
        //时间选择器 ，自定义布局
        TimePickerView.Builder builder = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // btn_CustomTime.setText(getTime(date));
                Log.e("bingtag", date.toString());
                //mEtiPaymentTime.setEditContent(getTime(date));

                if (mListener != null) {
                    mListener.onTimePickerSelect(getTime(date), v);
                }
            }
        })
                /*.setType(TimePickerView.PhotoFlavour.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
               /*.gravity(Gravity.RIGHT)// default is center*/
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(mYearMonthDay)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //.setDividerColor(Color.RED)
                .setDividerColor(Color.DKGRAY);

        pvCustomTime = setPickLabel(builder).build();
    }

    private TimePickerView.Type mYearMonthDay = YEAR_MONTH_DAY;

    private TimePickerView.Builder setPickLabel(TimePickerView.Builder builder) {
        switch (mYearMonthDay) {
            case YEAR_MONTH_DAY:
                builder.setLabel("年", "月", "日", "", "", "");
                break;

            case HOURS_MINS:
                builder.setLabel("", "", "", "时", "分", "");
                break;

            case ALL:
                builder.setLabel("年", "月", "日", "时", "分", "秒");
                break;
        }

        return builder;
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        String pattern = null;
        switch (mYearMonthDay) {
            case YEAR_MONTH_DAY:
                pattern = "yyyy-MM-dd";
                break;
            case HOURS_MINS:
                pattern = "HH:mm";
                return date.getTime() + "";
            default:
                pattern = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
