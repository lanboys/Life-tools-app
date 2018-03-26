package com.mjn.libs.base.vcode;

import android.widget.TextView;

import com.mjn.libs.R;
import com.mjn.libs.authCode.AuthCode;
import com.mjn.libs.authCode.CodeConfig;
import com.mjn.libs.base.MainLibActivity;

import java.util.List;

import static com.mjn.libs.cons.PermissionRequestCodeCons.REQUEST_CODE_PERMISSION_READ_SMS;

/**
 * @author 蓝兵
 */
public abstract class GetVcodeActivity<T extends IGetVcodeContract.IGetVcodePresenter>
        extends MainLibActivity<T>
        implements IGetVcodeContract.IGetVcodeView<T> {

    private TextView mVcodeTextView;
    private CodeConfig mConfig;

    //@Override
    //public void setVerificationStatus() {
    //
    //}

    protected abstract TextView getVcodeTimeTextView();

    @Override
    public void updateWaitingVerificationCodeTime(int time) {
        if (getVcodeTimeTextView() == null) {
            throw new RuntimeException("验证码控件未设置");
        }

        if (time > 0) {
            getVcodeTimeTextView().setClickable(false);
            getVcodeTimeTextView().setText(time + " S");
        } else if (time == 0) {
            getVcodeTimeTextView().setClickable(true);
            getVcodeTimeTextView().setText("重发验证码");
        } else if (time == -1) {
            getVcodeTimeTextView().setClickable(true);
            getVcodeTimeTextView().setText("获取验证码");
        }
    }

    @Override
    public void checkVcodeSuccess(boolean isSuccess) {

    }

    @Override
    public void getVcodeSuccess(boolean isSuccess) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AuthCode.getInstance().onDestroy();
    }

    ///**
    // * 简单处理了短信权限
    // */
    //private void handleReadSmsPermission() {
    //    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
    //            != PackageManager.PERMISSION_GRANTED) {
    //        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_PERMISSION_CODE);
    //    }
    //}

    //<uses-permission android:name="android.permission.READ_SMS"/>
    //<uses-permission android:name="android.permission.RECEIVE_SMS"/>

    public void autoFillVcode(TextView textView) {
        mVcodeTextView = textView;

        //AppUtil.postTaskSafeDelay(new Runnable() {
        //    @Override
        //    public void run() {
        requestPermissions(REQUEST_CODE_PERMISSION_READ_SMS, com.bing.lan.comm.R.array.read_sms_runtime_permissions);
        //    }
        //}, 1500);
    }

    @Override
    protected void requestPermissionSucceed(int requestCode, List<String> successPermissions) {
        super.requestPermissionSucceed(requestCode, successPermissions);

        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_SMS:
                initAuthCode();
                break;
        }
    }

    /*  权限请求失败时调用 */
    protected void requestPermissionFailed(int requestCode, List<String> failedPermissions) {
        showError("您阻止了app读取您的短信，您可以自己手动输入验证码");
    }

    public void initAuthCode() {
        // 设置验证码长度
        // 设置验证码发送号码前几位数字
        //.smsFrom(1690123456789) // 如果验证码发送号码固定，则可以设置验证码发送完整号码
        // 设置验证码短信开头文字
        // 设置验证码短信内容包含文字
        if (mConfig == null) {
            mConfig = new CodeConfig.Builder()
                    .codeLength(6) // 设置验证码长度
                    .smsFromStart(1069) // 设置验证码发送号码前几位数字
                    //.smsFrom(1690123456789) // 如果验证码发送号码固定，则可以设置验证码发送完整号码
                    .smsBodyStartWith("【xxx】") // 设置验证码短信开头文字
                    .smsBodyContains("您的验证码") // 设置验证码短信内容包含文字
                    .build();
        }

        if (mVcodeTextView != null) {
            AuthCode.getInstance().with(this).config(mConfig).into(mVcodeTextView);
        }
    }
}
