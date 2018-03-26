package com.bing.lan.comm.rx;

import com.bing.lan.comm.utils.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<T> implements Observer<T> {

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    private Disposable mDisposable;

    public Disposable getDisposable() {
        return mDisposable;
    }

    public boolean isUnSubscribed() {
        if (mDisposable != null) {
            return mDisposable.isDisposed();
        }
        return false;//说明disposable 还没有被赋值,所以任务还未开始,即没有解除
    }

    public boolean unSubscribe() {
        if (mDisposable == null) {
            log.i("unSubscribe(): mDisposable 为空 ");
            return false;
        }
        if (!mDisposable.isDisposed()) {
            log.i("unSubscribe(): 正在取消订阅 ");
            mDisposable.dispose();
        }
        return mDisposable.isDisposed();
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        log.i("onSubscribe(): 可以取消订阅了");
    }

    //@Override
    //public void onComplete() {
    //
    //}
}
