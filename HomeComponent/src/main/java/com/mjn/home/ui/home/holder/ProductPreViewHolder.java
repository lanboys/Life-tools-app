package com.mjn.home.ui.home.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.home.R;
import com.mjn.home.ui.home.adapter.HomeRecyclerAdapter;
import com.mjn.libs.comm.bean.IHomeItemBean;

/**
 * Created by 蓝兵 on 2018/3/26.
 */

public class ProductPreViewHolder extends BaseViewHolder<IHomeItemBean> {

    HomeRecyclerAdapter.OnHomeClickCallBack mOnHomeClickCallBack;
    private LinearLayout mHomeProductRoot;
    private TextView mHomeProductLixi;
    private TextView mHomeProductLixi2;
    private TextView mHomeProductDay;
    private TextView mHomeProductLeftMoney;
    private TextView mHomeProductLowmoney;

    public ProductPreViewHolder(View convertView, HomeRecyclerAdapter.OnHomeClickCallBack onHomeClickCallBack) {
        super(convertView);
        mOnHomeClickCallBack = onHomeClickCallBack;


        mHomeProductRoot = (LinearLayout) convertView.findViewById(R.id.home_product_root);
        mHomeProductLixi = (TextView) convertView.findViewById(R.id.home_product_lixi);
        mHomeProductLixi2 = (TextView) convertView.findViewById(R.id.home_product_lixi2);
        mHomeProductDay = (TextView) convertView.findViewById(R.id.home_product_day);
        mHomeProductLeftMoney = (TextView) convertView.findViewById(R.id.home_product_leftMoney);
        mHomeProductLowmoney = (TextView) convertView.findViewById(R.id.home_product_lowmoney);
    }

    @Override
    public void fillData(IHomeItemBean data, int position) {

    }

    private void initView() {
    }
}
