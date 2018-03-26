package com.mjn.home.ui.home.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bing.lan.comm.adapter.BaseRecyclerAdapter;
import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.home.R;
import com.mjn.home.ui.home.holder.BannerHolder;
import com.mjn.libs.comm.bean.IHomeItemBean;

import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BANNER;
import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BOTTOM;
import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BOTTOMGUIDE;
import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BTN;
import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_HONGBAOQIANDAO;
import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_PREPRODUCT;
import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_PRODUCT;

/**
 * @author 蓝兵
 */
public class HomeRecyclerAdapter extends BaseRecyclerAdapter<IHomeItemBean> {

    OnHomeClickCallBack mOnHomeClickCallBack;

    public void setOnHomeClickCallBack(OnHomeClickCallBack onHomeClickCallBack) {
        mOnHomeClickCallBack = onHomeClickCallBack;
    }

    @Override
    public int getItemLayoutResId(int viewType) {
        switch (viewType) {
            case HOME_ITEM_TYPE_BANNER:
                return R.layout.home_item_newbanner;
            case HOME_ITEM_TYPE_BTN:
                return 0;
            case HOME_ITEM_TYPE_PRODUCT:
                return 0;
            case HOME_ITEM_TYPE_BOTTOM:
                return 0;
            case HOME_ITEM_TYPE_PREPRODUCT:
                return 0;
            case HOME_ITEM_TYPE_BOTTOMGUIDE:
                return 0;
            case HOME_ITEM_TYPE_HONGBAOQIANDAO:
                return 0;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        int homeBeanType = data.get(position).getHomeBeanType();
        return homeBeanType;
    }

    @Override
    public BaseViewHolder createViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case HOME_ITEM_TYPE_BANNER:
                return new BannerHolder(itemView, mOnHomeClickCallBack);
            case HOME_ITEM_TYPE_BTN:
                return null;
            case HOME_ITEM_TYPE_PRODUCT:
                return null;
            case HOME_ITEM_TYPE_BOTTOM:
                return null;
            case HOME_ITEM_TYPE_PREPRODUCT:
                return null;
            case HOME_ITEM_TYPE_BOTTOMGUIDE:
                return null;
            case HOME_ITEM_TYPE_HONGBAOQIANDAO:
                return null;
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

    public interface OnHomeClickCallBack {

        void onBannerClick(boolean isToHtml5, Bundle bundle);
    }
}
