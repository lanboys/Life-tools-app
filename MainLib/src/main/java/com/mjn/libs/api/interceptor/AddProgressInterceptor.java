package com.mjn.libs.api.interceptor;

import com.bing.lan.comm.api.interceptor.BaseProgressInterceptor;
import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.utils.LogUtil;

/**
 * @author 蓝兵
 */
public class AddProgressInterceptor extends BaseProgressInterceptor {

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Override
    public void onRequestSingleProgress(int progressId, long currentBytes, long contentLength, boolean done) {
        //上传文件 单文件进度 在此回调
    }

    @Override
    public void onRequestProgress(int progressId, long currentBytes, long contentLength, boolean done) {
        log.i("onRequestProgress(): <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.i("onRequestProgress(): <<<<<<  progressId: " + progressId + "  currentBytes: " + currentBytes + "  contentLength: " + contentLength + "  done: " + done);
        log.i("onRequestProgress(): <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        //RxBus.getInstance().post(new ProgressEvent(progressId, currentBytes, contentLength));

        AppUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                //JzkSellerApplication.sBus.post(new ProgressEvent(progressId, currentBytes, contentLength));
            }
        });
    }

    @Override
    public void onResponseProgress(int progressId, long currentBytes, long contentLength, boolean done) {
        log.i("onResponseProgress(): >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.i("onResponseProgress(): >>>>>>  progressId: " + progressId + "  currentBytes: " + currentBytes + "  contentLength: " + contentLength + "  done: " + done);
        log.i("onResponseProgress(): >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        //RxBus.getInstance().post(new ProgressEvent(progressId, currentBytes, contentLength));

        AppUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                //JzkSellerApplication.sBus.post(new ProgressEvent(progressId, currentBytes, contentLength));
            }
        });
    }
}
