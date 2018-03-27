package com.mjn.invest.ui.invest.holder;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.invest.R;
import com.mjn.invest.ui.invest.adapter.InvestRecyclerAdapter;
import com.mjn.libs.comm.bean.IProduct;

/**
 * Created by 蓝兵 on 2018/3/27.
 */

public class InvestHolder extends BaseViewHolder<IProduct> {

    InvestRecyclerAdapter.OnInvestClickCallBack mOnHomeClickCallBack;
    private TextView mInvestListItemName;
    private LinearLayout mInvestItemLayout;
    private TextView mInvestItemPrecent;
    private TextView mInvestListItemAdd;
    private TextView mTextView15;
    private TextView mInvestItemMonth;
    private TextView mInvestItemMonthUnit;
    private TextView mInvestItemMoney;
    private TextView mInvestItemBtn;
    private ImageView mInvestItemNewer;
    private ImageView mInvestItemEnd;

    public InvestHolder(View convertView, InvestRecyclerAdapter.OnInvestClickCallBack onHomeClickCallBack) {
        super(convertView);
        this.mOnHomeClickCallBack = onHomeClickCallBack;

        mInvestListItemName = (TextView) convertView.findViewById(R.id.invest_list_item_name);
        mInvestItemLayout = (LinearLayout) convertView.findViewById(R.id.invest_item_layout);
        mInvestItemPrecent = (TextView) convertView.findViewById(R.id.invest_item_precent);
        mInvestListItemAdd = (TextView) convertView.findViewById(R.id.invest_list_item_add);
        mTextView15 = (TextView) convertView.findViewById(R.id.textView15);
        mInvestItemMonth = (TextView) convertView.findViewById(R.id.invest_item_month);
        mInvestItemMonthUnit = (TextView) convertView.findViewById(R.id.invest_item_month_unit);
        mInvestItemMoney = (TextView) convertView.findViewById(R.id.invest_item_money);
        mInvestItemBtn = (TextView) convertView.findViewById(R.id.invest_item_btn);
        mInvestItemNewer = (ImageView) convertView.findViewById(R.id.invest_item_newer);
        mInvestItemEnd = (ImageView) convertView.findViewById(R.id.invest_item_end);
    }

    @Override
    public void fillData(final IProduct product, int position) {

        if (product == null) {
            return;
        }

        if (!TextUtils.isEmpty(product.getTitle())) {
            mInvestListItemName.setText(product.getTitle());
        }
        if (product.getAddYield() > 0) {
            mInvestListItemAdd.setVisibility(View.VISIBLE);
            mInvestListItemAdd.setText(" +");
            mInvestListItemAdd.append(String.valueOf(product.getAddYield()));
            mInvestListItemAdd.append("%");
        } else {
            mInvestListItemAdd.setVisibility(View.INVISIBLE);
        }
        mInvestItemPrecent.setText(String.valueOf(product.getAnnualYield() - product.getAddYield()) + "%");

        if (product.getFinancialPeriod().contains("个月")) {
            mInvestItemMonth.setText(product.getFinancialPeriod().replaceAll("个月", ""));
            mInvestItemMonthUnit.setText("个月");
        } else if (product.getFinancialPeriod().contains("年")) {
            mInvestItemMonth.setText(product.getFinancialPeriod().replaceAll("年", ""));
            mInvestItemMonthUnit.setText("年");
        } else if (product.getFinancialPeriod().contains("天")) {
            mInvestItemMonth.setText(product.getFinancialPeriod().replaceAll("天", ""));
            mInvestItemMonthUnit.setText("天");
        }
        mInvestItemMoney.setText(String.valueOf(product.getRemainAmount() / 1000));
        mInvestItemLayout.setOnClickListener(new View.OnClickListener() {
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

        long joinDate = ((long) product.getJoinDateUnix() * (long) 1000);
        long endDate = (long) product.getEndDateUnix() * (long) 1000;
        if (!product.getStatus().equals("PUBLISHING")) {
            //            viewHolder.endBuy.setVisibility(View.VISIBLE);
            mInvestItemBtn.setBackgroundResource(R.drawable.icon_invest_listbtn_u);
            mInvestItemBtn.setText("已满标");
        } else if (joinDate > product.getServicetime()) {
            //            viewHolder.endBuy.setVisibility(View.VISIBLE);
            mInvestItemBtn.setBackgroundResource(R.drawable.icon_invest_listbtn_u);
            mInvestItemBtn.setText("未开始");
        } else if (endDate < product.getServicetime() && product.getEndDateUnix() != 0) {
            //            viewHolder.endBuy.setVisibility(View.VISIBLE);
            mInvestItemBtn.setBackgroundResource(R.drawable.icon_invest_listbtn_u);
            mInvestItemBtn.setText("已结束");
        } else {
            //            viewHolder.endBuy.setVisibility(View.INVISIBLE);
            mInvestItemBtn.setBackgroundResource(R.drawable.icon_invest_listbtn);
            mInvestItemBtn.setText("抢购");
        }
    }
}


