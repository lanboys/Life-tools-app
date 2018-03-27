package com.mjn.invest.ui.invest.holder;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.invest.R;
import com.mjn.invest.ui.invest.adapter.InvestRecyclerAdapter;
import com.mjn.libs.comm.bean.IProduct;
import com.mjn.libs.utils.AppSpDataUtil;

/**
 * Created by 蓝兵 on 2018/3/27.
 */

public class InvestTopHolder extends BaseViewHolder<IProduct> {

    InvestRecyclerAdapter.OnInvestClickCallBack mOnHomeClickCallBack;
    private RelativeLayout mHomeProductRoot;
    private TextView mTvTitleXinshoumingcheng;
    private LinearLayout mLlRootview;
    private TextView mHomeProductTitle;
    private TextView mTvTitleJinxianyici;
    private TextView mHomeProductLixi;
    private TextView mHomeProductLixi2;
    private TextView mTvTimestring;
    private TextView mTvTime;
    private TextView mHomeProductBtn;
    private ImageView mHomeProductStatus;
    private TextView mTvTitleJingxuanbiao;

    public InvestTopHolder(View convertView, InvestRecyclerAdapter.OnInvestClickCallBack onHomeClickCallBack) {
        super(convertView);
        this.mOnHomeClickCallBack = onHomeClickCallBack;
        //itemView.findViewById(R.layout.invest_topitem_product);

        mHomeProductRoot = (RelativeLayout) convertView.findViewById(R.id.home_product_root);
        mTvTitleXinshoumingcheng = (TextView) convertView.findViewById(R.id.tv_title_xinshoumingcheng);
        mLlRootview = (LinearLayout) convertView.findViewById(R.id.ll_rootview);
        mHomeProductTitle = (TextView) convertView.findViewById(R.id.home_product_title);
        mTvTitleJinxianyici = (TextView) convertView.findViewById(R.id.tv_title_jinxianyici);
        mHomeProductLixi = (TextView) convertView.findViewById(R.id.home_product_lixi);
        mHomeProductLixi2 = (TextView) convertView.findViewById(R.id.home_product_lixi2);
        mTvTimestring = (TextView) convertView.findViewById(R.id.tv_timestring);
        mTvTime = (TextView) convertView.findViewById(R.id.tv_time);
        mHomeProductBtn = (TextView) convertView.findViewById(R.id.home_product_btn);
        mHomeProductStatus = (ImageView) convertView.findViewById(R.id.home_product_status);
        mTvTitleJingxuanbiao = (TextView) convertView.findViewById(R.id.tv_title_jingxuanbiao);
    }

    @Override
    public void fillData(final IProduct product, int position) {

        if (product == null) {
            return;
        }
        // 仅限一次是否显示，显示新手专享或者新手推荐
        if (product.getCategoryId() == 4) {
            mTvTitleJinxianyici.setVisibility(View.VISIBLE);
            mTvTitleXinshoumingcheng.setText("新手专享");
        } else {
            mTvTitleJinxianyici.setVisibility(View.GONE);
            mTvTitleXinshoumingcheng.setText("新手推荐");
        }

        // 顶部标的名称
        if (!TextUtils.isEmpty(product.getTitle())) {
            mHomeProductTitle.setText(product.getTitle());
        }
        // 年化收益率
        mHomeProductLixi.setText(String.valueOf(product.getAnnualYield()) + "%");
        // 投资期限，投资时间
        if (product.getFinancialPeriod().contains("个月")) {
            mTvTime.setText(product.getFinancialPeriod().replaceAll("个月", ""));
            mTvTimestring.setText("个月");
        } else if (product.getFinancialPeriod().contains("年")) {
            mTvTime.setText(product.getFinancialPeriod().replaceAll("年", ""));
            mTvTimestring.setText("年");
        } else if (product.getFinancialPeriod().contains("天")) {
            mTvTime.setText(product.getFinancialPeriod().replaceAll("天", ""));
            mTvTimestring.setText("天");
        }

        long joinDate = ((long) product.getJoinDateUnix() * (long) 1000);
        long endDate = (long) product.getEndDateUnix() * (long) 1000;

        // 立即购买
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
            mHomeProductBtn.setText("立即购买");
        }

        mHomeProductRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("productId", product.getProductId());
                bundle.putString("title", product.getTitle());
                if (mOnHomeClickCallBack != null) {
                    mOnHomeClickCallBack.onClickToInvestPager(bundle);
                }
            }
        });
    }
}


