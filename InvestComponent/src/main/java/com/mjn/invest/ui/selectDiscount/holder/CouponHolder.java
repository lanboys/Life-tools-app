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

public class CouponHolder extends BaseViewHolder<UserCoupon> {

    private LinearLayout mCouponItemLayout;
    private TextView mCouponItemIntegral;
    private TextView mCouponItemType;
    private ImageView mQuanStatus;
    private TextView mCouponItemLimit;
    private TextView mCouponItemLimitMoney;
    private TextView mCouponItemDate;
    private boolean isCanPressed;

    SelectDiscountAdapter.OnDiscountClickCallBack onDiscountClickCallBack;

    public CouponHolder(View convertView,
            SelectDiscountAdapter.OnDiscountClickCallBack onDiscountClickCallBack) {
        super(convertView);
        this.onDiscountClickCallBack = onDiscountClickCallBack;

        mCouponItemLayout = (LinearLayout) convertView.findViewById(R.id.coupon_item_layout);
        mCouponItemIntegral = (TextView) convertView.findViewById(R.id.coupon_item_Integral);
        mCouponItemType = (TextView) convertView.findViewById(R.id.coupon_item_type);
        mQuanStatus = (ImageView) convertView.findViewById(R.id.quan_status);
        mCouponItemLimit = (TextView) convertView.findViewById(R.id.coupon_item_limit);
        mCouponItemLimitMoney = (TextView) convertView.findViewById(R.id.coupon_item_limit_money);
        mCouponItemDate = (TextView) convertView.findViewById(R.id.coupon_item_date);
    }

    @Override
    public void fillData(final UserCoupon userCoupon, int position) {

        mCouponItemIntegral.setText(String.valueOf(userCoupon.getAddtionAmount()));
        mCouponItemIntegral.append("%");
        mCouponItemLimit.setText("仅限于");
        mCouponItemLimit.append(String.valueOf(userCoupon.getConditionMonth()));
        mCouponItemLimit.append("个月以下产品");
        mCouponItemLimitMoney.setText("单笔投资上限");
        mCouponItemLimitMoney.append(String.valueOf(userCoupon.getConditionMoney() / 1000));
        mCouponItemDate.setText("有效日期:");
        mCouponItemDate.append(Tools.getFullFormatDate(userCoupon.getEffectiveDate().getTime()));
        mCouponItemDate.append("-");
        mCouponItemDate.append(Tools.getFullFormatDate(userCoupon.getExpireDate().getTime()));
        mCouponItemLayout.setOnClickListener(new View.OnClickListener() {
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
                e.printStackTrace();
            }
            if (couponBean != null && couponBean.isExpire == 1) {
                mQuanStatus.setVisibility(View.VISIBLE);
                mQuanStatus.setImageResource(R.drawable.icon_redpacket_out);
                mCouponItemIntegral.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
                mCouponItemType.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
                mCouponItemLimit.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
                mCouponItemLimitMoney.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
                mCouponItemDate.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
            } else {
                mQuanStatus.setVisibility(View.INVISIBLE);
                mCouponItemIntegral.setTextColor(AppUtil.getColor(R.color.ORANG_COLOR));
                mCouponItemType.setTextColor(AppUtil.getColor(R.color.ORANG_COLOR));
                mCouponItemLimit.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_COLOR));
                mCouponItemLimitMoney.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
                mCouponItemDate.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
            }
        } else {
            mQuanStatus.setVisibility(View.VISIBLE);
            mQuanStatus.setImageResource(R.drawable.icon_redpacket_use);
            mCouponItemIntegral.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
            mCouponItemType.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
            mCouponItemLimit.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
            mCouponItemLimitMoney.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
            mCouponItemDate.setTextColor(AppUtil.getColor(R.color.CHAR_GRAY_LESS_COLOR));
        }
    }
}


