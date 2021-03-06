package com.mjn.invest.ui.investDetail;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.view.MyToolbar;
import com.luojilab.component.componentlib.service.AutowiredService;
import com.luojilab.router.facade.annotation.Autowired;
import com.luojilab.router.facade.annotation.RouteNode;
import com.mjn.invest.R;
import com.mjn.invest.ui.investDown.InvestDetailDownFragment;
import com.mjn.invest.ui.investUp.InvestDetailUpFragment;
import com.mjn.invest.ui.payOrder.PayOrderActivity;
import com.mjn.libs.base.MainLibActivity;
import com.mjn.libs.comm.bean.IProduct;
import com.mjn.libs.comm.ui.login.LoginActivity;
import com.mjn.libs.cons.IntentParamsKeyCons;
import com.mjn.libs.cons.UIRouterCons;
import com.mjn.libs.utils.AppConfig;
import com.mjn.libs.utils.AppSpDataUtil;
import com.mjn.libs.utils.DragLayout;
import com.mjn.libs.utils.SPUtil;
import com.mjn.libs.utils.ToastAlone;
import com.mjn.libs.utils.Tools;

import static com.mjn.libs.cons.IntentParamsKeyCons.INTENT_TO_PAY_ORDER_PROJECT_ID;
import static com.mjn.libs.cons.IntentParamsKeyCons.INTENT_TO_PAY_ORDER_PROJECT_MONEY;

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
    private android.widget.TextView buyButton;

    @Autowired(name = UIRouterCons.INVEST_DETAIL_AUTOWIRED_PRODUCT_ID,
            desc = UIRouterCons.INVEST_DETAIL_AUTOWIRED_PRODUCT_ID_DESC)
    String mProjectId;

    @Autowired(name = UIRouterCons.INVEST_DETAIL_AUTOWIRED_PRODUCT_TITLE,
            desc = UIRouterCons.INVEST_DETAIL_AUTOWIRED_PRODUCT_TITLE_DESC)
    String mProjectTitle;

    /**
     * 上一页详情
     */
    private InvestDetailUpFragment up;
    /**
     * 下一页详情
     */
    private InvestDetailDownFragment down;

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
    protected void initViewAndData(final Intent intent) {
        //if (intent != null) {
        //    mProjectId = intent.getStringExtra(INVEST_DETAIL_AUTOWIRED_PRODUCT_ID);
        //    mProjectTitle = intent.getStringExtra(INVEST_DETAIL_AUTOWIRED_PRODUCT_TITLE);
        //} else {
        AutowiredService.Factory.getInstance().create().autowire(this);
        //}

        up = new InvestDetailUpFragment(mProjectId);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.first, up).commit();

        mToolbar = (MyToolbar) findViewById(R.id.toolbar);
        mDraglayout = (DragLayout) findViewById(R.id.draglayout);
        mFirst = (FrameLayout) findViewById(R.id.first);
        mSecond = (FrameLayout) findViewById(R.id.second);
        mTvInvsetmoney = (TextView) findViewById(R.id.tv_invsetmoney);
        buyButton = (TextView) findViewById(R.id.invest_detail_buy);

        setToolBar(mToolbar, mProjectTitle, true, R.drawable.icon_back_white);

        mDraglayout.setNextPageListener(new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {

            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppSpDataUtil.getInstance().isLogined()) {
                    Intent intent1 = new Intent(InvestDetailActivity.this, LoginActivity.class);
                    intent1.putExtra(IntentParamsKeyCons.INTENT_TO_LOGIN_ENTER_TYPE, 2);
                    startActivity(intent1, false, true);
                    return;
                }
                if (up.isComplete()) {
                    buyButton.setBackgroundColor(AppUtil.getColor(R.color.CHAR_GRAY_COLOR));
                    buyButton.setEnabled(false);
                } else {
                    if (up.checkMoney()) {
                        // 输入的金额超限了,炒股取值范围抛出异常解决
                        if (isChaoxian) {
                            ToastAlone.show("输入金额超限了");
                            isChaoxian = false;
                            return;
                        }
                        setMoney(up.getMoney());
                        if (((getMoney() - iProduct.getMinInvestment()) % iProduct.getIncreaseInvestment()) != 0) {
                            Tools.toastShow("投标金额只能是" + (iProduct.getIncreaseInvestment() / 1000) + "的整数倍");
                            up.setInputMoney();
                        } else if (getMoney() > iProduct.getRemainAmount()) {
                            Tools.toastShow("不能超过剩余金额");
                        } else if (iProduct.getCategoryId() == 4 && AppSpDataUtil.getInstance().getUserBean().getIfBuyPro().equals("Y")) {
                            Tools.toastShow("不能重复购买新手标");
                        } else {
                            up.removeLayoutChange();

                            Intent intent = new Intent(InvestDetailActivity.this, PayOrderActivity.class);
                            intent.putExtra(INTENT_TO_PAY_ORDER_PROJECT_MONEY, money + "");
                            intent.putExtra(INTENT_TO_PAY_ORDER_PROJECT_ID, mProjectId);
                            startActivity(intent, false, true);

                            long amount = up.getMoney() / 1000;
                            //                    double incomeOfInvestment = Double.valueOf(up.getShouyiMoney());
                       /*提交投资订单	$预置属性		字符串
                                项目名称	Title	字符串
                                产品类型	CategoryId	字符串
                                收益方式	IncomeMethod	字符串
                                项目期限	FinancialPeriod	字符串
                                风险等级	RiskLevel	字符串
                                借款年利率	AnnualYield	数值
                                投资金额	Amount	数值
                                投资收益	IncomeOfInvestment	数值
                                起头金额	MinInvestment	数字*/
                            //SensorsAnalyticsUtil.setSubmitInvestmentOrderViewT(AppConfig.context,
                            //        iProduct.getTitle(),
                            //        iProduct.getCategoryId() + "",
                            //        iProduct.getIncomeMethod(),
                            //        iProduct.getFinancialPeriod(),
                            //        iProduct.getRiskLevel(),
                            //        iProduct.getAnnualYield(),
                            //        amount,
                            //        iProduct.getMinInvestment() / 1000,
                            //        iProduct.getStartDate());
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void readyStartPresenter() {

    }

    /**
     * 要投的金额
     */
    private long money;

    public void setMoney(long money) {
        this.money = money;
    }

    public long getMoney() {
        return this.money;
    }

    public void addSecondFragment(String url) {

        if (down == null) {
            down = new InvestDetailDownFragment(url);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.second, down).commit();
        }
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
            buyButton.setBackgroundColor(AppUtil.getColor(R.color.BLUE_THEME));
        } else {
            buyButton.setBackgroundColor(AppUtil.getColor(R.color.BUY_BTN_CHAR));
        }
        buyButton.setText(name);
        buyButton.setEnabled(isCanBuy);
        getTongjiData(name);
    }

    private void getTongjiData(String status) {
               /* 项目名称	Title	字符串
                                风险等级	RiskLevel	字符串
                                产品类型	CategoryId	字符串
                                标的状态	Status	字符串
                                收益方式	IncomeMethod	字符串
                                起头金额	MinInvestment	数字
                                项目期限	FinancialPeriod	字符串
                                借款年利率	AnnualYield	数值*/
        if (iProduct != null) {
            // 保存实体类供统计
            SPUtil.getInstance().putBean(AppConfig.context, "orderState", iProduct);
            //SensorsAnalyticsUtil.setProductViewT(AppConfig.context, iProduct.getTitle(), iProduct.getRiskLevel(),
            //        iProduct.getCategoryId() + "", status, iProduct.getIncomeMethod(),
            //        iProduct.getMinInvestment() / 1000, iProduct.getFinancialPeriod(),
            //        iProduct.getAnnualYield(), iProduct.getStartDate());
        }
    }

    /**
     * 底部选中的投资金额显示
     *
     * @param investMoney
     */
    public void setBottomInvesetMoney(String investMoney) {
        mTvInvsetmoney.setText(investMoney);
    }

    IProduct iProduct;

    public void setIProduct(IProduct iProduct) {
        this.iProduct = iProduct;
    }
}
