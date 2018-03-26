package com.bing.lan.comm.api.interceptor;

import android.text.TextUtils;

import com.bing.lan.comm.api.progress.ProgressListener;
import com.bing.lan.comm.api.progress.ProgressRequestBody;
import com.bing.lan.comm.api.progress.ProgressResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 520 on 2017/6/28.
 */

public abstract class BaseProgressInterceptor implements Interceptor, ProgressListener {

    // protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Override
    public Response intercept(Chain chain) throws IOException {
        //拦截
        Request request = chain.request();
        String progressId = request.header("ProgressId");
        // log.i("intercept(): progressId: " + progressId);
        if (!TextUtils.isEmpty(progressId)) {
            // post 请求 添加进度 整体进度监听
            if ("POST".equals(request.method())) {
                request = request.newBuilder()
                        .post(ProgressRequestBody.create(request.body(), this, Integer.parseInt(progressId)))
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            // 添加进度监听
            return originalResponse.newBuilder()
                    .body(ProgressResponseBody.create(originalResponse.body(), this, Integer.parseInt(progressId)))
                    .build();
        } else {
            return chain.proceed(request);
        }
    }

    //上传文件 单文件进度 在此回调
    @Override
    public abstract void onRequestSingleProgress(int progressId, long currentBytes, long contentLength, boolean done);

    @Override
    public abstract void onRequestProgress(int progressId, long currentBytes, long contentLength, boolean done);

    @Override
    public abstract void onResponseProgress(int progressId, long currentBytes, long contentLength, boolean done);
}
