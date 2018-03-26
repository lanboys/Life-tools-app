package com.bing.lan.comm.load;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.utils.BitmapUtil;
import com.bing.lan.comm.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * @author 蓝兵
 *         //http://blog.csdn.net/w372426096/article/details/52472984
 *         //http://www.jb51.net/article/109816.htm
 *         <p>
 *         获取 Glide 的默认缓存文件 再设置到ImageView中
 *         <p>
 *         http://blog.csdn.net/liulong_/article/details/53678842
 *         <p>
 *         path: /data/user/0/xxx包名xxx/cache/image_manager_disk_cache/9870ab25533b4287065712cd07959148c5a8757a8c73fa2ed94ebb6d3a755221.0
 */

public class GetImageCacheAsyncTask extends AsyncTask<Void, Void, BitmapUtil.BitmapResult> {

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    private final Context context;
    private final GetImageCacheCallBack fileDownloadCallBack;
    private final String imageUrl;
    private final ImageView imageView;
    private int reqWidth;
    private int reqHeight;
    private int targetAngle;

    public GetImageCacheAsyncTask(Context context, ImageView imageView, String imageUrl,
            GetImageCacheCallBack fileDownloadCallBack, int reqWidth, int reqHeight, int targetAngle) {

        this.context = context;
        this.imageUrl = imageUrl;
        this.imageView = imageView;
        this.fileDownloadCallBack = fileDownloadCallBack;
        this.reqWidth = reqWidth;
        this.reqHeight = reqHeight;
        this.targetAngle = targetAngle;
    }

    //public GetImageCacheAsyncTask(Context context, ImageView imageView, String imageUrl,
    //        DownloadImageCallBack fileDownloadCallBack, int reqWidth, int reqHeight) {
    //
    //    this(context, imageView, imageUrl,
    //            fileDownloadCallBack, reqWidth, reqHeight, 0);
    //
    //    //this.context = context;
    //    //this.imageUrl = imageUrl;
    //    //this.imageView = imageView;
    //    //this.fileDownloadCallBack = fileDownloadCallBack;
    //    //this.reqWidth = reqWidth;
    //    //this.reqHeight = reqHeight;
    //}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //imageView.post(new Runnable() {
        //    @Override
        //    public void run() {
        //        if (reqWidth == 0) {
        //            reqWidth = imageView.getWidth();
        //        } else {
        //            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        //            layoutParams.realWidth = reqWidth;
        //            imageView.setLayoutParams(layoutParams);
        //        }
        //        if (reqHeight == 0) {
        //            reqHeight = imageView.getHeight();
        //        } else {
        //            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        //            layoutParams.realHeight = reqHeight;
        //            imageView.setLayoutParams(layoutParams);
        //        }
        //    }
        //});
    }

    @Override
    protected BitmapUtil.BitmapResult doInBackground(Void... voids) {
        try {
            //此file就是对应文件的缓存路径
            final File file = Glide.with(context)
                    .load(imageUrl)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

            BitmapUtil.BitmapResult bitmapResult = null;

            if (file != null) {

                //此path就是对应文件的缓存路径
                String path = file.getAbsolutePath();
                log.i("doInBackground() path: " + path);

                // 最后通知图库更新
                // context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                //         Uri.fromFile(result)));

                //Bitmap bmp = BitmapFactory.decodeFile(path);
                // Bitmap bmp = BitmapUtil.decodeSampledBitmapFromFile(path, 700, 600);

                bitmapResult = BitmapUtil.decodeSampledBitmapFromFile(path, reqWidth, reqHeight, targetAngle);
            }

            if (fileDownloadCallBack != null) {
                final BitmapUtil.BitmapResult finalBitmapResult = bitmapResult;
                AppUtil.getMainHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        fileDownloadCallBack.onGetImageCacheCallBack(file != null, imageUrl, file, finalBitmapResult);
                    }
                });
            }

            return bitmapResult;
        } catch (Exception e) {
            log.e("doInBackground():  " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(BitmapUtil.BitmapResult result) {
        if (result != null && imageView != null) {
            imageView.setImageBitmap(result.bitmap);
        }
    }
}


