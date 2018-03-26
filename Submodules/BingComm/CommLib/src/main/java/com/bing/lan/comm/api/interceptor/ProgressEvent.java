package com.bing.lan.comm.api.interceptor;

import com.bing.lan.comm.error.BusEvent;

/**
 * @author 蓝兵
 */
public class ProgressEvent implements BusEvent {

    private int progressId;
    private long currentBytes;
    private long contentLength;

    public ProgressEvent(int progressId, long currentBytes, long contentLength) {
        this.progressId = progressId;
        this.currentBytes = currentBytes;
        this.contentLength = contentLength;
    }

    public float getPercentProgress() {
        float v = 0;
        try {
            v = currentBytes * 1.0f / contentLength;
        } catch (Exception e) {
        }
        return v;
    }

    @Override
    public String toString() {
        return "ProgressEvent{" +
                "progressId=" + progressId +
                ", currentBytes=" + currentBytes +
                ", contentLength=" + contentLength +
                '}';
    }

    public int getProgressId() {
        return progressId;
    }

    public void setProgressId(int progressId) {
        this.progressId = progressId;
    }

    public long getCurrentBytes() {
        return currentBytes;
    }

    public void setCurrentBytes(long currentBytes) {
        this.currentBytes = currentBytes;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }
}
