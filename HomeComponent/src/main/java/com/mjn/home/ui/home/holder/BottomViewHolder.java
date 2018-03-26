package com.mjn.home.ui.home.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.home.R;
import com.mjn.home.ui.home.adapter.HomeRecyclerAdapter;
import com.mjn.libs.comm.bean.IHomeItemBean;

/**
 * Created by 蓝兵 on 2018/3/26.
 */

public class BottomViewHolder extends BaseViewHolder<IHomeItemBean> {

    HomeRecyclerAdapter.OnHomeClickCallBack mOnHomeClickCallBack;
    private LinearLayout mHomeProductRoot;

    public BottomViewHolder(View itemView, HomeRecyclerAdapter.OnHomeClickCallBack onHomeClickCallBack) {
        super(itemView);
        mOnHomeClickCallBack = onHomeClickCallBack;


        mHomeProductRoot = (LinearLayout) itemView.findViewById(R.id.home_product_root);
    }

    @Override
    public void fillData(IHomeItemBean data, int position) {

    }
}
