package com.bing.lan.comm.rx;

import com.bing.lan.comm.utils.LogUtil;

/**
 * @author 蓝兵
 * @time 2017/4/17  0:04
 */
public class MvpObserver<T> extends BaseObserver<T> {

    // protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    private LogUtil log;
    private OnDataChangerListener listener;
    private String mDescription;
    private int action;

    public MvpObserver(Builder builder) {
        this.action = builder.action;
        this.listener = builder.listener;
        this.mDescription = builder.mDescription;
        this.log = builder.log;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void onNext(T bean) {
        if (bean == null) {
            throw new NullPointerException("onNext(T bean)返回的对象 bean == null");
        }
        if (log != null) {
            log.i("onNext():加载 " + mDescription + " 成功: " + bean.toString());
        }
        if (listener != null && !listener.isDetachView()) {
            listener.onSuccess(action, bean);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (log != null) {
            log.e("onError():  加载 " + mDescription + " 失败: ", e);
        }
        if (listener != null && !listener.isDetachView()) {
            listener.onError(action, e);
        }
    }

    @Override
    public void onComplete() {
        if (log != null) {
            log.i("onCompleted(): 加载 " + mDescription + " 完成");
        }
        if (listener != null && !listener.isDetachView()) {
            listener.onCompleted(action);
        }
    }

    //@Override
    //public void onCompleted() {
    //    if (log != null) {
    //        log.i("onCompleted(): 加载 " + mDescription + " 完成");
    //    }
    //    if (listener != null) {
    //        listener.onCompleted(action);
    //    }
    //}

    public static final class Builder {

        OnDataChangerListener listener;
        String mDescription;
        int action;
        LogUtil log;

        private Builder() {

        }

        public Builder action(int action) {
            this.action = action;
            return this;
        }

        public Builder dataChangeListener(OnDataChangerListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder description(String description) {
            this.mDescription = description;
            return this;
        }

        public Builder log(LogUtil log) {
            this.log = log;
            return this;
        }

        public <M> MvpObserver<M> build() {
            return new MvpObserver<M>(this);
        }
    }
}