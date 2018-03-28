package com.mjn.invest.ui.investUp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.mjn.invest.R;
import com.mjn.invest.ui.investDetail.InvestDetailActivity;
import com.mjn.libs.api.ResponseListDataResult;
import com.mjn.libs.base.MainLibFragment;
import com.mjn.libs.comm.bean.IProduct;
import com.mjn.libs.utils.AppConfig;
import com.mjn.libs.utils.LogUtil;
import com.mjn.libs.utils.Tools;
import com.mjn.libs.view.ruler.SlideRuler;
import com.mjn.libs.view.ruler.SlideRulerDataInterface;

import java.util.List;

/**
 * @author 蓝兵
 */
public class InvestDetailUpFragment extends MainLibFragment<IInvestDetailUpContract.IInvestDetailUpPresenter>
        implements IInvestDetailUpContract.IInvestDetailUpView {

    private MyPullToRefreshScrollView mPullRefreshScrollview;
    private TextView mInvestDetailTitle;
    private TextView mInvestDetailEarnings;
    private TextView mInvestDetailEarningsAdd1;
    private TextView mInvestDetailEarningsAdd;
    private TextView mInvestDetailEarningsAdd2;
    private TextView mInvestDetailType;
    private TextView mInvestDetailStartMoney;
    private TextView mStartTime;
    private EditText input;
    private TextView mTvQuanegoumai;
    private ImageView mInvestDetailSub;
    private ImageView mInvestDetailAdd;
    private com.mjn.libs.view.ruler.SlideRuler loopScanleView;
    private TextView endMoney;
    private TextView mTvTipTime;
    private TextView mInvestDetailLeftMoney;
    private TextView mTvMaxmoney;
    private LinearLayout mInvestDetailKeyongll;
    private TextView mInvestDetailUserMoney;
    private TextView mInvestDetailHuankuan;
    private TextView mInvestDetailBaozhang;
    private TextView mInvestDetailXieyishu;
    private String mProjectId;

    private IProduct mIProduct = new IProduct();

    @SuppressLint("ValidFragment")
    public InvestDetailUpFragment(String projectId) {
        mProjectId = projectId;
    }

    public InvestDetailUpFragment() {

    }

    public static InvestDetailUpFragment newInstance(String title) {
        InvestDetailUpFragment fragment = new InvestDetailUpFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_invest_detail_up;
    }

    @Override
    protected IInvestDetailUpContract.IInvestDetailUpPresenter initPresenter() {
        InvestDetailUpPresenter presenter = new InvestDetailUpPresenter();
        presenter.setModule(new InvestDetailUpModule());
        presenter.onAttachView(this);
        return presenter;
    }

    InvestDetailActivity investDetail;

    @Override
    protected void initViewAndData(Intent intent, Bundle arguments) {
        investDetail = (InvestDetailActivity) getActivity();

        mPullRefreshScrollview = (MyPullToRefreshScrollView) mContentView.findViewById(R.id.pull_refresh_scrollview);
        mPullToRefreshBase = mPullRefreshScrollview;
        mInvestDetailTitle = (TextView) mContentView.findViewById(R.id.invest_detail_title);
        mInvestDetailEarnings = (TextView) mContentView.findViewById(R.id.invest_detail_earnings);
        mInvestDetailEarningsAdd1 = (TextView) mContentView.findViewById(R.id.invest_detail_earnings_add1);
        mInvestDetailEarningsAdd = (TextView) mContentView.findViewById(R.id.invest_detail_earnings_add);
        mInvestDetailEarningsAdd2 = (TextView) mContentView.findViewById(R.id.invest_detail_earnings_add2);
        mInvestDetailType = (TextView) mContentView.findViewById(R.id.invest_detail_type);
        mInvestDetailStartMoney = (TextView) mContentView.findViewById(R.id.invest_detail_startMoney);
        mStartTime = (TextView) mContentView.findViewById(R.id.startTime);
        input = (EditText) mContentView.findViewById(R.id.invest_detail_input);
        mTvQuanegoumai = (TextView) mContentView.findViewById(R.id.tv_quanegoumai);
        mInvestDetailSub = (ImageView) mContentView.findViewById(R.id.invest_detail_sub);
        mInvestDetailAdd = (ImageView) mContentView.findViewById(R.id.invest_detail_add);
        loopScanleView = (SlideRuler) mContentView.findViewById(R.id.loopScanleView);
        endMoney = (TextView) mContentView.findViewById(R.id.invest_detail_end_money);
        mTvTipTime = (TextView) mContentView.findViewById(R.id.tv_tipTime);
        mInvestDetailLeftMoney = (TextView) mContentView.findViewById(R.id.invest_detail_leftMoney);
        mTvMaxmoney = (TextView) mContentView.findViewById(R.id.tv_maxmoney);
        mInvestDetailKeyongll = (LinearLayout) mContentView.findViewById(R.id.invest_detail_keyongll);
        mInvestDetailUserMoney = (TextView) mContentView.findViewById(R.id.invest_detail_userMoney);
        mInvestDetailHuankuan = (TextView) mContentView.findViewById(R.id.invest_detail_huankuan);
        mInvestDetailBaozhang = (TextView) mContentView.findViewById(R.id.invest_detail_baozhang);
        mInvestDetailXieyishu = (TextView) mContentView.findViewById(R.id.invest_detail_xieyishu);

        mPullRefreshScrollview.setNeedConsumeTouch(true);
        mPullRefreshScrollview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPullRefreshScrollview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (mProjectId != null) {
                    mPresenter.updateInvestDetail(mProjectId);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }
        });
        mPullRefreshScrollview.addOnLayoutChangeListener(listener);

        //全额购买
        mTvQuanegoumai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(String.valueOf(mIProduct.getMaxInvestment() / 1000));
                loopScanleView.setCurrentValue(Integer.valueOf(input.getText().toString()));
                // 刷新
                loopScanleView.updateView(0, 0);
                checkMoney();
            }
        });

        // 刻度滚动
        loopScanleView.setSlideRulerDataInterface(new SlideRulerDataInterface() {
            @Override
            public void getText(String source) {
                try {
                    LogUtil.i("InvestDetailUp   slideRulerDataInterface=========" + source);
                    int data = 0;
                    if (!TextUtils.isEmpty(source)) {
                        data = Integer.valueOf(source);
                    }
                    int step = (int) (mIProduct.getIncreaseInvestment() / 1000);
                    data = (data / step) * step;
                    if (currMoney != data) {
                        currMoney = data;
                        input.setText(source);
                        // 滚动标尺底部显示的投标的钱
                        investDetail.setBottomInvesetMoney(currMoney + "元");
                        checkMoney();
                    }
                } catch (Exception e) {
                    log.e("getText():  " + e.getLocalizedMessage());
                }
            }
        });

        InputFilter[] inputFilters = new InputFilter[1];
        inputFilters[0] = inputFilter;
        input.setFilters(inputFilters);

        // 默认显示设置
        loopScanleView.setCurrentValue(0);
        endMoney.setText("一 一");
    }

    int currMoney = 0;

    InputFilter inputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // 输入内容为数字在正则限制
            String speChat = "^\\d+$";
            String sourcedata = source.toString();
            if (sourcedata.matches(speChat) == false) {
                return "";
            }
            int data = 0;
            if (!TextUtils.isEmpty(source)) {
                data = Integer.valueOf(source.toString());
            }
            int step = (int) (mIProduct.getIncreaseInvestment() / 1000);
            data = (data / step) * step;
            LogUtil.i("InvestDetailUp   filter=========" + data);
            if (currMoney != data) {
                currMoney = data;
                loopScanleView.setCurrentValue(data);
                // 输入框显示内容底部显示的元
                investDetail.setBottomInvesetMoney(data + "元");
            }
            return null;
        }
    };

    @Override
    protected void readyStartPresenter() {
        mPresenter.updateInvestDetail(mProjectId);
    }

    public boolean checkMoney() {
        boolean check = false;
        try {
            LogUtil.i("InvestDetailUp   checkMoney=========" + input.getText().toString());
            check = true;
            int mValue = 0;
            if (TextUtils.isEmpty(input.getText().toString())) {
                mValue = 0;
            } else {
                mValue = Integer.valueOf(input.getText().toString());
            }
            int max = (int) (mIProduct.getMaxInvestment() / 1000);
            int min = (int) (mIProduct.getMinInvestment() / 1000);
            int step = (int) (mIProduct.getIncreaseInvestment() / 1000);
            if (mValue > max) {
                mValue = max;
                Tools.toastShow("最多可投" + mIProduct.getMaxInvestment() / 1000 + "元");
                check = false;
            } else if (mValue < min) {
                mValue = min;
                Tools.toastShow("最少可投" + mIProduct.getMinInvestment() / 1000 + "元");
                check = false;
            } else {
                mValue = (mValue / step) * step;
            }
            setMoney(mValue);
            if (!TextUtils.isEmpty(input.getText().toString())) {
                input.setText(String.valueOf(mValue));
                input.setSelection(input.getText().length());
                endMoney.setText(getEarming());
            } else {
                loopScanleView.setCurrentValue(0);
                endMoney.setText("一 一");
            }
            LogUtil.i("checkMoney()" + "mValue=" + mValue + "-------input.getText().toString()=" + input.getText().toString());

            // 输入框为空显示0元
            if (!TextUtils.isEmpty(input.getText().toString())) {
                investDetail.setBottomInvesetMoney(input.getText().toString() + "元");
            } else {
                investDetail.setBottomInvesetMoney(0 + "元");
            }
        } catch (Exception e) {
            // 解决mValue值超出范围抛出的异常
            e.printStackTrace();
            Tools.toastShow("最多可投" + mIProduct.getMaxInvestment() / 1000 + "元");
            input.setText(mIProduct.getMaxInvestment() / 1000 + "");
            mInvestDetailEarnings.setText(String.valueOf(mIProduct.getAnnualYield() - mIProduct.getAddYield()));
            // 输入的金额超限了,
            investDetail.isChaoxian(true);
            // 输入框为空显示0元
            if (!TextUtils.isEmpty(input.getText().toString())) {
                investDetail.setBottomInvesetMoney(input.getText().toString() + "元");
            } else {
                investDetail.setBottomInvesetMoney(0 + "元");
            }
        }
        return check;
    }

    public void addLayoutChange() {
        if (mPullRefreshScrollview != null && listener != null) {
            mPullRefreshScrollview.addOnLayoutChangeListener(listener);
        }
    }

    public void removeLayoutChange() {
        mPullRefreshScrollview.removeOnLayoutChangeListener(listener);
    }

    private View.OnLayoutChangeListener listener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > AppConfig.SCREEN_HEIGHT / 3)) {

            } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > AppConfig.SCREEN_HEIGHT / 3)) {
                checkMoney();
            }
        }
    };
    /**
     * 投资金额
     */
    private long money;

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        money *= 1000;
        if (money < mIProduct.getMinInvestment()) {
            money = mIProduct.getMinInvestment();
            showError("最少可投" + mIProduct.getMinInvestment() / 1000 + "元");
        } else if (money > mIProduct.getMaxInvestment()) {
            money = mIProduct.getMaxInvestment();
            showError("最多可投" + mIProduct.getMaxInvestment() / 1000 + "元");
        }
        this.money = money;
        countEarming();
    }

    /**
     * 预期收益
     */
    private String earming;

    public void countEarming() {
        earming = Tools.getDecimal(((double) this.money / (double) 1000) * (double)
                (mIProduct.getAnnualYield() / (float) 100) / (double) 365 * (double) mIProduct.getFinancialPeriodDay());
    }

    public String getEarming() {
        return earming;
    }

    /**
     * 是否进度已满
     *
     * @return
     */
    public boolean isComplete() {
        try {
            return mIProduct.getInvestmentRatio().intValue() >= 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置最小金额
     */
    public void setInputMoney() {
        input.setText(String.valueOf(mIProduct.getMinInvestment() / 1000));
        input.setSelection(input.getText().length());
        setMoney(Long.parseLong(input.getText().toString()));
    }

    @Override
    public void updateSuccess(ResponseListDataResult<IProduct> listDataResult) {

        List<IProduct> list = listDataResult.getList();
        IProduct iProduct = list.get(0);

        if (iProduct == null) {
            return;
        }
        mIProduct = iProduct;
        investDetail.setIProduct(iProduct);
        investDetail.addSecondFragment(mIProduct.getIntroduceUrl());

        mInvestDetailEarnings.setText(String.valueOf(mIProduct.getAnnualYield() - mIProduct.getAddYield()));
        if (mIProduct.getAddYield() > 0) {
            mInvestDetailEarningsAdd.setVisibility(View.VISIBLE);
            mInvestDetailEarningsAdd1.setVisibility(View.VISIBLE);
            mInvestDetailEarningsAdd2.setVisibility(View.VISIBLE);
            mInvestDetailEarningsAdd.setText(String.valueOf(mIProduct.getAddYield()));
        }
        mInvestDetailType.setText(mIProduct.getFinancialPeriod());
        long joinDate = ((long) mIProduct.getJoinDateUnix() * (long) 1000);
        long endDate = (long) mIProduct.getEndDateUnix() * (long) 1000;

        if (!mIProduct.getStatus().equals("PUBLISHING")) {
            investDetail.setBuyButton(false, "已满标");
        } else if (joinDate > listDataResult.getServicetime()) {
            investDetail.setBuyButton(false, "未开始");
        } else if (endDate < listDataResult.getServicetime() && mIProduct.getEndDateUnix() != 0) {
            investDetail.setBuyButton(false, "已结束");
        } else {
            investDetail.setBuyButton(true, "立即购买");
        }
        mInvestDetailLeftMoney.setText(String.valueOf(mIProduct.getRemainAmount() / 1000));
        mInvestDetailLeftMoney.append("元");

        mInvestDetailStartMoney.setText(String.valueOf(mIProduct.getMinInvestment() / 1000));
        mInvestDetailStartMoney.append("元");
        // 接口请求成功，显示收益
        // endMoney.setText(Tools.getDecimal(((double) investDetailUpModel.getMoney() / (double) 1000) * (double) (mIProduct.getAnnualYield() / (float) 100) / (double) 365 * (double) mIProduct.getFinancialPeriodDay()));
        // 接口请求成功，显示最小借款金额
        //input.setText(String.valueOf(mIProduct.getMinInvestment() / 1000));
        String incomeStr = "";
        switch (mIProduct.getIncomeMethod()) {
            case "0":
                incomeStr = "等额本息";
                break;
            case "1":
                incomeStr = "先息后本";
                break;
            case "2":
                incomeStr = "到期还本付息";
                break;
        }
        mInvestDetailHuankuan.setText(incomeStr);
        loopScanleView.setMaxValue((int) (mIProduct.getMaxInvestment() / 1000));
        loopScanleView.setCurrentValue((int) (mIProduct.getMinInvestment() / 1000));
        loopScanleView.setMinCurrentValue((int) (mIProduct.getMinInvestment() / 1000));
        loopScanleView.setMinUnitValue((int) (mIProduct.getIncreaseInvestment() / 1000));

        // 最大可投金额
        mTvMaxmoney.setText((int) (mIProduct.getMaxInvestment() / 1000) + "元");
        // 开始计息时间
        mStartTime.setText(Tools.getFullFormatDate2(listDataResult.getServicetime()));
        // 收益提示语
        mTvTipTime.setText("预计" + Tools.getFullFormatDate4((listDataResult.getServicetime()
                + (mIProduct.getFinancialPeriodDay() * 24 * 60 * 60 * 1000))) + "后可收益(元)");
        // 投资最大金额的提示
        input.setHint("最多可投" + (mIProduct.getMaxInvestment() / 1000) + "元");
    }
}
