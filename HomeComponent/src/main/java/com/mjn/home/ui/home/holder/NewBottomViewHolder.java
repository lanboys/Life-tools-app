package com.mjn.home.ui.home.holder;

import android.view.View;
import android.widget.ImageView;

import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.home.R;
import com.mjn.home.ui.home.adapter.HomeRecyclerAdapter;
import com.mjn.libs.comm.bean.IHomeItemBean;

/**
 * Created by 蓝兵 on 2018/3/26.
 */

public class NewBottomViewHolder extends BaseViewHolder<IHomeItemBean> {

    HomeRecyclerAdapter.OnHomeClickCallBack mOnHomeClickCallBack;
    private ImageView mIvBottomimg;

    public NewBottomViewHolder(View itemView, HomeRecyclerAdapter.OnHomeClickCallBack onHomeClickCallBack) {
        super(itemView);
        mOnHomeClickCallBack = onHomeClickCallBack;
        //itemView.findViewById(R.layout.home_item_bottom_image);
        mIvBottomimg = (ImageView) itemView.findViewById(R.id.iv_bottomimg);
    }

    @Override
    public void fillData(IHomeItemBean data, int position) {

    }

    private void initView() {
    }
}
