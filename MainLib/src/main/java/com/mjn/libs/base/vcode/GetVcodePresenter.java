package com.mjn.libs.base.vcode;

import com.bing.lan.comm.api.HttpResult;
import com.mjn.libs.api.ResponseResult;
import com.mjn.libs.base.MainLibActivityPresenter;
import com.mjn.libs.comm.bean.ISmsCode;
import com.mjn.libs.cons.GetVCodeType;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.mjn.libs.cons.IConstants.TOTAL_WAITING_VERIFICATION_CODE_TIME_60S;
import static com.mjn.libs.cons.RequestActionCons.ACTION_CHECK_VCODE;
import static com.mjn.libs.cons.RequestActionCons.ACTION_GET_VCODE;
import static com.mjn.libs.cons.RequestActionCons.ACTION_GET_VCODE_FORGET;

/**
 * @author 蓝兵
 */
public abstract class GetVcodePresenter<
        T extends IGetVcodeContract.IGetVcodeView,
        V extends IGetVcodeContract.IGetVcodeModule>
        extends MainLibActivityPresenter<T, V>
        implements IGetVcodeContract.IGetVcodePresenter<T, V> {

    @Override
    public void getForgetVcode(String phone, GetVCodeType type) {
        showProgressDialog("正在获取验证码..");
        requestData(ACTION_GET_VCODE_FORGET, phone, type.getType());
    }

    @Override
    public void getVcode(String phone, GetVCodeType type) {
        showProgressDialog("正在获取验证码..");
        requestData(ACTION_GET_VCODE, phone, type.getType());
    }

    @Override
    public void checkVcode(String phone, GetVCodeType type, String code) {
        requestData(ACTION_CHECK_VCODE, phone, type.getType(), code);
    }

    public void handleVcode(int action, Object data) {
        switch (action) {
            case ACTION_GET_VCODE:
                ResponseResult<Object> httpResult = (ResponseResult<Object>) data;
                String errorCode = httpResult.getCode();
                if (ResponseResult.REQUEST_CODE_SUCCESS.equals(errorCode)) {
                    updateWaitingVerificationCodeTime();  //倒计时
                    //showToast(httpResult.getMessage());
                    mView.getVcodeSuccess(true);
                } else {
                    //showError("操作过于频繁,请稍后再试");
                    showError(httpResult.getMessage());
                    mView.getVcodeSuccess(false);
                }
                break;

            case ACTION_GET_VCODE_FORGET:
                ResponseResult<ISmsCode> httpResult1 = (ResponseResult<ISmsCode>) data;
                String errorCode1 = httpResult1.getCode();
                if (ResponseResult.REQUEST_CODE_SUCCESS.equals(errorCode1)) {
                    updateWaitingVerificationCodeTime();  //倒计时
                    //showToast(httpResult.getMessage());
                    mView.getVcodeSuccess(true);
                } else {
                    //showError("操作过于频繁,请稍后再试");
                    showError(httpResult1.getMessage());
                    mView.getVcodeSuccess(false);
                }
                break;

            case ACTION_CHECK_VCODE:
                HttpResult<Object> httpResult3 = (HttpResult<Object>) data;
                int errorCode3 = httpResult3.getErrorCode();
                if (errorCode3 == HttpResult.HTTP_CODE_SUCCESS) {
                    //取消倒计时
                    releaseTask(0);
                    mView.checkVcodeSuccess(true);
                } else {
                    showError(httpResult3.getMsg());
                    mView.checkVcodeSuccess(false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(int action, Throwable e) {
        super.onError(action, e);
        if (action == ACTION_GET_VCODE) {
            showError("获取失败");
            mView.getVcodeSuccess(false);
        } else if (action == ACTION_GET_VCODE_FORGET) {
            showError("获取失败");
            mView.getVcodeSuccess(false);
        } else if (action == ACTION_CHECK_VCODE) {
            mView.checkVcodeSuccess(false);
        }
    }

    @Override
    public void onCompleted(int action) {
        super.onCompleted(action);
    }

    private CompositeDisposable mComposite;

    @Override
    public void updateWaitingVerificationCodeTime() {
        Disposable subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        int countTime = TOTAL_WAITING_VERIFICATION_CODE_TIME_60S - aLong.intValue();
                        if (countTime >= 0 && mView != null) {
                            mView.updateWaitingVerificationCodeTime(countTime);
                        }
                    }
                });
        if (mComposite == null) {
            mComposite = new CompositeDisposable();
        }
        mComposite.add(subscribe);
    }

    @Override
    public void releaseTask(int code) {
        if (mView != null) {
            mView.updateWaitingVerificationCodeTime(code);
        }
        if (mComposite != null) {
            mComposite.clear();
            mComposite = null;
        }
    }

    @Override
    public void onDetachView() {
        super.onDetachView();
        releaseTask(0);
    }
}
