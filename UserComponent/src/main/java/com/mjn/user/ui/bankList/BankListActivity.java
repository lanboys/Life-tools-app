package com.mjn.user.ui.bankList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bing.lan.comm.adapter.BaseRecyclerAdapter;
import com.bing.lan.comm.view.MyToolbar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.luojilab.component.componentlib.service.AutowiredService;
import com.luojilab.router.facade.annotation.Autowired;
import com.luojilab.router.facade.annotation.RouteNode;
import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.MainLibActivity;
import com.mjn.libs.comm.bean.IBankCard;
import com.mjn.libs.comm.bean.IBankCardList;
import com.mjn.libs.comm.bean.PayInfo;
import com.mjn.libs.comm.bean.QuotaData;
import com.mjn.libs.cons.UIRouterCons;
import com.mjn.libs.utils.AppSpDataUtil;
import com.mjn.user.R;
import com.mjn.user.ui.bankList.adapter.BankListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 蓝兵
 */

@RouteNode(path = UIRouterCons.USER_BANKLIST_ROUTE_NODE_PATH,
        desc = UIRouterCons.USER_BANKLIST_ROUTE_NODE_DESC)
public class BankListActivity extends MainLibActivity<IBankListContract.IBankListPresenter>
        implements IBankListContract.IBankListView, BaseRecyclerAdapter.OnItemClickListener {

    private com.bing.lan.comm.view.MyToolbar mToolbar;
    private com.handmark.pulltorefresh.library.PullToRefreshScrollView mPullRefreshScrollview;
    private RecyclerView mRecyclerView;
    private android.widget.TextView mMybankBtnAddCard;
    private android.widget.ImageView mMybankIcon;
    private android.widget.TextView mMybankBtnType;
    private android.widget.TextView mMybankLimit;

    private boolean mIsPayOrder = false;
    private BankListAdapter mAdapter;
    private ArrayList<IBankCard> mList = new ArrayList<>();
    private QuotaData quotaData = new QuotaData();

    @Autowired(name = UIRouterCons.USER_BANKLIST_AUTOWIRED_IS_PAYORDER,
            desc = UIRouterCons.USER_BANKLIST_AUTOWIRED_IS_PAYORDER_DESC)
    String isPayOrder;

    @Autowired(name = UIRouterCons.USER_AUTOWIRED_PAY_INFO,
            desc = UIRouterCons.USER_AUTOWIRED_PAY_INFO_DESC)
    PayInfo mPayInfo;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bank_list;
    }

    @Override
    protected IBankListContract.IBankListPresenter initPresenter() {
        BankListPresenter presenter = new BankListPresenter();
        presenter.setModule(new BankListModule());
        presenter.onAttachView(this);
        return presenter;
    }

    @Override
    protected void initViewAndData(Intent intent) {

        mToolbar = (MyToolbar) findViewById(R.id.toolbar);
        mPullRefreshScrollview = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mMybankBtnAddCard = (TextView) findViewById(R.id.mybank_btn_add_card);
        mMybankIcon = (ImageView) findViewById(R.id.mybank_icon);
        mMybankBtnType = (TextView) findViewById(R.id.mybank_btn_type);
        mMybankLimit = (TextView) findViewById(R.id.mybank_limit);

        mMybankLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("quota", quotaData);
                //Tools.pushScreen(BankLimit.class, bundle);
                showError("去银行限额页面");
            }
        });

        mMybankBtnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppSpDataUtil.getInstance().getUserBean().getAuthStatus().equals("REGISTER")) {
                    Bundle authBundle = new Bundle();
                    authBundle.putInt("type", 1);
                    //Tools.pushScreen(Auth.class, authBundle);
                    showError("去认证页面");
                } else {
                    showError("去添加银行卡页面");
                    //Tools.pushScreen(AddBank.class, null);
                    // 未绑定银行卡进行统计
                    //SensorsAnalyticsUtil.setAppClick(AppConfig.context, "添加银行卡", "我的银行卡");
                }
            }
        });

        setToolBar(mToolbar, "我的银行卡", true, 0);

        initRecyclerView();

        mPullRefreshScrollview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPullRefreshScrollview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mPresenter.updateBankCardList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }
        });

        AutowiredService.Factory.getInstance().create().autowire(this);
        if (isPayOrder != null && isPayOrder.equals("1")) {
            mIsPayOrder = true;
        }
        if (mPayInfo != null) {
            List<IBankCard> bankCards = mPayInfo.getBankCards();
            mAdapter.setDataAndRefresh(bankCards);
        }
    }

    @Override
    protected void readyStartPresenter() {
        mPresenter.updateBankCardList();
    }

    private void initRecyclerView() {

        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.TRANSPARENT)
                        .sizeResId(R.dimen.dimen_recycleView_divider_10dp)
                        .marginResId(R.dimen.dimen_recycleView_divider_left_margin_10dp,
                                R.dimen.dimen_recycleView_divider_right_margin_10dp)
                        .build());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new BankListAdapter();
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDataAndRefresh(mList);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.updateBankCardList();
    }

    @Override
    public void onRecyclerViewItemClick(View v, int position) {
        IBankCard data = mAdapter.getItem(position);

        if (data != null && data.getBankCardId() != null) {
            data.setIsDefault("Y");
            //setCurBank(data.getBankCardId());
            showError("设置默认银行卡");
        }
    }

    @Override
    public void updateBankListSuccess(ResponseListDataResult<IBankCardList> data) {
        IBankCardList iBankCardList = data.getList().get(0);
        List<IBankCard> bankCardList = iBankCardList.getBankCardList();
        String supportBanks = iBankCardList.getSupportBanks();
        quotaData.quotaList = iBankCardList.getQuotas();

        if (bankCardList.isEmpty()) {
            mMybankBtnAddCard.setVisibility(View.VISIBLE);
        } else {
            mMybankBtnAddCard.setVisibility(View.GONE);
        }

        mAdapter.setDataAndRefresh(bankCardList);
        mAdapter.notifyDataSetChanged();

        mMybankBtnType.setText("平台支持银行: ");
        mMybankBtnType.append(supportBanks);
    }

    @Override
    public void setDefaultBankCardSuccess(ResponseListDataResult<IBankCard> data) {
        if (mIsPayOrder) {
            finish();
        } else {
            showToast(data.getMessage());
        }
    }
}
