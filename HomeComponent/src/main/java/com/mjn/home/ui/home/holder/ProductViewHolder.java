package com.mjn.home.ui.home.holder;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.home.R;
import com.mjn.home.ui.home.adapter.HomeRecyclerAdapter;
import com.mjn.libs.comm.bean.IHomeItemBean;
import com.mjn.libs.comm.bean.IProduct;
import com.mjn.libs.utils.AppSpDataUtil;

import static com.mjn.libs.cons.UIRouterCons.INVEST_DETAIL_AUTOWIRED_PRODUCT_ID;
import static com.mjn.libs.cons.UIRouterCons.INVEST_DETAIL_AUTOWIRED_PRODUCT_TITLE;

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
        IProduct product = (IProduct) data;
        updateView(product);
    }

    private void updateView(final IProduct product) {
        // 新手标显示仅限一次图标
        if (product.getCategoryId() == 4) {
            mTvXinshouJinxianyici.setVisibility(View.VISIBLE);
        } else {
            mTvXinshouJinxianyici.setVisibility(View.GONE);
        }
        mHomeProductTitle.setText(product.getTitle());
        if (product.getAddYield() > 0) {
            mHomeProductLixi2.setVisibility(View.INVISIBLE);
            mHomeProductLixi.setText(product.getAnnualYield() + "%");
            mHomeProductLixi2.setText(" + " + String.valueOf(product.getAddYield()) + "%");
        } else {
            mHomeProductLixi2.setVisibility(View.INVISIBLE);
            mHomeProductLixi.setText(String.valueOf(product.getAnnualYield()) + "%");
        }
        // TODO 剩余金额 改为 起投金额
        //        productViewHolder.leftMoney.setText("剩余");
        //        productViewHolder.leftMoney.append(String.valueOf(product.getMinInvestment() / 1000));
        //        productViewHolder.leftMoney.append("元");
        //        productViewHolder.startMoney.setText(String.valueOf(product.getMinInvestment() / 1000));
        //        productViewHolder.startMoney.append("元起投");
        //        productViewHolder.day.setText(product.getFinancialPeriod());
        //        productViewHolder.day2.setText(product.getFinancialPeriod() + "");

        // 投资期限
        if (product.getFinancialPeriod().contains("个月")) {
            mHomeProductTime.setText(product.getFinancialPeriod().replaceAll("个月", ""));
            mHomeProductTimestirng.setText("个月");
        } else if (product.getFinancialPeriod().contains("年")) {
            mHomeProductTime.setText(product.getFinancialPeriod().replaceAll("年", ""));
            mHomeProductTimestirng.setText("年");
        } else if (product.getFinancialPeriod().contains("天")) {
            mHomeProductTime.setText(product.getFinancialPeriod().replaceAll("天", ""));
            mHomeProductTimestirng.setText("天");
        }

        if (product.getCategoryId() == 4) {
            mHomeProductStatus.setImageResource(R.drawable.icon_invest_newer);
        } else {
            mHomeProductStatus.setImageResource(R.drawable.icon_home_tuijian);
        }
        long joinDate = ((long) product.getJoinDateUnix() * (long) 1000);
        long endDate = (long) product.getEndDateUnix() * (long) 1000;

        if (AppSpDataUtil.getInstance().isLogined()) {
            if (!product.getStatus().equals("PUBLISHING")) {
                mHomeProductBtn.setBackgroundResource(R.drawable.bg_round_corner_rect_gray1);
                mHomeProductBtn.setText("已满标");
            } else if (joinDate > product.getServicetime()) {
                mHomeProductBtn.setBackgroundResource(R.drawable.bg_round_corner_rect_gray1);
                mHomeProductBtn.setText("未开始");
            } else if (endDate < product.getServicetime() && product.getEndDateUnix() != 0) {
                mHomeProductBtn.setBackgroundResource(R.drawable.bg_round_corner_rect_gray1);
                mHomeProductBtn.setText("已结束");
            } else {
                mHomeProductBtn.setBackgroundResource(R.drawable.bg_round_corner_rect_bluenew2);
                mHomeProductBtn.setText("立即购买");
            }
        } else {
            mHomeProductBtn.setBackgroundResource(R.drawable.bg_round_corner_rect_bluenew2);
            //            mHomeProductBtn.setText("注册领取888元红包");
            mHomeProductBtn.setText("立即购买");
        }

        mHomeProductRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(INVEST_DETAIL_AUTOWIRED_PRODUCT_ID, product.getProductId()+"");
                bundle.putString(INVEST_DETAIL_AUTOWIRED_PRODUCT_TITLE, product.getTitle());
                //Tools.pushScreen(InvestDetail.class, bundle);
                if (mOnHomeClickCallBack != null) {
                    mOnHomeClickCallBack.onClickToInvestDetailPager(bundle);
                }
            }
        });
    }
}
