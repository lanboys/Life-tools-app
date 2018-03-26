package com.bing.lan.comm.picker;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bing.lan.comm.R;
import com.bing.lan.comm.dialog.SelectTimeBean;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * 时间选择器
 */
public class TimePickDialogFragment extends DialogFragment {

    private static final String TAG = "TimePickDialog";

    private TextView tv_time_title;
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private int currentHour;
    private int currentMinute;

    private SelectTimeBean mSelectTime = new SelectTimeBean();

    private OnSelectTimeListener mSelectTimeListener;

    public void setOnSelectTimeListener(OnSelectTimeListener listener) {
        mSelectTimeListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.fragment_time_pick_dialog, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        tv_time_title = (TextView) view.findViewById(R.id.tv_time_title);
        DatePicker date_pick = (DatePicker) view.findViewById(R.id.date_pick);
        TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_pick);
        TextView tv_conform = (TextView) view.findViewById(R.id.tv_conform);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

        timePicker.setIs24HourView(true);

        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DATE);
        currentHour = calendar.get(Calendar.HOUR);
        currentMinute = calendar.get(Calendar.MINUTE);

        date_pick.init(currentYear, currentMonth, currentDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                currentYear = year;
                currentMonth = monthOfYear;
                currentDay = dayOfMonth;
                updateTimeTitle(currentYear, currentMonth, currentDay, currentHour, currentMinute);
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                currentHour = hourOfDay;
                currentMinute = minute;
                updateTimeTitle(currentYear, currentMonth, currentDay, currentHour, currentMinute);
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectTimeListener != null) {
                    mSelectTimeListener.onSelectTimeListener(mSelectTime);
                }

                dismiss();
            }
        });
    }

    private void updateTimeTitle(int year, int month, int day, int hour, int minute) {
        Log.e(TAG, "updateTimeTitle createTime year " + year + " month: " + month + "  day: " +
                day + "  hour: " + hour + " minute: " + minute);

        tv_time_title.setText(year + "-" + month + "-" + day + "-" + hour + "-" + minute);
        mSelectTime.setYear(year);
        mSelectTime.setMonth(month);
        mSelectTime.setDay(day);
        mSelectTime.setHour(hour);
        mSelectTime.setMinute(minute);
    }

    /**
     * 设置时间选择器的分割线颜色
     *
     * @param datePicker
     */
    private void setDatePickerDividerColor(DatePicker datePicker) {
        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);

        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(0x666666));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public interface OnSelectTimeListener {

        void onSelectTimeListener(SelectTimeBean bean);
    }
}
