package com.mjn.home.ui.home.holder;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.home.R;
import com.mjn.home.ui.home.adapter.HomeRecyclerAdapter;
import com.mjn.libs.comm.bean.IHomeItemBean;
import com.mjn.libs.utils.AppConfig;
import com.mjn.libs.utils.FitScreenUtil;

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
        updateBottomImage();
    }

    /**
     * 底部一张图片跳转到帮助中心
     */
    private void updateBottomImage() {
        FitScreenUtil.setParams(mIvBottomimg, 1);
        mIvBottomimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "帮助中心");
                bundle.putString("url", AppConfig.HTTP_HOST + AppConfig.HELP_URL);
                if (mOnHomeClickCallBack != null) {
                    mOnHomeClickCallBack.onClickToHtml5Pager(bundle);
                }
            }
        });
    }
}
