package com.bing.lan.comm.picker;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bing.lan.comm.utils.loader.AssetsLoader;
import com.bing.lan.comm.picker.bean.AreaBean;
import com.bing.lan.comm.picker.bean.ChinaAreaBean;
import com.bing.lan.comm.picker.bean.CityBean;
import com.bing.lan.comm.picker.bean.ProvinceBean;

import java.util.ArrayList;

public class AreaPickerUtil {

    PickerItemSelectListener mListener;
    private ArrayList<TestBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private OptionsPickerView pvOptions;
    // private ArrayList<CategoryFirst> options1Items1 = new ArrayList<>();
    // private ArrayList<ArrayList<CategorySecond>> options2Items1 = new ArrayList<>();
    // private ArrayList<ArrayList<ArrayList<CategoryThird>>> options3Items1 = new ArrayList<>();
    private ArrayList<ProvinceBean> options1Items2 = new ArrayList<>();
    private ArrayList<ArrayList<CityBean>> options2Items2 = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<AreaBean>>> options3Items2 = new ArrayList<>();

    public AreaPickerUtil(PickerItemSelectListener listener) {
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

                if (mListener != null) {
                    mListener.onAreaPickSelect(
                            options1Items2, options2Items2, options3Items2,
                            options1, options2, options3, v);
                }
            }
        })
                .setTitleText("选择省市区")
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

        /*pvOptions.setPicker(options1Items);//一级选择器*/
        //pvOptions.setPicker(options1Items, options2Items);//二级选择器
        //pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.setPicker(options1Items2, options2Items2, options3Items2);//三级选择器

        // 不联动的多级选项
        /*pvOptions.setNPicker(options1Items, options2Items,options3Items);//三级选择器*/

    }

    private void initOptionData1(Activity activity) {
        String json = AssetsLoader.loadJsonString(activity, "area-code.json");
        ChinaAreaBean chinaAreaBean = ChinaAreaBean.objectFromData(json);

        options1Items2 = chinaAreaBean.getChina();

        for (ProvinceBean provinceBean : options1Items2) {

            ArrayList<CityBean> child = provinceBean.getChild();
            if (child == null) {
                child = new ArrayList<>();
            }
            options2Items2.add(child);
            ArrayList<ArrayList<AreaBean>> lists = new ArrayList<>();
            options3Items2.add(lists);
            for (CityBean cityBean : child) {
                ArrayList<AreaBean> child1 = cityBean.getChild();

                if (child1 == null) {
                    child1 = new ArrayList<>();
                    child1.add(new AreaBean("0", 3, ""));
                }
                lists.add(child1);
            }
        }

        // CategoryBean categoryBean = CategoryBean.objectFromData(json);
        // options1Items1 = categoryBean.getData();
        // for (CategoryFirst first : options1Items1) {
        //     ArrayList<CategorySecond> secondList = first.getChildClassify();
        //     if (secondList == null) {
        //         secondList = new ArrayList<>();
        //     }
        //
        //     options2Items1.add(secondList);
        //
        //     ArrayList<ArrayList<CategoryThird>> lists = new ArrayList<>();
        //     options3Items1.add(lists);
        //
        //     for (CategorySecond second : secondList) {
        //         ArrayList<CategoryThird> thirdList = second.getChildClassify();
        //
        //         if (thirdList == null) {
        //             thirdList = new ArrayList<>();
        //             thirdList.add(new CategoryThird(0, 3, ""));
        //         }
        //         lists.add(thirdList);
        //     }
        // }
    }

    public interface PickerItemSelectListener {

        void onAreaPickSelect(ArrayList<ProvinceBean> options1Items1,
                ArrayList<ArrayList<CityBean>> options2Items1,
                ArrayList<ArrayList<ArrayList<AreaBean>>> options3Items1,
                int options1, int options2, int options3, View v);
    }

    // String pickerViewText = options1Items1.get(options1).getPickerViewText();
    // String pickerViewText1 = options2Items1.get(options1).get(options2).getPickerViewText();
    // String pickerViewText2 = options3Items1.get(options1).get(options2).get(options3).getPickerViewText();
    //
    // showToast(pickerViewText + pickerViewText1 + pickerViewText2);
}
