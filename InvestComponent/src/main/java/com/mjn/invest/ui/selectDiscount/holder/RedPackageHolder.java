package com.mjn.invest.ui.selectDiscount.holder;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bing.lan.comm.adapter.BaseViewHolder;
import com.bing.lan.comm.app.AppUtil;
import com.mjn.invest.R;
import com.mjn.invest.ui.selectDiscount.adpter.SelectDiscountAdapter;
import com.mjn.libs.comm.bean.NewUserCouponBean;
import com.mjn.libs.comm.bean.UserCoupon;
import com.mjn.libs.utils.Tools;

/**
 * Created by 蓝兵 on 2018/3/27.
 */

public class RedPackageHolder extends BaseViewHolder<UserCoupon> {

    private LinearLayout mRootView;
    private ImageView mQuanStatus;
    private LinearLayout mLlMoney;
    private TextView mQuanMoneyIcon;
    private TextView mQuanMoney;
    private TextView mQuanTitle;
    private TextView mQuanDesc;
    private TextView mQuanDate;

    private boolean isCanPressed;
    SelectDiscountAdapter.OnDiscountClickCallBack onDiscountClickCallBack;

    public RedPackageHolder(View convertView,
            SelectDiscountAdapter.OnDiscountClickCallBack onDiscountClickCallBack) {
        super(convertView);
        this.onDiscountClickCallBack = onDiscountClickCallBack;

        mRootView = (LinearLayout) convertView.findViewById(R.id.rootView);
        mQuanStatus = (ImageView) convertView.findViewById(R.id.quan_status);
        mLlMoney = (LinearLayout) convertView.findViewById(R.id.ll_money);
        mQuanMoneyIcon = (TextView) convertView.findViewById(R.id.quan_money_icon);
        mQuanMoney = (TextView) convertView.findViewById(R.id.quan_money);
        mQuanTitle = (TextView) convertView.findViewById(R.id.quan_title);
        mQuanDesc = (TextView) convertView.findViewById(R.id.quan_desc);
        mQuanDate = (TextView) convertView.findViewById(R.id.quan_date);
    }

    @Override
    public void fillData(final UserCoupon userCoupon, int position) {

        mQuanMoney.setText(String.valueOf(userCoupon.getCouponValue() / 1000));
        mQuanTitle.setText(userCoupon.getCouponName());
        mQuanDesc.setText(userCoupon.getDescription());
        mQuanDate.setText("有效时间: ");
        mQuanDate.append(Tools.getFullFormatDate2(userCoupon.getEffectiveDate().getTime()));
        mQuanDate.append("-");
        mQuanDate.append(Tools.getFullFormatDate2(userCoupon.getExpireDate().getTime()));
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCanPressed) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isUse", true);
                    bundle.putSerializable("coupon", userCoupon);
                    if (onDiscountClickCallBack != null) {
                        onDiscountClickCallBack.onClickToPayOrderPager(bundle);
                    }
                }
            }
        });
        if (userCoupon.getStatus() == 0) {//未使用
            NewUserCouponBean couponBean = null;
            try {
                couponBean = (NewUserCouponBean) userCoupon;
            } catch (Exception e) {
            }
            if (couponBean != null && couponBean.isExpire == 1) {//已过期
                mQuanStatus.setVisibility(View.VISIBLE);
                mQuanStatus.setImageResource(R.drawable.icon_redpacket_out);
                mQuanMoney.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
                mQuanMoneyIcon.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
                mQuanTitle.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
                mQuanDesc.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
                mQuanDate.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
            } else {
                mQuanStatus.setVisibility(View.INVISIBLE);
                mQuanMoney.setTextColor(AppUtil.getColor(R.color.ORANG_COLOR));
                mQuanMoneyIcon.setTextColor(AppUtil.getColor(R.color.ORANG_COLOR));
                mQuanTitle.setTextColor(AppUtil.getColor(R.color.CHAR_BLACK_COLOR));
                mQuanDesc.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_COLOR));
                mQuanDate.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
            }
        } else {
            mQuanStatus.setVisibility(View.VISIBLE);
            mQuanStatus.setImageResource(R.drawable.icon_redpacket_use);
            mQuanMoney.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
            mQuanMoneyIcon.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
            mQuanTitle.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
            mQuanDesc.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
            mQuanDate.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
        }
    }
}


