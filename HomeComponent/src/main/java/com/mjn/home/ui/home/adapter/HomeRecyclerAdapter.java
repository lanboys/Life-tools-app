package com.mjn.home.ui.home.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bing.lan.comm.adapter.BaseRecyclerAdapter;
import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.home.R;
import com.mjn.home.ui.home.holder.BannerHolder;
import com.mjn.home.ui.home.holder.BottomViewHolder;
import com.mjn.home.ui.home.holder.BtnViewHolder;
import com.mjn.home.ui.home.holder.NewBottomViewHolder;
import com.mjn.home.ui.home.holder.ProductPreViewHolder;
import com.mjn.home.ui.home.holder.ProductViewHolder;
import com.mjn.libs.comm.bean.IHomeItemBean;

import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BANNER;
import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BOTTOM;
import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BOTTOM_GUIDE;
import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_BTN;
import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_HONG_BAO_QIAN_DAO;
import static com.mjn.libs.comm.bean.IHomeItemBean.HomeBeanType.HOME_ITEM_TYPE_PRE_PRODUCT;
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
                return R.layout.home_item_btn;
            case HOME_ITEM_TYPE_BOTTOM:
                return R.layout.home_item_bottom;
            case HOME_ITEM_TYPE_PRODUCT:
                return R.layout.home_item_product;
            case HOME_ITEM_TYPE_PRE_PRODUCT:
                return R.layout.home_item_newproduct;
            case HOME_ITEM_TYPE_BOTTOM_GUIDE:
                return R.layout.home_item_bottom_image;
            case HOME_ITEM_TYPE_HONG_BAO_QIAN_DAO:
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
                return new BtnViewHolder(itemView, mOnHomeClickCallBack);
            case HOME_ITEM_TYPE_BOTTOM:
                return new BottomViewHolder(itemView, mOnHomeClickCallBack);
            case HOME_ITEM_TYPE_PRODUCT:
                return new ProductViewHolder(itemView, mOnHomeClickCallBack);
            case HOME_ITEM_TYPE_PRE_PRODUCT:
                return new ProductPreViewHolder(itemView, mOnHomeClickCallBack);
            case HOME_ITEM_TYPE_BOTTOM_GUIDE:
                return new NewBottomViewHolder(itemView, mOnHomeClickCallBack);
            case HOME_ITEM_TYPE_HONG_BAO_QIAN_DAO:
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

        void onClickToHtml5Pager(Bundle bundle);

        void onClickToLoginPager();

        void onClickToActivityCenterPager();

        void onClickToInvestDetailPager(Bundle bundle);
    }
}
