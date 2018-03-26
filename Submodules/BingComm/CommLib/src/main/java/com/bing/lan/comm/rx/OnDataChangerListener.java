package com.bing.lan.comm.rx;

/**
 * Created by 520 on 2017/7/6.
 */

public interface OnDataChangerListener {

    @SuppressWarnings("unchecked")
    void onSuccess(int action, Object data);

    void onLoading(int action);

    void onError(int action, Throwable e);

    void onCompleted(int action);

    void onNetError(int action, String tip);

    boolean isDetachView();
}
