package com.bing.lan.comm.load;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.utils.FileUtil;
import com.bing.lan.comm.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * @author 蓝兵  只下载图片到SD卡
 *         获取 Glide 的默认缓存文件 再移动到SD卡
 */

public class DownloadImageTask extends AsyncTask<Void, Void, File> {

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    private final Context context;
    private final DownloadImageCallBack fileDownloadCallBack;
    private final String imageUrl;
    private File desFile;

    public DownloadImageTask(Context context, String imageUrl, File desFile,
            DownloadImageCallBack fileDownloadCallBack) {

        this.context = context;
        this.imageUrl = imageUrl;
        this.desFile = desFile;
        this.fileDownloadCallBack = fileDownloadCallBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (desFile == null) {
            desFile = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera",//有的手机如vivo,相机相册不是在此文件夹
                    imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
        }
    }

    @Override
    protected File doInBackground(Void... voids) {
        try {
            //此file就是对应文件的缓存路径
            final File file = Glide.with(context)
                    .load(imageUrl)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

            //此path就是对应文件的缓存路径
            String path = file.getAbsolutePath();
            Log.e("GlideDownloadImagePath", path);

            if (FileUtil.moveFile(file, desFile, false)) {
                return desFile;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.e("doInBackground():  " + e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result) {
        if (fileDownloadCallBack != null) {
            log.i("onDownloadImageCallBack(): 图片保存地址: " + result);
            fileDownloadCallBack.onDownloadImageCallBack(result != null, imageUrl, result);
        }
        // 最后通知图库更新  公有文件夹的图片才能被图库检索
        // 不通知,即使放在默认照片文件夹也检索不到
        AppUtil.getAppContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(desFile)));
    }
}


