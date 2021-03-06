package com.mjn.invest.ui.payOrder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bing.lan.comm.utils.SoftInputUtil;
import com.bing.lan.comm.view.MyToolbar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.luojilab.component.componentlib.service.JsonService;
import com.mjn.invest.R;
import com.mjn.invest.ui.investUp.MyPullToRefreshScrollView;
import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.MainLibActivity;
import com.mjn.libs.comm.bean.IBankCard;
import com.mjn.libs.comm.bean.Order;
import com.mjn.libs.comm.bean.OrderBean;
import com.mjn.libs.comm.bean.PayInfo;
import com.mjn.libs.comm.bean.UserCoupon;
import com.mjn.libs.cons.IntentRequestCode;
import com.mjn.libs.utils.AppConfig;
import com.mjn.libs.utils.AppSpDataUtil;
import com.mjn.libs.utils.SPUtil;
import com.mjn.libs.utils.TimeCount;
import com.mjn.libs.utils.Tools;

import static com.mjn.libs.cons.IntentParamsKeyCons.INTENT_TO_PAY_ORDER_PROJECT_ID;
import static com.mjn.libs.cons.IntentParamsKeyCons.INTENT_TO_PAY_ORDER_PROJECT_MONEY;
import static com.mjn.libs.cons.UIRouterCons.APP_HOST;
import static com.mjn.libs.cons.UIRouterCons.MAIN_APP_ROUTE_NODE_PATH;
import static com.mjn.libs.cons.UIRouterCons.UI_ROUTER_SCHEME;
import static com.mjn.libs.cons.UIRouterCons.USER_AUTOWIRED_PAY_INFO;
import static com.mjn.libs.cons.UIRouterCons.USER_BANKLIST_AUTOWIRED_IS_PAYORDER;
import static com.mjn.libs.cons.UIRouterCons.USER_BANKLIST_ROUTE_NODE_PATH;
import static com.mjn.libs.cons.UIRouterCons.USER_DISCOUNT_AUTOWIRED_MONEY;
import static com.mjn.libs.cons.UIRouterCons.USER_DISCOUNT_ROUTE_NODE_PATH;
import static com.mjn.libs.cons.UIRouterCons.USER_HOST;

/**
 * @author 蓝兵
 */
public class PayOrderActivity extends MainLibActivity<IPayOrderContract.IPayOrderPresenter>
        implements IPayOrderContract.IPayOrderView, View.OnClickListener, TimeCount.CountDownTimerListener {

    private MyToolbar mToolbar;
    private MyPullToRefreshScrollView mPullRefreshScrollview;
    private TextView mPayOrderMoney;
    private RelativeLayout mPayOrderDiscountLayout;
    private ImageView mPayOrderCouponArrow;
    private TextView mPayOrderCoupon;
    private TextView mPayOrderBalance;
    private LinearLayout mPayOrderBankLayout;
    private ImageView mPayOrderBankIcon;
    private TextView mPayOrderBankName;
    private TextView mPayOrderBankNumber;
    private TextView mPayOrderBankMoney;
    private TextView mPayOrderLimit;
    private TextView mPayOrderBtnLimit;
    private TextView mPayOrderBtnPay;
    private TextView mPayOrderBtnProtocol;
    private RelativeLayout mPayOrderSmsLayout;
    private TextView mPayOrderSmsPhone;
    private android.widget.EditText mPayOrderSmsInput;
    private TextView mPayOrderSmsBtnSend;
    private TextView mPayOrderSmsBtnCancel;
    private TextView mPayOrderSmsBtnOk;
    private String mProjectId;
    private Long mMoney;
    private OrderBean mOrderBean;
    private PayInfo mPayInfo = new PayInfo();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pay_order;
    }

    @Override
    protected IPayOrderContract.IPayOrderPresenter initPresenter() {
        PayOrderPresenter presenter = new PayOrderPresenter();
        presenter.setModule(new PayOrderModule());
        presenter.onAttachView(this);
        return presenter;
    }

    @Override
    protected void initViewAndData(Intent intent) {
        mToolbar = (MyToolbar) findViewById(R.id.toolbar);
        mPullRefreshScrollview = (MyPullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        mPayOrderMoney = (TextView) findViewById(R.id.pay_order_money);
        mPayOrderDiscountLayout = (RelativeLayout) findViewById(R.id.pay_order_discount_layout);
        mPayOrderCouponArrow = (ImageView) findViewById(R.id.pay_order_coupon_arrow);
        mPayOrderCoupon = (TextView) findViewById(R.id.pay_order_coupon);
        mPayOrderBalance = (TextView) findViewById(R.id.pay_order_balance);
        mPayOrderBankLayout = (LinearLayout) findViewById(R.id.pay_order_bank_layout);
        mPayOrderBankIcon = (ImageView) findViewById(R.id.pay_order_bank_icon);
        mPayOrderBankName = (TextView) findViewById(R.id.pay_order_bank_name);
        mPayOrderBankNumber = (TextView) findViewById(R.id.pay_order_bank_number);
        mPayOrderBankMoney = (TextView) findViewById(R.id.pay_order_bank_money);
        mPayOrderLimit = (TextView) findViewById(R.id.pay_order_limit);
        mPayOrderBtnLimit = (TextView) findViewById(R.id.pay_order_btn_limit);
        mPayOrderBtnPay = (TextView) findViewById(R.id.pay_order_btn_pay);
        mPayOrderBtnProtocol = (TextView) findViewById(R.id.pay_order_btn_protocol);
        mPayOrderSmsLayout = (RelativeLayout) findViewById(R.id.pay_order_sms_layout);
        mPayOrderSmsPhone = (TextView) findViewById(R.id.pay_order_sms_phone);
        mPayOrderSmsInput = (EditText) findViewById(R.id.pay_order_sms_input);
        mPayOrderSmsBtnSend = (TextView) findViewById(R.id.pay_order_sms_btn_send);
        mPayOrderSmsBtnCancel = (TextView) findViewById(R.id.pay_order_sms_btn_cancel);
        mPayOrderSmsBtnOk = (TextView) findViewById(R.id.pay_order_sms_btn_ok);

        setToolBar(mToolbar, "订单支付", true, 0);

        if (intent != null) {
            mProjectId = intent.getStringExtra(INTENT_TO_PAY_ORDER_PROJECT_ID);
            String money = intent.getStringExtra(INTENT_TO_PAY_ORDER_PROJECT_MONEY);
            if (!TextUtils.isEmpty(money)) {
                mMoney = Long.valueOf(money);
            }
        }

        mPullToRefreshBase = mPullRefreshScrollview;
        mPullRefreshScrollview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPullRefreshScrollview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mPresenter.getOrderInfo(mProjectId, mMoney + "");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }
        });

        mPayOrderDiscountLayout.setOnClickListener(this);
        mPayOrderSmsLayout.setOnClickListener(this);
        mPayOrderSmsBtnOk.setOnClickListener(this);
        mPayOrderSmsBtnCancel.setOnClickListener(this);
        mPayOrderSmsBtnSend.setOnClickListener(this);
        mPayOrderBtnProtocol.setOnClickListener(this);
        mPayOrderBtnPay.setOnClickListener(this);
        mPayOrderBtnLimit.setOnClickListener(this);
        mPayOrderBankLayout.setOnClickListener(this);
    }

    @Override
    protected void readyStartPresenter() {
        mPresenter.getOrderInfo(mProjectId, mMoney + "");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.pay_order_discount_layout == id) {
            discountClick(view);
        } else if (R.id.pay_order_bank_layout == id) {
            bankClick(view);
        } else if (R.id.pay_order_btn_limit == id) {
            limitClick(view);
        } else if (R.id.pay_order_btn_pay == id) {
            buyClick(view);
        } else if (R.id.pay_order_btn_protocol == id) {
            protocolClick(view);
        } else if (R.id.pay_order_sms_btn_send == id) {
            sendClick(view);
        } else if (R.id.pay_order_sms_btn_cancel == id) {
            cancelClick(view);
        } else if (R.id.pay_order_sms_btn_ok == id) {
            okClick(view);
        } else if (R.id.pay_order_sms_layout == id) {
            smsLayoutClick(view);
        }
    }

    /**
     * 优惠券点击
     */
    public void discountClick(View view) {
        if (mPayInfo == null) {
            return;
        }

        if (mPayInfo.getLuckeyMoneys() == null && mPayInfo.getCoupons() == null || mPayInfo.getCoupons().isEmpty() &&
                mPayInfo.getLuckeyMoneys().isEmpty()) {
            Tools.toastShow("您没有优惠券");
            return;
        }

        //showError("去选择优惠券");

        //Intent intent = new Intent(PayOrderActivity.this, SelectDiscountActivity.class);
        //intent.putExtra(INTENT_TO_PAY_ORDER_PROJECT_MONEY, mMoney + "");
        //intent.putExtra(INTENT_TO_PAY_ORDER_PROJECT_ID, mPayInfo);
        //startActivityForResult(intent, IntentRequestCode.REQUEST_CODE_TO_GET_DISCOUNT);

        Bundle bundle = new Bundle();
        bundle.putString(USER_DISCOUNT_AUTOWIRED_MONEY, mMoney + "");
        bundle.putString(USER_AUTOWIRED_PAY_INFO, JsonService.Factory.getInstance().create().toJsonString(mPayInfo));

        UIRouter.getInstance().openUri(
                this,
                UI_ROUTER_SCHEME + USER_HOST + USER_DISCOUNT_ROUTE_NODE_PATH,
                bundle,
                IntentRequestCode.REQUEST_CODE_TO_GET_DISCOUNT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IntentRequestCode.REQUEST_CODE_TO_GET_DISCOUNT && RESULT_OK == resultCode) {
            Bundle bundle = data.getBundleExtra("discount");
            if (bundle != null) {//优惠卷选择
                UserCoupon userCoupon = (UserCoupon) bundle.getSerializable("coupon");
                isUseCoupon = bundle.getBoolean("isUse", true);
                mUserCoupon = userCoupon;
                if (userCoupon != null) {
                    if (userCoupon.getCouponValue() > 0) {//红包
                        mPayOrderCoupon.setText(Tools.getDecimal((double) userCoupon.getCouponValue() / (double) 1000));
                        mPayOrderCoupon.append("元");
                    } else {
                        mPayOrderCoupon.setText("加息" + userCoupon.getAddtionAmount() + "%");
                    }
                } else {
                    mPayOrderCoupon.setText("0元");
                }
            }
        }
        readyStartPresenter();
    }

    /**
     * 银行卡点击
     */
    public void bankClick(View view) {

        //Intent intent = new Intent(PayOrderActivity.this, BankListActivity.class);
        //intent.putExtra(INTENT_TO_SELECT_BANK_ISBACK, true);
        //intent.putExtra(INTENT_TO_SELECT_BANK_INFO, mPayInfo);
        //startActivity(intent);

        Bundle bundle = new Bundle();
        bundle.putString(USER_BANKLIST_AUTOWIRED_IS_PAYORDER, "1");
        bundle.putString(USER_AUTOWIRED_PAY_INFO, JsonService.Factory.getInstance().create().toJsonString(mPayInfo));

        UIRouter.getInstance().openUri(
                this,
                UI_ROUTER_SCHEME + USER_HOST + USER_BANKLIST_ROUTE_NODE_PATH,
                bundle);
    }

    /**
     * 限额说明点击事件
     */
    public void limitClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("payInfo", mPayInfo);
        //Tools.pushScreen(BankLimit.class, bundle);
        showError("去看限额");
    }

    /**
     * 立即支付
     */
    public void buyClick(View view) {
        if (mPayInfo.getBankCards() != null && !mPayInfo.getBankCards().isEmpty()) {
            mPresenter.sendPayVcode(mProjectId, "", "", mMoney + "");
        } else {
            Tools.toastShow("请先绑定银行卡");
        }
    }

    /**
     * 咨询服务协议
     */
    public void protocolClick(View view) {
        Bundle bundle = new Bundle();
        // json数据保存,请求无token出错咨询协议看不到. UserModel中findUserInfo接口重新存储了一下。
        // 登录成功后保存的有token信息. 解决问题登录将token存到sp
        String token = SPUtil.getInstance().getString("token");
        bundle.putString("url", AppConfig.HTTP_HOST + AppConfig.INSTALLMENT_URL + "?token=" + token);
        bundle.putString("title", "出借咨询服务协议");
        toHtml5Pager(bundle);
    }

    private TimeCount tc;

    /**
     * 发送验证码按钮
     */
    public void sendClick(View view) {

        SoftInputUtil.closeSoftInput(this);

        mPresenter.reSendPayVcode(mOrderBean.getOrderId() + "");
        if (tc != null) {
            tc.cancel();
            tc = null;
        }
        tc = new TimeCount(60000, 1000, this);
        tc.start();
    }

    /**
     * 取消验证码按钮
     */
    public void cancelClick(View view) {
        SoftInputUtil.closeSoftInput(this);

        mPayOrderSmsLayout.setVisibility(View.GONE);
    }

    /**
     * 确认短信验证码获取
     */
    public void okClick(View view) {

        SoftInputUtil.closeSoftInput(this);
        //payOrderModel.pay(mPayOrderSmsInput.getText().toString());

        showError("收到验证码后去支付");
    }

    /**
     * 短信验证码布局
     */
    public void smsLayoutClick(View view) {

    }

    /**
     * 显示验证码布局
     */
    public void showSmsLayout(String phone) {

        mPayOrderSmsPhone.setText("已向您");
        mPayOrderSmsPhone.append(phone);
        mPayOrderSmsPhone.append("的手机发送短信验证码");
        mPayOrderSmsLayout.setVisibility(View.VISIBLE);
        if (tc != null) {
            tc.cancel();
            tc = null;
        }
        tc = new TimeCount(60000, 1000, this);
        tc.start();
    }

    @Override
    public void initTimer() {
        mPayOrderSmsBtnSend.setClickable(false);
    }

    @Override
    public void finishTimer() {
        mPayOrderSmsBtnSend.setText("重新获取");
        mPayOrderSmsBtnSend.setClickable(true);
    }

    @Override
    public void updateTimer(long millisUntilFinished) {
        long time = (millisUntilFinished / 1000);
        if (time < 10) {
            mPayOrderSmsBtnSend.setText("0");
            mPayOrderSmsBtnSend.append(String.valueOf(time));
            mPayOrderSmsBtnSend.append("s");
        } else {
            mPayOrderSmsBtnSend.setText(String.valueOf(time));
            mPayOrderSmsBtnSend.append("s");
        }
    }

    /**
     * 选择的优惠信息
     */
    private UserCoupon mUserCoupon;
    /**
     * 是否使用优惠
     */
    private boolean isUseCoupon = true;

    /**
     * 绑定的银行卡id
     */
    private int bankId;

    @Override
    public void updateSuccess(ResponseListDataResult<PayInfo> data) {
        mPayInfo = data.getList().get(0);

        mPayOrderMoney.setText(String.valueOf(mMoney / 1000));
        mPayOrderMoney.append("元");

        boolean isLuckey = false;
        if (mUserCoupon != null) {
            if (mUserCoupon.getCouponValue() > 0) {//红包
                isLuckey = true;
                mPayOrderCoupon.setText(Tools.getDecimal((double) mUserCoupon.getCouponValue() / (double) 1000));
                mPayOrderCoupon.append("元");
            } else {
                isLuckey = false;
                mPayOrderCoupon.setText("加息" + mUserCoupon.getAddtionAmount() + "%");
            }
        } else if (isUseCoupon) {
            if (mPayInfo.getLuckeyMoneys() != null && !mPayInfo.getLuckeyMoneys().isEmpty()) {
                isLuckey = true;
                mUserCoupon = mPayInfo.getLuckeyMoneys().get(0);
                mPayOrderCoupon.setText(String.valueOf(mPayInfo.getLuckeyMoneys().get(0).getCouponValue() / 1000));
                mPayOrderCoupon.append("元");
            } else if (mPayInfo.getCoupons() != null && !mPayInfo.getCoupons().isEmpty()) {
                mUserCoupon = mPayInfo.getCoupons().get(0);
                isLuckey = false;
                mPayOrderCoupon.setText("加息" + mPayInfo.getCoupons().get(0).getAddtionAmount() + "%");
            }
        }
        if (AppSpDataUtil.getInstance().getUserBean().getAvailableAmount() != null) {
            mPayOrderBalance.setText(Tools.getDecimal((double) AppSpDataUtil.getInstance().getUserBean().getAvailableAmount() / (double) 1000));
            mPayOrderBalance.append("元");
        }
        if (mPayInfo.getBankCards() != null) {
            for (IBankCard iBankCard : mPayInfo.getBankCards()) {
                if (iBankCard.getIsDefault().equals("Y")) {
                    mPayOrderBankIcon.setImageResource(AppConfig.iconMap.get(iBankCard.getBankCode()));
                    mPayOrderBankName.setText(iBankCard.getBankName());
                    mPayOrderBankNumber.setText("尾号" + iBankCard.getCardNo());
                    double money = 0;
                    long amoney = (AppSpDataUtil.getInstance().getUserBean().getAvailableAmount() / 10) * 10;
                    if (mUserCoupon != null) {
                        money = (double) (mMoney) - (double) (isLuckey ?
                                (mUserCoupon.getCouponValue()) : 0) - (double) amoney;
                    } else {
                        money = (double) (mMoney) - (double) (isLuckey ?
                                (mPayInfo.getLuckeyMoneys().get(0).getCouponValue()) : 0) - (double) amoney;
                    }
                    if (money > 0) {
                        mPayOrderBankMoney.setText(Tools.getDecimal(money / (double) 1000));
                    } else {
                        mPayOrderBankMoney.setText("0.00");
                    }
                    bankId = iBankCard.getBankCardId();

                    mPayOrderBankMoney.append("元");
                    mPayOrderLimit.setText("本卡本次最多可支付");
                    mPayOrderLimit.append(iBankCard.getQuota() + "");
                    mPayOrderLimit.append("元");
                    break;
                }
            }
        } else {
            showError("您需要先绑定银行卡");
        }
    }

    @Override
    public void sendVcodeSuccess(ResponseListDataResult<OrderBean> data) {
        mOrderBean = data.getList().get(0);
        if (mOrderBean.isSuccess()) {
            showToast("余额支付成功");
            UIRouter.getInstance().openUri(
                    this,
                    UI_ROUTER_SCHEME + APP_HOST + MAIN_APP_ROUTE_NODE_PATH,
                    null);
        } else {
            // 弹框
            if (!TextUtils.isEmpty(mOrderBean.getPhone())) {
                showSmsLayout(mOrderBean.getPhone());
            }
        }
    }

    @Override
    public void reSendVcodeSuccess(ResponseListDataResult<Order> data) {
        showToast(data.getMessage());
    }

    @Override
    public void payOrderSuccess(ResponseListDataResult<OrderBean> data) {
        OrderBean orderBean = data.getList().get(0);

        showError("去主页");
    }
}
