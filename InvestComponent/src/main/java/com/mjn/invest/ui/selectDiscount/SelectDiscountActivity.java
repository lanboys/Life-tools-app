package com.mjn.invest.ui.selectDiscount;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.view.MyToolbar;
import com.ganxin.library.LoadDataLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.mjn.invest.R;
import com.mjn.invest.ui.selectDiscount.adpter.SelectDiscountAdapter;
import com.mjn.libs.base.IMainLibActivityContract;
import com.mjn.libs.base.MainLibActivity;
import com.mjn.libs.comm.bean.ISelectDiscountItemBean;
import com.mjn.libs.comm.bean.PayInfo;
import com.mjn.libs.comm.bean.UserCoupon;
import com.mjn.libs.view.pullRefresh.PullToRefreshLoadDataLayoutRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import static com.mjn.libs.cons.IntentParamsKeyCons.INTENT_TO_PAY_ORDER_PROJECT_ID;
import static com.mjn.libs.cons.IntentParamsKeyCons.INTENT_TO_PAY_ORDER_PROJECT_MONEY;

/**
 * @author 蓝兵
 */
public class SelectDiscountActivity extends MainLibActivity<IMainLibActivityContract.IMainLibActivityPresenter> implements SelectDiscountAdapter.OnDiscountClickCallBack, View.OnClickListener {

    private MyToolbar mToolbar;
    private TextView mSelectDiscountBtnCoupon;
    private TextView mSelectDiscountBtnRed;
    private com.mjn.libs.view.pullRefresh.PullToRefreshLoadDataLayoutRecyclerView mPullRefreshRecycler;
    private TextView mSelectDiscountBtnNotUse;
    private Long mMoney;
    private PayInfo mPayInfo;
    private ArrayList<UserCoupon> mList = new ArrayList<>();
    private ArrayList<UserCoupon> mRedPackageList = new ArrayList<>();
    private ArrayList<UserCoupon> mCouponList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private SelectDiscountAdapter mAdapter;

    @Override
    protected IMainLibActivityContract.IMainLibActivityPresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_select_discount;
    }

    @Override
    protected void initViewAndData(Intent intent) {
        mToolbar = (MyToolbar) findViewById(R.id.toolbar);
        mSelectDiscountBtnCoupon = (TextView) findViewById(R.id.select_discount_btn_coupon);
        mSelectDiscountBtnRed = (TextView) findViewById(R.id.select_discount_btn_red);
        mPullRefreshRecycler = (PullToRefreshLoadDataLayoutRecyclerView) findViewById(R.id.pull_refresh_recycler);
        mSelectDiscountBtnNotUse = (TextView) findViewById(R.id.select_discount_btn_not_use);

        mSelectDiscountBtnCoupon.setOnClickListener(this);
        mSelectDiscountBtnRed.setOnClickListener(this);

        mPullToRefreshBase = mPullRefreshRecycler;
        mLoadDataLayout = mPullRefreshRecycler.getRefreshableView();
        mLoadDataLayout.setStatus(LoadDataLayout.SUCCESS);

        setToolBar(mToolbar, "优惠选择", true, 0);
        if (intent != null) {
            String money = intent.getStringExtra(INTENT_TO_PAY_ORDER_PROJECT_MONEY);
            if (!TextUtils.isEmpty(money)) {
                mMoney = Long.valueOf(money);
            }
            mPayInfo = (PayInfo) intent.getSerializableExtra(INTENT_TO_PAY_ORDER_PROJECT_ID);

            if (mPayInfo != null) {
                for (UserCoupon userCoupon : mPayInfo.getLuckeyMoneys()) {
                    userCoupon.setSelectDiscountBeanType(ISelectDiscountItemBean.SelectDiscountBeanType.DISCOUNT_ITEM_TYPE_RED_PACKAGE);
                    mRedPackageList.add(userCoupon);
                }
                for (UserCoupon userCoupon : mPayInfo.getCoupons()) {
                    userCoupon.setSelectDiscountBeanType(ISelectDiscountItemBean.SelectDiscountBeanType.DISCOUNT_ITEM_TYPE_COUPON);
                    mCouponList.add(userCoupon);
                }
            }
        }

        mPullRefreshRecycler.setMode(PullToRefreshBase.Mode.DISABLED);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = mPullRefreshRecycler.getRealRefreshableView();

        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.TRANSPARENT)
                        .sizeResId(R.dimen.dimen_recycleView_divider_10dp)
                        .marginResId(R.dimen.dimen_recycleView_divider_left_margin_10dp,
                                R.dimen.dimen_recycleView_divider_right_margin_10dp)
                        .build());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new SelectDiscountAdapter();
        mAdapter.setOnDiscountClickCallBack(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDataAndRefresh(mList);

        mSelectDiscountBtnRed.performLongClick();
    }

    @Override
    protected void readyStartPresenter() {

    }

    @Override
    public void onClickToPayOrderPager(Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra("discount", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.select_discount_btn_coupon == id) {
            mSelectDiscountBtnCoupon.setBackgroundColor(AppUtil.getColor(R.color.BLUE_THEME));
            mSelectDiscountBtnCoupon.setTextColor(AppUtil.getColor(R.color.white));
            mSelectDiscountBtnRed.setBackgroundColor(AppUtil.getColor(R.color.white));
            mSelectDiscountBtnRed.setTextColor(AppUtil.getColor(R.color.CHAR_BLACK_COLOR));

            mAdapter.setDataAndRefresh(mCouponList);
        } else if (R.id.select_discount_btn_red == id) {
            mSelectDiscountBtnCoupon.setBackgroundColor(AppUtil.getColor(R.color.white));
            mSelectDiscountBtnCoupon.setTextColor(AppUtil.getColor(R.color.CHAR_BLACK_COLOR));
            mSelectDiscountBtnRed.setBackgroundColor(AppUtil.getColor(R.color.BLUE_THEME));
            mSelectDiscountBtnRed.setTextColor(AppUtil.getColor(R.color.white));

            mAdapter.setDataAndRefresh(mRedPackageList);
        }
    }
}
