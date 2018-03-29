package com.mjn.user.ui.bankList.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bing.lan.comm.adapter.BaseRecyclerAdapter;
import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.libs.comm.bean.IBankCard;
import com.mjn.libs.utils.AppConfig;
import com.mjn.user.R;

/**
 * @author 蓝兵
 */
public class BankListAdapter extends BaseRecyclerAdapter<IBankCard> {

    @Override
    public int getItemLayoutResId(int viewType) {
        return R.layout.bankitem;
    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }

    @Override
    public BaseViewHolder<IBankCard> createViewHolder(View itemView, int viewType) {

        return new BankHolder(itemView);
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

    class BankHolder extends BaseViewHolder<IBankCard> {

        private RelativeLayout mBankLayout;
        private ImageView mGetCashBankIcon;
        private TextView mGetCashBankName;
        private TextView mBankBranch;
        private ImageView mBankSelect;

        public BankHolder(View itemView) {
            super(itemView);
            mBankLayout = (RelativeLayout) itemView.findViewById(R.id.bank_layout);
            mGetCashBankIcon = (ImageView) itemView.findViewById(R.id.get_cash_bank_icon);
            mGetCashBankName = (TextView) itemView.findViewById(R.id.get_cash_bank_name);
            mBankBranch = (TextView) itemView.findViewById(R.id.bank_branch);
            mBankSelect = (ImageView) itemView.findViewById(R.id.bank_select);
        }

        @Override
        public void fillData(IBankCard data, int position) {

            mGetCashBankIcon.setImageResource(AppConfig.iconMap.get(data.getBankCode()));
            mGetCashBankName.setText(data.getBankName());
            mBankBranch.setText("尾号" + data.getCardNo());

        }
    }
}
