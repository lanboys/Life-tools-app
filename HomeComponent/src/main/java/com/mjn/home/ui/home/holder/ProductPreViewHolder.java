package com.mjn.home.ui.home.holder;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.home.R;
import com.mjn.home.ui.home.adapter.HomeRecyclerAdapter;
import com.mjn.libs.comm.bean.IHomeItemBean;
import com.mjn.libs.comm.bean.IProduct;

import static com.mjn.libs.cons.UIRouterCons.INVEST_DETAIL_AUTOWIRED_PRODUCT_ID;
import static com.mjn.libs.cons.UIRouterCons.INVEST_DETAIL_AUTOWIRED_PRODUCT_TITLE;

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

        IProduct product = (IProduct) data;
        updateView(product);
    }

    /**
     * preferenceList 数据
     */
    private void updateView(final IProduct product) {
        if (product.getAddYield() > 0) {
            mHomeProductLixi2.setVisibility(View.VISIBLE);
            mHomeProductLixi.setText(product.getAnnualYield() - product.getAddYield() + "%");
            mHomeProductLixi2.setText(" + " + String.valueOf(product.getAddYield()) + "%");
        } else {
            mHomeProductLixi2.setVisibility(View.GONE);
            mHomeProductLixi.setText(String.valueOf(product.getAnnualYield()) + "%");
        }
        // TODO 剩余金额 改为 起投金额
        /*mHomeProductLeftMoney.setText("剩余");
        mHomeProductLeftMoney.append(String.valueOf(product.getMinInvestment() / 1000));
        mHomeProductLeftMoney.append("元");*/
        mHomeProductLeftMoney.setText(String.valueOf(product.getMinInvestment() / 1000) + "元");

        mHomeProductDay.setText(product.getFinancialPeriod());
        //        mHomeProductDay.setText(Html.fromHtml("期限<font color= '#e3a802'>" + "" + product.getFinancialPeriod() + "</font>" + "月"));

        mHomeProductRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(INVEST_DETAIL_AUTOWIRED_PRODUCT_ID, product.getProductId()+"");
                bundle.putString(INVEST_DETAIL_AUTOWIRED_PRODUCT_TITLE, product.getTitle());
                if (mOnHomeClickCallBack != null) {
                    mOnHomeClickCallBack.onClickToInvestDetailPager(bundle);
                }
            }
        });
    }
}
