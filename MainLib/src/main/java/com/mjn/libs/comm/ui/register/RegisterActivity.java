package com.mjn.libs.comm.ui.register;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bing.lan.comm.view.MyToolbar;
import com.mjn.libs.R;
import com.mjn.libs.base.vcode.GetVcodeActivity;
import com.mjn.libs.comm.bean.UserBean;
import com.mjn.libs.cons.GetVCodeType;
import com.mjn.libs.utils.AppUtil;
import com.mjn.libs.utils.Tools;

/**
 * @author 蓝兵
 */
public class RegisterActivity extends GetVcodeActivity<IRegisterContract.IRegisterPresenter>
        implements IRegisterContract.IRegisterView, View.OnClickListener {

    private com.bing.lan.comm.view.MyToolbar mToolbar;
    private android.widget.ScrollView mMyscroll;
    private android.widget.LinearLayout mLoginContainer;
    private android.widget.EditText mRegisterAccountEt;
    private android.widget.ImageView mIvDeletephone;
    private android.widget.TextView mRegisterAuthTv;
    private android.widget.EditText mRegisterAuthEt;
    private android.widget.ImageView mIvDeletecode;
    private android.widget.EditText mRegisterPasswordEt;
    private android.widget.ImageView mIvDeletepwd;
    private android.widget.EditText mRegisterInviteCode;
    private android.widget.TextView mRegisterLoginBtn;
    private android.widget.TextView mUseRuler;

    //MyToolbar mToolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected IRegisterContract.IRegisterPresenter initPresenter() {
        RegisterPresenter presenter = new RegisterPresenter();
        presenter.setModule(new RegisterModule());
        presenter.onAttachView(this);
        return presenter;
    }

    private void initView() {
        mToolbar = (MyToolbar) findViewById(R.id.toolbar);
        mMyscroll = (ScrollView) findViewById(R.id.myscroll);
        mLoginContainer = (LinearLayout) findViewById(R.id.login_container);
        mRegisterAccountEt = (EditText) findViewById(R.id.register_accountEt);
        mIvDeletephone = (ImageView) findViewById(R.id.iv_deletephone);
        mRegisterAuthTv = (TextView) findViewById(R.id.register_authTv);
        mRegisterAuthEt = (EditText) findViewById(R.id.register_authEt);
        mIvDeletecode = (ImageView) findViewById(R.id.iv_deletecode);
        mRegisterPasswordEt = (EditText) findViewById(R.id.register_passwordEt);
        mIvDeletepwd = (ImageView) findViewById(R.id.iv_deletepwd);
        mRegisterInviteCode = (EditText) findViewById(R.id.register_invite_code);
        mRegisterLoginBtn = (TextView) findViewById(R.id.register_loginBtn);
        mUseRuler = (TextView) findViewById(R.id.use_ruler);

        mRegisterAuthTv.setOnClickListener(this);
        mRegisterLoginBtn.setOnClickListener(this);

        mRegisterAccountEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mIvDeletephone.setVisibility(View.VISIBLE);
                    mIvDeletecode.setVisibility(View.GONE);
                    mIvDeletepwd.setVisibility(View.GONE);
                } else {
                    mIvDeletephone.setVisibility(View.GONE);
                    mIvDeletecode.setVisibility(View.GONE);
                    mIvDeletepwd.setVisibility(View.GONE);
                }
            }
        });

        mRegisterAuthEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mIvDeletecode.setVisibility(View.VISIBLE);
                    mIvDeletephone.setVisibility(View.GONE);
                    mIvDeletepwd.setVisibility(View.GONE);
                } else {
                    mIvDeletephone.setVisibility(View.GONE);
                    mIvDeletecode.setVisibility(View.GONE);
                    mIvDeletepwd.setVisibility(View.GONE);
                }
            }
        });

        mRegisterPasswordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mIvDeletepwd.setVisibility(View.VISIBLE);
                    mIvDeletephone.setVisibility(View.GONE);
                    mIvDeletecode.setVisibility(View.GONE);
                } else {
                    mIvDeletephone.setVisibility(View.GONE);
                    mIvDeletecode.setVisibility(View.GONE);
                    mIvDeletepwd.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initViewAndData(Intent intent) {
        initView();
        setToolBar(mToolbar, "注册", true, 0);
    }

    @Override
    protected void readyStartPresenter() {

    }

    @Override
    protected TextView getVcodeTimeTextView() {
        return mRegisterAuthTv;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.register_authTv) {
            getVcode();
        } else if (id == R.id.register_loginBtn) {
            checkRegister();
        }
    }

    private void checkRegister() {
        if (TextUtils.isEmpty(mRegisterAccountEt.getText().toString()) ||
                mRegisterAccountEt.getText().length() != 11) {
            showError("请输入正确手机号");
            return;
        }
        if (TextUtils.isEmpty(mRegisterAuthEt.getText().toString())) {
            showError("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(mRegisterPasswordEt.getText().toString())) {
            showError("请输入密码");
            return;
        }
        if (!AppUtil.checkPwd(mRegisterPasswordEt.getText().toString())) {
            showError("请输入6-12位字母数字组合密码");
            return;
        }
        if (!TextUtils.isEmpty(mRegisterInviteCode.getText().toString()) &&
                mRegisterInviteCode.getText().toString().length() != 9) {
            showError("请输入9位邀请码");
            return;
        }
        mPresenter.register(mRegisterAccountEt.getText().toString().trim(),
                Tools.getMd5Pwd(mRegisterPasswordEt.getText().toString().trim()),
                mRegisterAuthEt.getText().toString().trim(), "GOSJPQBLH"
        );
    }

    private void getVcode() {
        if (TextUtils.isEmpty(mRegisterAccountEt.getText().toString()) ||
                mRegisterAccountEt.getText().length() != 11) {
            showError("请输入正确手机号");
            return;
        }

        //autoFillVcode(mRegisterAuthEt);
        mPresenter.getVcode(
                mRegisterAccountEt.getText().toString(),
                GetVCodeType.REGISTER);
    }

    @Override
    public void registerSuccess(UserBean userBean) {
        showError("恭喜注册成功");
        log.i("registerSuccess(): " + userBean.toString());
    }
}
