package com.mjn.libs.comm.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bing.lan.comm.view.MyToolbar;
import com.mjn.libs.R;
import com.mjn.libs.base.vcode.GetVcodeActivity;
import com.mjn.libs.comm.ui.register.RegisterActivity;
import com.mjn.libs.utils.AppUtil1;
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

        mLoginRegistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisterActivity.class,false,true);
            }
        });

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

        }
    }

    private void checkLogin() {
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
                mLoginAccountEt.getText().toString(),
                Tools.getMd5Pwd(mLoginPasswordEt.getText().toString())
        );
    }

    @Override
    protected TextView getVcodeTimeTextView() {
        return null;
    }
}
