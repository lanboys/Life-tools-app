package com.mjn.invest.ui.investDetail;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bing.lan.comm.view.MyToolbar;
import com.luojilab.component.componentlib.service.AutowiredService;
import com.luojilab.router.facade.annotation.Autowired;
import com.luojilab.router.facade.annotation.RouteNode;
import com.mjn.invest.R;
import com.mjn.invest.ui.investDown.InvestDetailDownFragment;
import com.mjn.invest.ui.investUp.InvestDetailUpFragment;
import com.mjn.libs.base.MainLibActivity;
import com.mjn.libs.cons.UIRouterCons;
import com.mjn.libs.utils.DragLayout;
import com.mjn.libs.utils.Tools;

/**
 * @author 蓝兵
 */

@RouteNode(path = UIRouterCons.INVEST_DETAIL_ROUTE_NODE_PATH,
        desc = UIRouterCons.INVEST_DETAIL_ROUTE_NODE_DESC)
public class InvestDetailActivity extends MainLibActivity<IInvestDetailContract.IInvestDetailPresenter>
        implements IInvestDetailContract.IInvestDetailView {

    private com.bing.lan.comm.view.MyToolbar mToolbar;
    private com.mjn.libs.utils.DragLayout mDraglayout;
    private android.widget.FrameLayout mFirst;
    private android.widget.FrameLayout mSecond;
    private android.widget.TextView mTvInvsetmoney;
    private android.widget.TextView mInvestDetailBuy;
    FragmentTransaction fragmentTransaction;

    @Autowired(name = UIRouterCons.INVEST_DETAIL_AUTOWIRED_PRODUCT_ID,
            desc = UIRouterCons.INVEST_DETAIL_AUTOWIRED_PRODUCT_ID_DESC)
    String mProjectId;

    @Autowired(name = UIRouterCons.INVEST_DETAIL_AUTOWIRED_PRODUCT_TITLE,
            desc = UIRouterCons.INVEST_DETAIL_AUTOWIRED_PRODUCT_TITLE_DESC)
    String mProjectTitle;

    /**
     * 上一页详情
     */
    private InvestDetailUpFragment up = new InvestDetailUpFragment();
    /**
     * 下一页详情
     */
    private InvestDetailDownFragment down = new InvestDetailDownFragment();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_invest_detail;
    }

    @Override
    protected IInvestDetailContract.IInvestDetailPresenter initPresenter() {
        InvestDetailPresenter presenter = new InvestDetailPresenter();
        presenter.setModule(new InvestDetailModule());
        presenter.onAttachView(this);

        return presenter;
    }

    @Override
    protected void initViewAndData(Intent intent) {
        AutowiredService.Factory.getInstance().create().autowire(this);

        mToolbar = (MyToolbar) findViewById(R.id.toolbar);
        mDraglayout = (DragLayout) findViewById(R.id.draglayout);
        mFirst = (FrameLayout) findViewById(R.id.first);
        mSecond = (FrameLayout) findViewById(R.id.second);
        mTvInvsetmoney = (TextView) findViewById(R.id.tv_invsetmoney);
        mInvestDetailBuy = (TextView) findViewById(R.id.invest_detail_buy);

        setToolBar(mToolbar, "投资详情页", true, 0);
    }

    @Override
    protected void readyStartPresenter() {
        setToolBar(mToolbar, mProjectTitle, true, 0);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.first, up).add(R.id.second, down).commit();

        mDraglayout.setNextPageListener(new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {

                showError("显示下一页");
            }
        });
    }

    private boolean isChaoxian;

    /**
     * 输入是否超限提示
     *
     * @return
     */
    public void isChaoxian(Boolean isChaoxian) {
        this.isChaoxian = isChaoxian;
    }

    public void setBuyButton(boolean isCanBuy, String name) {
        if (isCanBuy) {
            buyButton.setBackgroundColor(Tools.getResourceColor(R.color.BLUE_THEME));
        } else {
            buyButton.setBackgroundColor(Tools.getResourceColor(R.color.BUY_BTN_CHAR));
        }
        buyButton.setText(name);
        buyButton.setEnabled(isCanBuy);
        getTongjiData(name);
    }

    /**
     * 底部选中的投资金额显示
     *
     * @param investMoney
     */
    public void setBottomInvesetMoney(String investMoney) {
        tv_invsetmoney.setText(investMoney);
    }
}
