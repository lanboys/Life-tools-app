package com.bing.lan.comm.api.progress;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bing.lan.comm.utils.LogUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * @author 蓝兵
 */
public class ProgressSingleRequestBody extends RequestBody {

    protected static final LogUtil log = LogUtil.getLogUtil(ProgressSingleRequestBody.class, LogUtil.LOG_VERBOSE);
    private boolean isFirst = true;
    private MediaType contentType;
    private File file;
    private int progressId;
    private ProgressListener progressListener;

    private ProgressSingleRequestBody(final @Nullable MediaType contentType, final File file,
            ProgressListener progressListener, int progressId) {
        this.contentType = contentType;
        this.file = file;
        this.progressListener = progressListener;
        this.progressId = progressId;
    }

    public static RequestBody create(final @Nullable MediaType contentType, final File file,
            ProgressListener progressListener, int progressId) {
        if (file == null)
            throw new NullPointerException("content == null");
        return new ProgressSingleRequestBody(contentType, file, progressListener, progressId);
    }

    @Override
    public
    @Nullable
    MediaType contentType() {
        return contentType;
    }

    @Override
    public long contentLength() {
        return file.length();
    }

    /**
     * 会调用两次 为什么? 所以做一次判断
     */
    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        Source source = null;
        try {
            source = Okio.source(file);
            log.i("writeTo(): isFirst: " + isFirst);
            if (isFirst) {
                sink.writeAll(wrapSource(source));
                isFirst = false;
            } else {//source-----读取到------>sink
                sink.writeAll(source);
            }
            log.i("writeTo(): file: " + file);
        } finally {
            Util.closeQuietly(source);
        }
    }

    /**
     * 读取，回调进度接口
     *
     * @param source Source
     * @return Source
     */
    private Source wrapSource(Source source) {

        return new ForwardingSource(source) {
            //当前读取字节数
            long totalBytesRead = 0L;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                //增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //回调，如果contentLength()不知道长度，会返回-1
                if (progressListener != null) {
                    progressListener.onRequestSingleProgress(progressId, totalBytesRead, contentLength(), bytesRead == -1);
                }
                return bytesRead;
            }
        };
    }
}
