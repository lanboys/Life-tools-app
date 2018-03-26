package com.bing.lan.comm.mvp;

import android.os.NetworkOnMainThreadException;

import com.bing.lan.comm.app.AppConfig;
import com.bing.lan.comm.utils.LogUtil;
import com.ganxin.library.LoadDataLayout;
import com.google.gson.JsonParseException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * @author 蓝兵
 * @time 2017/1/12  18:31
 */
public abstract class BasePresenter<
        T extends IBaseContract.IBaseView,
        M extends IBaseContract.IBaseModule>
        implements IBaseContract.IBasePresenter<T, M> {

    protected LogUtil log = LogUtil.getLogUtil(getClass(), 1);

    // 会不会内存泄露
    protected M mModule;
    protected T mView;

    @Override
    public boolean isDetachView() {//null 不能进行回调
        return mView == null;
    }

    @Override
    public void setModule(M module) {
        mModule = module;
    }

    @Override
    public void showError(CharSequence msg) {
        if (mView == null) {
            return;
        }
        try {
            mView.showError(msg);
        } catch (Exception e) {
            log.e("showError():  " + e.getLocalizedMessage());
        }
    }

    @Override
    public void showToast(CharSequence msg) {
        if (mView == null) {
            return;
        }
        try {
            mView.showToast(msg);
        } catch (Exception e) {
            log.e("showToast():  " + e.getLocalizedMessage());
        }
    }

    @Override
    public void showInfo(CharSequence msg) {
        if (mView == null) {
            return;
        }
        try {
            mView.showInfo(msg);
        } catch (Exception e) {
            log.e("showInfo():  " + e.getLocalizedMessage());
        }
    }

    @Override
    public void showTip(CharSequence msg) {
        if (mView == null) {
            return;
        }
        try {
            mView.showTip(msg);
        } catch (Exception e) {
            log.e("showTip():  " + e.getLocalizedMessage());
        }
    }

    @Override
    public void showProgressDialog(CharSequence msg) {
        if (mView == null) {
            return;
        }
        try {
            mView.showProgressDialog(msg);
        } catch (Exception e) {
            log.e("showProgressDialog():  " + e.getLocalizedMessage());
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mView == null) {
            return;
        }
        try {
            mView.dismissProgressDialog();
        } catch (Exception e) {
            log.e("dismissProgressDialog():  " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onAttachView(T view) {
        mView = view;
    }

    @Override
    public void onDetachView() {
        if (mModule != null) {
            mModule.releaseTask();
        }
        mView = null;
    }

    @Override
    public void requestData(int action, Object... parameter) {
        if (mModule != null) {
            mModule.requestData(action, this, parameter);
        }
    }

    @Override
    public void onLoading(int action) {
        log.i("onLoading(): 有相同任务正在执行,请稍后再试;  action: " + action);
    }

    @Override
    public void onError(int action, Throwable e) {
        if (AppConfig.LOG_DEBUG) {// 测试环境显示
            onException(action, e);
        }
        if (mView != null) {
            mView.setLoadDataLayoutStatus(LoadDataLayout.ERROR);
        }
        if (mModule != null) {
            mModule.refreshTask(action);
        }
        finishAction(action);
    }

    /**
     * 请求异常
     */
    private void onException(int action, Throwable e) {
        if (e == null || mView == null) {
            return;
        }
        if (e instanceof MvpHttpException) {//Null is not a valid element
            showError("自定义Http请求异常: " + e.getMessage());
        } else if (e instanceof NullPointerException) {//Null is not a valid element
            showError("空指针 NullPointerException");
        } else if (e instanceof NetworkOnMainThreadException) {
            showError("主线程网络请求异常..");
        } else if (e instanceof java.net.SocketTimeoutException) {//failed to connect to /192.168.2.186 (port 8888) after 15000ms
            showError("网络超时,请检查网络或代理..");
        } else if (e instanceof ConnectException) {
            showError("网络连接异常,请检查网络或代理..");//添加了代理会抛这个异常
        } else if (e instanceof HttpException) {
            showError("服务器异常..");//服务器500 抛这个异常
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            showError("I/O中断异常..");
        } else if (e instanceof UnknownHostException) {   //  未知域名异常
            showError("未知域名异常..");
        } else if (e instanceof javax.net.ssl.SSLHandshakeException
                || e instanceof java.security.cert.CertPathValidatorException) {   //  SSL证书异常
            showError("SSL证书异常..");
        } else if (e instanceof java.net.ProtocolException
                || e instanceof java.io.IOException) {   //  I/O流意外结束
            showError("I/O异常或流意外结束..");
        } else if (e instanceof JsonParseException
                || e instanceof com.alibaba.fastjson.JSONException
                || e instanceof org.json.JSONException
                || e instanceof ParseException) {   //  解析错误
            showError("json解析异常..");
        } else {
            //showError("未知错误..");
            showError(e.getMessage());
        }
    }

    @Override
    public void onCompleted(int action) {
        if (mModule != null) {
            mModule.refreshTask(action);
        }
        finishAction(action);
    }

    @Override
    public void onNetError(int action, String tip) {
        if (mView != null) {
            mView.setLoadDataLayoutStatus(LoadDataLayout.NO_NETWORK);
            showInfo(tip);
        }
        finishAction(action);

        onCompleted(action);
    }

    protected void finishAction(int action) {
        switch (action) {
            default:
                if (mView != null) {
                    dismissProgressDialog();
                    mView.closeRefreshing();
                }
                break;
        }
    }

    public static class MvpHttpException extends RuntimeException {

        public MvpHttpException(String message) {
            super(message);
        }
    }
}
