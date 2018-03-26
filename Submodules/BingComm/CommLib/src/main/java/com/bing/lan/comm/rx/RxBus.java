package com.bing.lan.comm.rx;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Author: yxhuang
 * Date: 2016/10/26
 * Email: yxhuang@gmail.com
 */

public class RxBus {

    private static volatile RxBus mInstance;

    private final Subject<Object> mBus;

    private RxBus() {
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }

        return mInstance;
    }

    /**
     * 发送消息
     *
     * @param object
     */
    public void post(@NonNull Object object) {
        mBus.onNext(object);
    }

    /**
     * 接收消息
     * 根据传递的 eventType 类型返回特定类型(eventType)的被观察者
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }
}
