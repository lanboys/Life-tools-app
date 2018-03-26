package com.bing.lan.comm.error;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.bing.lan.comm.utils.FileUtil;
import com.bing.lan.comm.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by 520 on 2017/6/19.
 */

public abstract class BaseErrorReportService extends IntentService {

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    public BaseErrorReportService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        getErrorReportFile();
    }

    /**
     * 获取 错误文件后缀
     *
     * @return 错误文件后缀
     */
    protected String getErrorFileSuffix() {
        return ".jzk";
        //return ".apk";
    }

    /**
     * 重写 方法 更改错误文件路径
     *
     * @return 错误文件路径
     */
    protected String getErrorFileDir() {

        // File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile();
        File file = getExternalFilesDir("log");
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return getCacheDir().getAbsolutePath();
        }
    }

    protected void getErrorReportFile() {

        ArrayList<File> files = new ArrayList<>();
        //便利所有文件
        FileUtil.listFilesBySuffix(getErrorFileDir(), getErrorFileSuffix(), files);
        log.i("getErrorReportFile(): 错误报告文件数：" + files.size());
        if (files.size() > 0) {
            // 找最新的文件
            File lastFileFromList = FileUtil.findLastFileFromList(files);
            uploadErrorFile(lastFileFromList);
        }
    }

    protected abstract void uploadErrorFile(File lastFileFromList);

    protected void deleteErrorReportFile(File file) {
        if (file != null) {
            log.i("deleteErrorReportFile(): 删除日志");
            FileUtil.deleteFileOrFolder(file);
        }
    }
}
