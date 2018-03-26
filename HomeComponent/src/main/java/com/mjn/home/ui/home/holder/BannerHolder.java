package com.mjn.home.ui.home.holder;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.home.R;
import com.mjn.home.ui.home.adapter.BannerAdapter;
import com.mjn.home.ui.home.adapter.HomeRecyclerAdapter;
import com.mjn.home.ui.home.bean.BannerBean;
import com.mjn.libs.comm.bean.Adv;
import com.mjn.libs.comm.bean.IHomeItemBean;
import com.mjn.libs.utils.AppSpDataUtil;
import com.mjn.libs.utils.SPUtil;
import com.mjn.libs.view.banner.BannerBaseAdapter;
import com.mjn.libs.view.banner.BannerView;

import java.util.List;

/**
 * Created by 蓝兵 on 2018/3/24.
 */

public class BannerHolder extends BaseViewHolder<IHomeItemBean>
        implements BannerBaseAdapter.OnPageTouchListener<Adv> {

    private final BannerView bannerView;
    private List<Adv> mBannerList;
    HomeRecyclerAdapter.OnHomeClickCallBack mOnHomeClickCallBack;

    public BannerHolder(View itemView, HomeRecyclerAdapter.OnHomeClickCallBack onHomeClickCallBack) {
        super(itemView);
        mOnHomeClickCallBack = onHomeClickCallBack;
        bannerView = itemView.findViewById(R.id.bannerView);
    }

    @Override
    public void fillData(IHomeItemBean homeItemBean, int position) {
        BannerBean data = (BannerBean) homeItemBean;
        mBannerList = data.getBannerList();
        BannerAdapter mAdapter = new BannerAdapter(com.bing.lan.comm.app.AppUtil.getAppContext());
        mAdapter.setOnPageTouchListener(this);
        bannerView.setAdapter(mAdapter);
        mAdapter.setData(mBannerList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageClick(int position, Adv adv) {
        // 页面点击
        if (mBannerList.isEmpty())
            return;
        String destUrl = mBannerList.get(position).getDestUrl();

        if (mBannerList.get(position).getNeedLogin() == 0) {
            if (!mBannerList.isEmpty() && position < mBannerList.size() &&
                    !TextUtils.isEmpty(destUrl)) {
                Bundle bundle = new Bundle();

                // 判断是否登录，登录传token,userid,isoginq=1.未登录只传递isLogin
                if (AppSpDataUtil.getInstance().isLogined()) {
                    Integer userId = AppSpDataUtil.getInstance().getUserBean().getUserId();
                    // 拼接参数用？还是&的区分
                    if (destUrl.contains("?")) {
                        bundle.putString("url", destUrl + "&token=" + SPUtil.getInstance().getString("token") + "&userId=" + userId + "&isLogin=1");
                    } else {
                        bundle.putString("url", destUrl + "?token=" + SPUtil.getInstance().getString("token") + "&userId=" + userId + "&isLogin=1");
                    }
                } else {
                    if (destUrl.contains("?")) {
                        bundle.putString("url", destUrl + "&isLogin=0");
                    } else {
                        bundle.putString("url", destUrl + "?isLogin=0");
                    }
                }

                bundle.putString("title", mBannerList.get(position).getAdvTxt());
                //Tools.pushScreen(HybridOfWebview.class, bundle);
                if (mOnHomeClickCallBack != null) {
                    mOnHomeClickCallBack.onClickToHtml5Pager( bundle);
                }
            }
        } else {
            // 未登录状态下不可以进入H5
            if (!AppSpDataUtil.getInstance().isLogined()) {
                //Tools.pushScreen(Login.class, null);
                if (mOnHomeClickCallBack != null) {
                    mOnHomeClickCallBack.onClickToLoginPager( );
                }
            } else {
                if (position < mBannerList.size() && !TextUtils.isEmpty(destUrl)) {
                    Bundle bundle = new Bundle();
                    Integer userId = AppSpDataUtil.getInstance().getUserBean().getUserId();
                    // 拼接参数用？还是&的区分
                    if (destUrl.contains("?")) {
                        bundle.putString("url", destUrl + "&token="
                                + SPUtil.getInstance().getString("token") + "&userId="
                                + userId + "&isLogin=1");
                    } else {
                        bundle.putString("url", destUrl + "?token="
                                + SPUtil.getInstance().getString("token") + "&userId="
                                + userId + "&isLogin=1");
                    }

                    bundle.putString("title", mBannerList.get(position).getAdvTxt());
                    //Tools.pushScreen(HybridOfWebview.class, bundle);
                    if (mOnHomeClickCallBack != null) {
                        mOnHomeClickCallBack.onClickToHtml5Pager( bundle);
                    }
                }
            }
        }
    }

    @Override
    public void onPageDown() {
        if (bannerView != null) {
            // 按下，可以停止轮播
            bannerView.stopAutoScroll();
        }
    }

    @Override
    public void onPageUp() {
        if (bannerView != null) {
            // 抬起开始轮播
            bannerView.startAutoScroll();
            Log.e("bannerView", "start");
        }
    }
}


