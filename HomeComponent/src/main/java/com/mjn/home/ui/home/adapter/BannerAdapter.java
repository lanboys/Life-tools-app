package com.mjn.home.ui.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.mjn.home.R;
import com.mjn.libs.comm.bean.Adv;
import com.mjn.libs.utils.ImageLoaderUtil;
import com.mjn.libs.view.banner.BannerBaseAdapter;

/**
 * Created by 蓝兵 on 2018/3/24.
 */

public class BannerAdapter extends BannerBaseAdapter<Adv> {

    public BannerAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.item_banner;
    }

    @Override
    protected void convert(View convertView, final Adv adv) {
        ImageView img = (ImageView) convertView.findViewById(R.id.pageImage);
        ImageLoaderUtil.showImage(img, adv.getPicUrl(), R.drawable.bg_banner_default);
    }
}
