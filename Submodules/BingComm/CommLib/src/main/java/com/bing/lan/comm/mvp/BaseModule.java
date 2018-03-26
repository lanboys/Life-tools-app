package com.bing.lan.comm.mvp;

import android.text.TextUtils;

import com.bing.lan.comm.api.progress.ProgressListener;
import com.bing.lan.comm.api.progress.ProgressSingleRequestBody;
import com.bing.lan.comm.app.BaseApplication;
import com.bing.lan.comm.rx.BaseObserver;
import com.bing.lan.comm.rx.MvpObserver;
import com.bing.lan.comm.rx.OnDataChangerListener;
import com.bing.lan.comm.utils.LogUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author 蓝兵
 */
public abstract class BaseModule implements IBaseContract.IBaseModule, OnDataChangerListener {

    // @Inject
    // protected LogUtil log;
    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    protected Map<String, MvpObserver> mSubscriptions = new HashMap<>();
    //protected Map<String, Subscription> mSubscriptions = new HashMap<>();
    protected OnDataChangerListener presenter;

    public BaseModule() {
        // DaggerDiComponent.builder()
        //         .diModule(new DiModule(getClass()))
        //         .build()
        //         .inject(this);
    }

    @Override
    public void releaseTask() {
        if (mSubscriptions != null && mSubscriptions.size() > 0) {
            for (MvpObserver subscription : mSubscriptions.values()) {
                releaseSubscribe(subscription);
            }
            mSubscriptions.clear();
        }
    }

    private void releaseSubscribe(BaseObserver subscribe) {
        if (subscribe != null && !subscribe.isUnSubscribed()) {
            subscribe.unSubscribe();
        }
    }

    // @Override
    // public void loadImage(Object path, ImageView imageView) {
    //
    //     // Glide可以加载 path 为object的路径,比图本地资源文件  R.mipmap.ic_launcher
    //
    //     // Glide.with(AppUtil.getAppContext())
    //     //         .load(path)
    //     //         .crossFade()
    //     //         .into(imageView);
    //
    //     // ImagePicassoUtil.loadImage(imageView, (String) path);
    //     // ImagePicassoUtil.loadBigImage(imageView, (String) path);
    //
    //     ImageLoader.getInstance().loadBigImage(imageView, (String) path);
    // }

    /**
     * 任务完成或者发生错误的时候调用
     *
     * @param action
     */
    @Override
    public void refreshTask(int action) {
        mSubscriptions.remove(String.valueOf(action));
    }

    @Override
    public void requestData(int action, OnDataChangerListener listener, Object... parameter) {

        if (!BaseApplication.netWorkStatus.isNetworkAvailable) {
            listener.onNetError(action, BaseApplication.netWorkStatus.netWorkTip);
            return;
        }

        MvpObserver subscribe = mSubscriptions.get(String.valueOf(action));
        if (subscribe != null && !subscribe.isUnSubscribed()) {
            //任务正在进行中
            if (listener != null) {
                //log.i("requestData(): 有相同任务正在执行,请稍后再试;  action: " + action);
                listener.onLoading(action);
            }
        } else {
            //没有任务进行
            loadData(action, listener, parameter);
        }
    }

    protected abstract void loadData(int action, OnDataChangerListener listener, Object[] parameter);

    protected <T> MvpObserver<T> subscribe(Observable<T> observable,
            //protected <T> void subscribe(Observable<T> observable,
            int action,
            OnDataChangerListener listener,
            String onNextString) {

        MvpObserver<T> observer = MvpObserver.newBuilder()
                .action(action)
                .log(log)
                .dataChangeListener(wrapperOnDataChangerListener(listener))
                .description(onNextString)
                .build();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        mSubscriptions.put(String.valueOf(action), observer);
        return observer;
    }

    @Override
    public OnDataChangerListener wrapperOnDataChangerListener(OnDataChangerListener presenter) {
        this.presenter = presenter;
        return this;
    }

    @Override
    public void onSuccess(int action, Object data) {
        if (presenter != null) {
            presenter.onSuccess(action, data);
        }
    }

    @Override
    public void onLoading(int action) {
        if (presenter != null) {
            presenter.onLoading(action);
        }
    }

    @Override
    public void onError(int action, Throwable e) {
        if (presenter != null) {
            presenter.onError(action, e);
        }
    }

    @Override
    public void onCompleted(int action) {
        if (presenter != null) {
            presenter.onCompleted(action);
        }
    }

    @Override
    public void onNetError(int action, String tip) {
        if (presenter != null) {
            presenter.onNetError(action, tip);
        }
    }

    @Override
    public boolean isDetachView() {
        return presenter != null && presenter.isDetachView();
    }

    protected MultipartBody.Part createMultipartBodyPart(String name, File file) {
        if (file != null) {
            //text/plain
            //text/html
            //application/x-www-form-urlencoded
            //multipart/form-data
            return MultipartBody.Part.createFormData(name, file.getName(),
                    createMultipartRequestBody(file));
            //return MultipartBody.Part.createFormData("Upload[file]", file.getName(), requestBody);
        }
        return null;
    }

    protected RequestBody createMultipartRequestBody(File file) {
        if (file != null) {
            return RequestBody.create(MediaType.parse("multipart/form-data"), file);
        }
        return null;
    }

    protected RequestBody createMultipartRequestBody(String value) {
        if (value != null) {
            return RequestBody.create(null, value);
        }
        return null;
    }

    protected MultipartBody.Part createProgressMultipartBodyPart(String name, File file, ProgressListener listener, int progressId) {
        if (file != null) {
            return MultipartBody.Part.createFormData(name, file.getName(),
                    createProgressMultipartRequestBody(file, listener, progressId));
        }
        return null;
    }

    protected RequestBody createProgressMultipartRequestBody(File file, ProgressListener listener, int progressId) {
        if (file != null) {
            return ProgressSingleRequestBody.create(MediaType.parse("multipart/form-data"), file, listener, progressId);
        }
        return null;
    }

    public void checkNotEmptyAdd(Map<String, String> map, String value, String name) {
        if (!TextUtils.isEmpty(value)) {
            map.put(name, value);
        }
    }

    public void checkNotNullAdd(Map<String, String> map, String value, String name) {
        if (null != value) {
            map.put(name, value);
        }
    }
}
