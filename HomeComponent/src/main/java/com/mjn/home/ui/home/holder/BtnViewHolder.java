package com.mjn.home.ui.home.holder;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bing.lan.comm.adapter.BaseViewHolder;
import com.mjn.home.R;
import com.mjn.home.ui.home.adapter.HomeRecyclerAdapter;
import com.mjn.home.ui.home.bean.HomeBtnBean;
import com.mjn.libs.comm.bean.HomeMessageBean;
import com.mjn.libs.comm.bean.IHomeItemBean;
import com.mjn.libs.utils.AppSpDataUtil;
import com.mjn.libs.utils.SPUtil;
import com.mjn.libs.utils.Tools;
import com.mjn.libs.view.UPMarqueeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝兵 on 2018/3/26.
 */

public class BtnViewHolder extends BaseViewHolder<IHomeItemBean> implements View.OnClickListener {

    HomeRecyclerAdapter.OnHomeClickCallBack mOnHomeClickCallBack;
    private UPMarqueeView home_tongzhi;
    private TextView mHomeBanner;
    private TextView mHomeHuodong;
    private TextView mHomeQiandao;
    private TextView mHomeYaoqing;
    private LinearLayout mLlHongbaoorqiandao;
    private ImageView mIvHongbaoorqiandao;

    // 平台简介url
    private String platformDescUrl;

    public BtnViewHolder(View convertView, HomeRecyclerAdapter.OnHomeClickCallBack onHomeClickCallBack) {
        super(convertView);
        mOnHomeClickCallBack = onHomeClickCallBack;

        home_tongzhi = (UPMarqueeView) convertView.findViewById(R.id.home_tongzhi);
        mHomeBanner = (TextView) convertView.findViewById(R.id.home_banner);
        mHomeHuodong = (TextView) convertView.findViewById(R.id.home_huodong);
        //mHomeQiandao = (TextView) convertView.findViewById(R.id.home_qiandao);
        mHomeYaoqing = (TextView) convertView.findViewById(R.id.home_yaoqing);

        mLlHongbaoorqiandao = (LinearLayout) convertView.findViewById(R.id.ll_hongbaoorqiandao);
        mIvHongbaoorqiandao = (ImageView) convertView.findViewById(R.id.iv_hongbaoorqiandao);

        //mHomeTongzhi.setOnClickListener(this);
        mHomeBanner.setOnClickListener(this);
        mHomeHuodong.setOnClickListener(this);
        //mHomeQiandao.setOnClickListener(this);
        mHomeYaoqing.setOnClickListener(this);
        mLlHongbaoorqiandao.setOnClickListener(this);
    }

    @Override
    public void fillData(IHomeItemBean data, int position) {
        HomeBtnBean homeBtnBean = (HomeBtnBean) data;
        if (homeBtnBean != null) {
            platformDescUrl = homeBtnBean.getPlatformDescUrl();
        }

        // 未登录显示红包
        if (AppSpDataUtil.getInstance().isLogined()) {
            mLlHongbaoorqiandao.setVisibility(View.GONE);
        } else {
            mIvHongbaoorqiandao.setBackgroundResource(R.drawable.xinshouhongbao);
            mLlHongbaoorqiandao.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.home_tongzhi == id) {

        } else if (R.id.home_huodong == id) {
            if (mOnHomeClickCallBack != null) {
                mOnHomeClickCallBack.onClickToActivityCenterPager();
            }
        } else if (R.id.home_banner == id) {
            clickHomeBanner();
        } else if (R.id.home_yaoqing == id) {
            clickYaoQing();
        } else if (R.id.ll_hongbaoorqiandao == id) {
            if (mOnHomeClickCallBack != null) {
                mOnHomeClickCallBack.onClickToLoginPager();
            }
        }
    }

    private List<View> messageViews;

    public void setTongZhiData(final Context context, final List<HomeMessageBean> messageList) {
        if (home_tongzhi != null && messageViews == null) {
            messageViews = new ArrayList<>();
            for (final HomeMessageBean bean : messageList) {
                View itemView = LayoutInflater.from(context).inflate(R.layout.message_item, null);
                MessageViewHolder messageViewHolder = new MessageViewHolder(itemView);
                messageViewHolder.message_title.setText(bean.msg);
                messageViews.add(itemView);
            }
            home_tongzhi.setViews(messageViews);
            home_tongzhi.setOnItemClickListener(new UPMarqueeView.OnItemClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    HomeMessageBean bean = messageList.get(position);
                    if (bean.type == 1 && !TextUtils.isEmpty(bean.contentUrl)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("url", bean.contentUrl);
                        mOnHomeClickCallBack.onClickToHtml5Pager(bundle);
                    }
                }
            });
        }
    }

    public class MessageViewHolder {

        public TextView message_title;

        public MessageViewHolder(View view) {
            message_title = view.findViewById(R.id.message_title);
        }
    }

    private void clickYaoQing() {
        if (AppSpDataUtil.getInstance().isLogined()) {
            if (AppSpDataUtil.getInstance().getUserBean() != null) {
                Bundle bundle = new Bundle();
                bundle.putString("url", AppSpDataUtil.getInstance().getUserBean().getInvitedFriendsUrl() + "?userId=" + AppSpDataUtil.getInstance().getUserBean().getUserId());
                Tools.Log(AppSpDataUtil.getInstance().getUserBean().getInvitedFriendsUrl() + "?userId=" + AppSpDataUtil.getInstance().getUserBean().getUserId());
                if (mOnHomeClickCallBack != null) {
                    mOnHomeClickCallBack.onClickToHtml5Pager(bundle);
                }
            }
        } else {
            if (mOnHomeClickCallBack != null) {
                mOnHomeClickCallBack.onClickToLoginPager();
            }
        }
    }

    private void clickHomeBanner() {

        if (!TextUtils.isEmpty(platformDescUrl)) {
            Bundle bundle = new Bundle();
            if (AppSpDataUtil.getInstance().isLogined()) {
                if (platformDescUrl.contains("?")) {
                    bundle.putString("url", platformDescUrl + "&token=" + SPUtil.getInstance().getString("token") +
                            "&userId=" + AppSpDataUtil.getInstance().getUserBean().getUserId() + "&isLogin=1");
                } else {
                    bundle.putString("url", platformDescUrl + "?token=" + SPUtil.getInstance().getString("token") +
                            "&userId=" + AppSpDataUtil.getInstance().getUserBean().getUserId() + "&isLogin=1");
                }
            } else {
                if (platformDescUrl.contains("?")) {
                    bundle.putString("url", platformDescUrl + "&isLogin=0");
                } else {
                    bundle.putString("url", platformDescUrl + "?isLogin=0");
                }
            }
            bundle.putString("title", "平台简介");
            //Tools.pushScreen(HybridOfWebview.class, bundle);
            if (mOnHomeClickCallBack != null) {
                mOnHomeClickCallBack.onClickToHtml5Pager(bundle);
            }
        }
    }
}
