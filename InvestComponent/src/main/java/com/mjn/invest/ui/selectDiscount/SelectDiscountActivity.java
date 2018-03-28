package com.mjn.invest.ui.selectDiscount;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.bing.lan.comm.view.MyToolbar;
import com.mjn.invest.R;
import com.mjn.libs.base.IMainLibActivityContract;
import com.mjn.libs.base.MainLibActivity;
import com.mjn.libs.comm.bean.PayInfo;
import com.mjn.libs.view.pullRefresh.PullToRefreshLoadDataLayoutRecyclerView;

import java.io.Serializable;

import static com.mjn.libs.cons.IntentParamsKeyCons.INTENT_TO_PAY_ORDER_PROJECT_ID;
import static com.mjn.libs.cons.IntentParamsKeyCons.INTENT_TO_PAY_ORDER_PROJECT_MONEY;

/**
 * @author 蓝兵
 */
public class SelectDiscountActivity extends MainLibActivity<IMainLibActivityContract.IMainLibActivityPresenter> {

    private MyToolbar mToolbar;
    private TextView mSelectDiscountBtnCoupon;
    private TextView mSelectDiscountBtnRed;
    private com.mjn.libs.view.pullRefresh.PullToRefreshLoadDataLayoutRecyclerView mPullRefreshRecycler;
    private TextView mSelectDiscountBtnNotUse;
    private Long mMoney;
    private PayInfo mPayInfo;

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
        setToolBar(mToolbar, "优惠选择", true, 0);

        if (intent != null) {
            String money = intent.getStringExtra(INTENT_TO_PAY_ORDER_PROJECT_MONEY);
            if (!TextUtils.isEmpty(money)) {
                mMoney = Long.valueOf(money);
            }
            mPayInfo = (PayInfo) intent.getSerializableExtra(INTENT_TO_PAY_ORDER_PROJECT_ID);
        }
    }

    @Override
    protected void readyStartPresenter() {

    }
}
