//package com.bing.lan.comm.rx;
//
//import com.jakewharton.rxrelay2.PublishRelay;
//import com.jakewharton.rxrelay2.Relay;
//
//import io.reactivex.Observable;
//
///**
// * Created by 520 on 2017/7/6.
// */
//// http://www.tuicool.com/articles/e6RJfaV
////    https://github.com/JakeWharton/RxRelay
//public class RelayRxBus  implements RxBus1{
//
//    private final Relay<Object> mBus;
//
//    private RelayRxBus() {
//        // toSerialized method made bus thread safe
//        mBus = PublishRelay.create().toSerialized();
//    }
//
//    public static RelayRxBus get() {
//        return Holder.BUS;
//    }
//
//    public void post(Object obj) {
//        mBus.onNext(obj);
//    }
//
//    public <T> Observable<T> toObservable(Class<T> tClass) {
//        return mBus.ofType(tClass);
//    }
//
//    public Observable<Object> toObservable() {
//        return mBus;
//    }
//
//    public boolean hasObservers() {
//        return mBus.hasObservers();
//    }
//
//    private static class Holder {
//
//        private static final RelayRxBus BUS = new RelayRxBus();
//    }
//}
