package com.bing.lan.comm.picker;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bing.lan.comm.picker.bean.TimeBean;
import com.bing.lan.comm.utils.loader.AssetsLoader;

import java.util.ArrayList;

public class MyTimePickerUtil {

    PickerItemSelectListener mListener;

    private ArrayList<TimeBean.TimeListBean> options1Items1 = new ArrayList<>();
    private ArrayList<TimeBean.TimeListBean> options1Items2 = new ArrayList<>();

    private OptionsPickerView pvOptions;

    public MyTimePickerUtil(PickerItemSelectListener listener) {
        mListener = listener;
    }

    public void setListener(PickerItemSelectListener listener) {
        mListener = listener;
    }

    public void selectType(Activity activity) {
        if (pvOptions == null) {
            initOptionPicker(activity);
        }

        if (pvOptions != null) {
            pvOptions.show();
        }
    }

    private void initOptionPicker(Activity activity) {//条件选择器初始化

        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */
        //initOptionData(activity);
        initOptionData1(activity);
        pvOptions = new OptionsPickerView.Builder(activity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                //String tx = options1Items.get(options1).getPickerViewText()
                //        + options2Items.get(options1).get(options2)
                //        + options3Items.get(options1).get(options2).get(options3);
                // btn_Options.setText(tx);

                //if (mListener != null) {
                //    mListener.onTimePickerSelect(
                //            options1Items.get(options1).getPickerViewText(),
                //            options2Items.get(options1).get(options2),
                //
                //            options3Items.get(options1).get(options2).get(options3),
                //            v);
                //}

                if (mListener != null) {
                    mListener.onMyTimePickSelect(
                            options1Items1,
                            options1Items2,
                            options1,
                            options2,

                            v);
                }
            }
        })
                .setTitleText("营业时间")
                .setContentTextSize(20)//设置滚轮文字大小
                //.setDividerColor(Color.GREEN)//设置分割线的颜色
                .setSelectOptions(0, 0, 0)//默认选中项
                //.setBgColor(Color.BLACK)
                //.setTitleBgColor(Color.DKGRAY)
                //.setTitleColor(Color.LTGRAY)
                .setCancelColor(0xff0195ff)
                .setSubmitColor(0xff0195ff)
                //.setTextColorCenter(Color.LTGRAY)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                // .setLabels("省", "市", "区")
                .build();

        //pvOptions.setSelectOptions(1,1);

        //pvOptions.setPicker(options1Items1);//一级选择器
        //pvOptions.setPicker(options1Items, options2Items);//二级选择器
        //pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        //pvOptions.setPicker(options1Items1, options2Items1, options3Items1);//三级选择器

        pvOptions.setNPicker(options1Items1, options1Items1, null);
        pvOptions.setSelectOptions(8, 20);

        // 不联动的多级选项
        /*pvOptions.setNPicker(options1Items, options2Items,options3Items);//三级选择器*/

    }

    public void setSelectOptions(int option1, int option2) {
        if (pvOptions != null) {
            pvOptions.setSelectOptions(option1, option2);
        }
    }

    private void initOptionData1(Activity activity) {
        String json = AssetsLoader.loadJsonString(activity, "time.json");
        TimeBean categoryBean = TimeBean.objectFromData(json);
        options1Items1 = categoryBean.getTimeList();

        TimeBean categoryBean1 = TimeBean.objectFromData(json);
        options1Items2 = categoryBean1.getTimeList();
    }

    public interface PickerItemSelectListener {

        void onMyTimePickSelect(
                ArrayList<TimeBean.TimeListBean> options1Items1,
                ArrayList<TimeBean.TimeListBean> options1Items2,
                int options1,
                int options2,
                View v);
    }
}
