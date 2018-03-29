package com.mjn.user.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjn.libs.base.MainLibFragment;
import com.mjn.libs.utils.ToastAlone;
import com.mjn.user.R;

/**
 * @author 蓝兵
 */
public class UserFragment extends MainLibFragment<IUserContract.IUserPresenter>
        implements IUserContract.IUserView ,View.OnClickListener{

    // 用户手机
    TextView user_phone;
    TextView user_qiandao;
    // 资产总额
    TextView userMoney;
    // 累计收益
    TextView leijishouyi;
    // 可用余额
    TextView keyongMoney;
    TextView mWithdrawDeposit;
    TextView mRecharge;
    LinearLayout mInvestmentRecord;
    LinearLayout mReceivableCalendar;
    LinearLayout mMoneyFlowingWater;
    LinearLayout mRedPacket;
    LinearLayout mRateCoupon;
    LinearLayout mMyIntegral;
    LinearLayout mInviteFriends;
    RelativeLayout mRiskRating;
    RelativeLayout mAccountSetting;
    RelativeLayout mhelpCenter;
    RelativeLayout mPlatformProfile;


    public UserFragment() {

    }

    public static UserFragment newInstance(String title) {
        UserFragment fragment = new UserFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    protected IUserContract.IUserPresenter initPresenter() {
        UserPresenter presenter = new UserPresenter();
        presenter.setModule(new UserModule());
        presenter.onAttachView(this);
        return presenter;
    }

    // @Override
    // protected void startInject(FragmentComponent fragmentComponent) {
    //     //        fragmentComponent.inject(this);
    // }

    @Override
    protected void initViewAndData(Intent intent, Bundle arguments) {
        initView();

    }

    private void initView() {
        user_phone = mContentView.findViewById(R.id.user_phone);
        user_qiandao = mContentView.findViewById(R.id.user_qiandao);
        user_qiandao.setOnClickListener(this);
        userMoney = mContentView.findViewById(R.id.userMoney);
        keyongMoney = mContentView.findViewById(R.id.keyongMoney);
        leijishouyi = mContentView.findViewById(R.id.leijishouyi);
        mWithdrawDeposit = mContentView.findViewById(R.id.user_tixian);
        mWithdrawDeposit.setOnClickListener(this);
        mRecharge = mContentView.findViewById(R.id.user_chongzhi);
        mRecharge.setOnClickListener(this);
        mInvestmentRecord = mContentView.findViewById(R.id.ll_user_jiaoyi1);
        mInvestmentRecord.setOnClickListener(this);
        mReceivableCalendar = mContentView.findViewById(R.id.ll_user_rili1);
        mReceivableCalendar.setOnClickListener(this);
        mMoneyFlowingWater = mContentView.findViewById(R.id.ll_user_caifu1);
        mMoneyFlowingWater.setOnClickListener(this);
        mRedPacket = mContentView.findViewById(R.id.ll_user_hongbao1);
        mRedPacket.setOnClickListener(this);
        mRateCoupon = mContentView.findViewById(R.id.ll_user_quan1);
        mRateCoupon.setOnClickListener(this);
        mMyIntegral = mContentView.findViewById(R.id.ll_user_jifen1);
        mMyIntegral.setOnClickListener(this);
        mInviteFriends = mContentView.findViewById(R.id.ll_user_yaoqin1);
        mInviteFriends.setOnClickListener(this);
        mRiskRating = mContentView.findViewById(R.id.ll_risk_rating);
        mRiskRating.setOnClickListener(this);
        mAccountSetting = mContentView.findViewById(R.id.ll_user_accountSetting);
        mAccountSetting.setOnClickListener(this);
        mhelpCenter = mContentView.findViewById(R.id.ll_user_help1);
        mhelpCenter.setOnClickListener(this);
        mPlatformProfile = mContentView.findViewById(R.id.ll_user_platformProfile);
        mPlatformProfile.setOnClickListener(this);
    }

    @Override
    protected void readyStartPresenter() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        // 签到
        if(id == R.id.user_qiandao){
            ToastAlone.show("签到");
        }else if(id == R.id.user_tixian){
            // 提现
        }else if(id == R.id.user_chongzhi){
            // 充值
        }else if(id == R.id.ll_user_jiaoyi1){
            // 投资记录
        }else if(id == R.id.ll_user_rili1){
            // 回款日历
        }else if(id == R.id.ll_user_caifu1){
            // 资金流水
        }else if(id == R.id.ll_user_hongbao1){
            // 红包
        }else if(id == R.id.ll_user_quan1){
            // 加息券
        }else if(id == R.id.ll_user_jifen1){
            // 我的积分
        }else if(id == R.id.ll_user_yaoqin1){
            // 邀请好友
        }else if(id == R.id.ll_risk_rating){
            // 风险评级
        }else if(id == R.id.ll_user_accountSetting){
            // 账户设置
        }else if(id == R.id.ll_user_help1){
            // 帮助中心
        }else if(id == R.id.ll_user_platformProfile){
            // 平台简介
        }



    }
}
