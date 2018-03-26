package com.mjn.home.ui.home.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.home.R;
import com.mjn.home.ui.home.adapter.HomeRecyclerAdapter;
import com.mjn.libs.comm.bean.IHomeItemBean;

/**
 * Created by 蓝兵 on 2018/3/26.
 */

public class ProductViewHolder extends BaseViewHolder<IHomeItemBean> {

    HomeRecyclerAdapter.OnHomeClickCallBack mOnHomeClickCallBack;
    private RelativeLayout mHomeProductRoot;
    private TextView mHomeProductTitle;
    private TextView mTvXinshouJinxianyici;
    private TextView mHomeProductLixi;
    private TextView mHomeProductLixi2;
    private TextView mHomeProductTimestirng;
    private TextView mHomeProductTime;
    private TextView mHomeProductBtn;
    private TextView mHomeProductDay;
    private TextView mHomeProductLeftMoney;
    private TextView mHomeProductStartMoney;
    private ImageView mHomeProductStatus;

    public ProductViewHolder(View convertView, HomeRecyclerAdapter.OnHomeClickCallBack onHomeClickCallBack) {
        super(convertView);
        mOnHomeClickCallBack = onHomeClickCallBack;


        mHomeProductRoot = (RelativeLayout) convertView.findViewById(R.id.home_product_root);
        mHomeProductTitle = (TextView) convertView.findViewById(R.id.home_product_title);
        mTvXinshouJinxianyici = (TextView) convertView.findViewById(R.id.tv_xinshou_jinxianyici);
        mHomeProductLixi = (TextView) convertView.findViewById(R.id.home_product_lixi);
        mHomeProductLixi2 = (TextView) convertView.findViewById(R.id.home_product_lixi2);
        mHomeProductTimestirng = (TextView) convertView.findViewById(R.id.home_product_timestirng);
        mHomeProductTime = (TextView) convertView.findViewById(R.id.home_product_time);
        mHomeProductBtn = (TextView) convertView.findViewById(R.id.home_product_btn);
        mHomeProductDay = (TextView) convertView.findViewById(R.id.home_product_day);
        mHomeProductLeftMoney = (TextView) convertView.findViewById(R.id.home_product_leftMoney);
        mHomeProductStartMoney = (TextView) convertView.findViewById(R.id.home_product_startMoney);
        mHomeProductStatus = (ImageView) convertView.findViewById(R.id.home_product_status);

    }

    @Override
    public void fillData(IHomeItemBean data, int position) {

    }

}
