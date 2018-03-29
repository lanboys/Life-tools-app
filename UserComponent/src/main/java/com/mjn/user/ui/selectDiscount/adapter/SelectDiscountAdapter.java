package com.mjn.user.ui.selectDiscount.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bing.lan.comm.adapter.BaseRecyclerAdapter;
import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.libs.comm.bean.UserCoupon;
import com.mjn.user.R;
import com.mjn.user.ui.selectDiscount.holder.CouponHolder;
import com.mjn.user.ui.selectDiscount.holder.RedPackageHolder;

import static com.mjn.libs.comm.bean.ISelectDiscountItemBean.SelectDiscountBeanType.DISCOUNT_ITEM_TYPE_COUPON;
import static com.mjn.libs.comm.bean.ISelectDiscountItemBean.SelectDiscountBeanType.DISCOUNT_ITEM_TYPE_RED_PACKAGE;

/**
 * @author 蓝兵
 */
public class SelectDiscountAdapter extends BaseRecyclerAdapter<UserCoupon> {

    OnDiscountClickCallBack mOnDiscountClickCallBack;

    public void setOnDiscountClickCallBack(OnDiscountClickCallBack discountClickCallBack) {
        mOnDiscountClickCallBack = discountClickCallBack;
    }

    @Override
    public int getItemLayoutResId(int viewType) {
        switch (viewType) {
            case DISCOUNT_ITEM_TYPE_RED_PACKAGE:
                return R.layout.red_package_item;
            case DISCOUNT_ITEM_TYPE_COUPON:
                return R.layout.coupon_item;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        return data.get(position).getSelectDiscountBeanType();
    }

    @Override
    public BaseViewHolder<UserCoupon> createViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case DISCOUNT_ITEM_TYPE_RED_PACKAGE:
                return new RedPackageHolder(itemView, mOnDiscountClickCallBack);
            case DISCOUNT_ITEM_TYPE_COUPON:
                return new CouponHolder(itemView, mOnDiscountClickCallBack);
        }
        return null;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        //RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //if (layoutManager instanceof GridLayoutManager) {
        //    final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
        //    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        //        @Override
        //        public int getSpanSize(int position) {
        //            int itemViewType = getItemViewType(position);
        //            if (itemViewType == ITEM_TYPE_NORMAL) {
        //                return 1;
        //            }
        //            return 5;
        //        }
        //    });
        //}
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
    }

    public interface OnDiscountClickCallBack {

        void onClickToPayOrderPager(Bundle bundle);
    }
}
