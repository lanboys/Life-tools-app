package com.mjn.invest.ui.investList.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bing.lan.comm.adapter.BaseRecyclerAdapter;
import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.invest.R;
import com.mjn.invest.ui.investList.holder.InvestHolder;
import com.mjn.invest.ui.investList.holder.InvestTopHolder;
import com.mjn.libs.comm.bean.IProduct;

import static com.mjn.libs.comm.bean.IInvestItemBean.InvestType.INVEST_TYPE_ITEM;
import static com.mjn.libs.comm.bean.IInvestItemBean.InvestType.INVEST_TYPE_TOP_ITEM;

/**
 * @author 蓝兵
 */
public class InvestRecyclerAdapter extends BaseRecyclerAdapter<IProduct> {

    OnInvestClickCallBack mOnHomeClickCallBack;

    public void setOnInvestClickCallBack(OnInvestClickCallBack onHomeClickCallBack) {
        mOnHomeClickCallBack = onHomeClickCallBack;
    }

    @Override
    public int getItemLayoutResId(int viewType) {
        switch (viewType) {
            case INVEST_TYPE_TOP_ITEM:
                return R.layout.invest_topitem_product;
            case INVEST_TYPE_ITEM:
                return R.layout.invest_list_item;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        return data.get(position).getInvestBeanType();
    }

    @Override
    public BaseViewHolder<IProduct> createViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case INVEST_TYPE_TOP_ITEM:
                return new InvestTopHolder(itemView, mOnHomeClickCallBack);
            case INVEST_TYPE_ITEM:
                return new InvestHolder(itemView, mOnHomeClickCallBack);
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

    public interface OnInvestClickCallBack {

        void onClickToInvestPager(Bundle bundle);
    }
}
