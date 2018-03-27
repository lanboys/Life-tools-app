package com.mjn.libs.comm.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bing.lan.comm.utils.SoftInputUtil;
import com.bing.lan.comm.view.MyToolbar;
import com.mjn.libs.R;
import com.mjn.libs.base.vcode.GetVcodeActivity;
import com.mjn.libs.comm.bean.IUser;
import com.mjn.libs.comm.ui.register.RegisterActivity;
import com.mjn.libs.cons.IntentParamsKeyCons;
import com.mjn.libs.cons.SP_Constant;
import com.mjn.libs.db.DataSaveManager;
import com.mjn.libs.utils.AppSpDataUtil;
import com.mjn.libs.utils.AppUtil1;
import com.mjn.libs.utils.SPUtil;
import com.mjn.libs.utils.Tools;

/**
 * @author 蓝兵
 */
public class LoginActivity extends GetVcodeActivity<ILoginContract.ILoginPresenter>
        implements ILoginContract.ILoginView, View.OnClickListener {

    ////@BindView(R.id.toolbar)
    ////@BindView(R2.id.toolbar)
    //MyToolbar mToolbar;
    private android.widget.LinearLayout mLoginContainer;
    private android.widget.ImageView mImageView3;
    private android.widget.EditText mLoginAccountEt;
    private android.widget.ImageView mIvDeletephone;
    private android.widget.LinearLayout mLinearLayout;
    private android.widget.ImageView mImageView4;
    private android.widget.EditText mLoginPasswordEt;
    private android.widget.ImageView mIvDeletepwd;
    private android.widget.TextView mLoginLoginBtn;
    private android.widget.TextView mLoginRegistBtn;
    private android.widget.TextView mLoginFindPwdBtn;
    private MyToolbar mToolbar;

    /**
     * 入口类型(1首页 2标的 详情进入)
     */
    private int enterType = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected ILoginContract.ILoginPresenter initPresenter() {
        LoginPresenter presenter = new LoginPresenter();
        presenter.setModule(new LoginModule());
        presenter.onAttachView(this);
        return presenter;
    }

    private void initView() {
        mToolbar = (MyToolbar) findViewById(R.id.toolbar);
        mLoginContainer = (LinearLayout) findViewById(R.id.login_container);
        mImageView3 = (ImageView) findViewById(R.id.imageView3);
        mLoginAccountEt = (EditText) findViewById(R.id.login_accountEt);
        mIvDeletephone = (ImageView) findViewById(R.id.iv_deletephone);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mImageView4 = (ImageView) findViewById(R.id.imageView4);
        mLoginPasswordEt = (EditText) findViewById(R.id.login_passwordEt);
        mIvDeletepwd = (ImageView) findViewById(R.id.iv_deletepwd);
        mLoginLoginBtn = (TextView) findViewById(R.id.login_loginBtn);
        mLoginRegistBtn = (TextView) findViewById(R.id.login_registBtn);
        mLoginFindPwdBtn = (TextView) findViewById(R.id.login_findPwdBtn);

        mLoginLoginBtn.setOnClickListener(this);
        mLoginRegistBtn.setOnClickListener(this);

        mLoginAccountEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mIvDeletephone.setVisibility(View.VISIBLE);
                    mIvDeletepwd.setVisibility(View.GONE);
                } else {
                    mIvDeletephone.setVisibility(View.GONE);
                    mIvDeletepwd.setVisibility(View.GONE);
                }
            }
        });

        mLoginPasswordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mIvDeletepwd.setVisibility(View.VISIBLE);
                    mIvDeletephone.setVisibility(View.GONE);
                } else {
                    mIvDeletepwd.setVisibility(View.GONE);
                    mIvDeletephone.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initViewAndData(Intent intent) {
        initView();
        setToolBar(mToolbar, "登录", true, R.drawable.icon_invite_friend_close);
        if (intent != null) {
            enterType = intent.getIntExtra(IntentParamsKeyCons.INTENT_TO_LOGIN_ENTER_TYPE, 0);
        }
    }

    @Override
    protected void readyStartPresenter() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login_loginBtn) {
            checkLogin();
        } else if (id == R.id.login_registBtn) {
            startActivity(RegisterActivity.class, false, true);
        }
    }

    private void checkLogin() {
        SoftInputUtil.closeSoftInput(this);
        if (TextUtils.isEmpty(mLoginAccountEt.getText().toString())) {
            showError("请填写账号");
            return;
        }

        if (TextUtils.isEmpty(mLoginPasswordEt.getText().toString())) {
            showError("请填写密码");
            return;
        }
        if (!AppUtil1.checkPwd(mLoginPasswordEt.getText().toString())) {
            showError("请输入6-12位字母数字组合密码");
            return;
        }
        mPresenter.login(
                mLoginAccountEt.getText().toString().trim(),
                Tools.getMd5Pwd(mLoginPasswordEt.getText().toString().trim())
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        String saveAccount = DataSaveManager.getInstance().read("InputAccount");
        if (saveAccount == null) {
            saveAccount = "";
        }
        if (mLoginAccountEt != null) {
            mLoginAccountEt.setText(saveAccount);
        }
    }

    @Override
    protected TextView getVcodeTimeTextView() {
        return null;
    }

    @Override
    public void onLoginSuccess(IUser iUser) {
        // json数据保存
        AppSpDataUtil.getInstance().saveUserBean(iUser);
        SPUtil.getInstance().putString("token", iUser.getToken());
        // 存储手机号码用于显示到顶部标题
        SPUtil.getInstance().putString("phone", mLoginAccountEt.getText().toString().trim());
        // 登录成功后，进行IM登录
        //UdeskUtil.userLogin(getActivity(), AppSpDataUtil.getInstance().getPhone(), AppSpDataUtil.getInstance().getUserBean().getRealName(), String.valueOf(AppSpDataUtil.getInstance().getUserBean().getUserId()));

        // TODO: 2018/3/27 im 神策统计

        // 神策统计，未登录用匿名ID登录成功后传递用户id
        //SensorsAnalyticsUtil.setTrackID(AppSpDataUtil.getInstance().getUserBean().getUserId() + "");
        //SensorsAnalyticsUtil.setTrack(AppConfig.context, "LoginSuccess");
        //UmengPushUtils.setAlias(String.valueOf(AppSpDataUtil.getInstance().getUserBean().getUserId()));
        // 分享跳转
        if (enterType == 1) {
            if (AppSpDataUtil.getInstance().getUserBean() != null) {
                Bundle bundle = new Bundle();
                bundle.putString("url", AppSpDataUtil.getInstance().getUserBean().getInvitedFriendsUrl()
                        + "?userId=" + AppSpDataUtil.getInstance().getUserBean().getUserId());

                toHtml5Pager(bundle);
                log.i("onLoginSuccess(): " + AppSpDataUtil.getInstance().getUserBean().getInvitedFriendsUrl()
                        + "?userId=" + AppSpDataUtil.getInstance().getUserBean().getUserId());
            }
        } else {
            // 登录成功，手势密码不为空
            if (!TextUtils.isEmpty(SPUtil.getInstance().getString(SP_Constant.PATTERN_INFO))) {
                // 如果登录手机号码与前用户不相同,清除上一个手势密码，重新设置手势密码
                if (!AppSpDataUtil.getInstance().getPhone().equals(mLoginAccountEt.getText().toString().trim())) {
                    SPUtil.getInstance().putString(SP_Constant.PATTERN_INFO, "");

                    // TODO: 2018/3/27 去设置手势密码
                    finish();
                    showError("去设置手势密码");
                } else {
                    finish();
                }
            } else {
                // TODO: 2018/3/27 去设置手势密码
                finish();
                showError("手势密码为空，直接设置手势密码");
            }
        }
    }
}
