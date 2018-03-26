package com.bing.lan.comm.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bing.lan.comm.R;
import com.bing.lan.comm.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

/**
 * @author 蓝兵
 * @time 2017/2/23  22:43
 */
public class GlideLoadStrategy implements IBaseLoaderStrategy {

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Override
    public void loadImage(Context ctx, ImageView imageView, String imageUrl) {
        loadImagePlaceholder(ctx, imageView, imageUrl, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
    }

    @Override
    public void loadImage(Context ctx, ImageView imageView, String imageUrl, int reqWidth, int reqHeight) {
        loadImagePlaceholder(ctx, imageView, imageUrl, reqWidth, reqHeight, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
    }

    @Override
    public void loadImagePlaceholder(Context ctx, ImageView imageView, String imageUrl, int placeholderResId, int errorResId) {
        //不给override尺寸按ImageView尺寸进行加载图片到内存,从网络下载的图片都是原尺寸
        Glide.with(ctx)
                .load(imageUrl)
                .asBitmap()
                // .Transformation()
                .thumbnail(0.1f)
                //.override(imageViewWidth, finalHeight)
                .placeholder(placeholderResId)
                .error(errorResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void loadImagePlaceholder(Context ctx, ImageView imageView, String imageUrl, int reqWidth, int reqHeight,
            int placeholderResId, int errorResId) {
        log.i("loadImage()  reqWidth: " + reqWidth + "   reqHeight: " + reqHeight);

        Glide.with(ctx)
                .load(imageUrl)
                .asBitmap()
                // .Transformation()
                .thumbnail(0.1f)
                .override(reqWidth, reqHeight)// 宽高是用于内部的 BitmapFactory.decodeFile
                .placeholder(placeholderResId)
                .error(errorResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public void loadOriginImage(final Context ctx, final ImageView imageView, final String imageUrl, final int imageViewWidth, int reqHeight) {

        log.i("loadImage()  reqWidth: " + imageViewWidth + "   reqHeight: " + reqHeight);

        final ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();

        Glide.with(ctx)
                .load(imageUrl)
                .asBitmap()
                // .Transformation()
                // .thumbnail(0.1f)
                // .fitCenter()
                // .centerCrop() Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL
                // .placeholder(R.drawable.image_default_202)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                          @Override
                          public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {

                              int width = bitmap.getWidth();
                              int height = bitmap.getHeight();
                              log.d("onResourceReady(): bitmap realWidth:  " + width + ", bitmap realHeight: " + height + "  imageUrl: " + imageUrl);

                              // imageView1.setRelative(realWidth / realHeight);
                              // imageView.setImageBitmap(bitmap);

                              //计算控件高宽比
                              int finalHeight = (int) (imageViewWidth * height / width + 0.5f);
                              layoutParams.height = finalHeight;

                              log.d("onResourceReady(): imageViewWidth: " + imageViewWidth + " imageViewHeight: " + finalHeight);

                              // //计算inSampleSize
                              // float scaleW = (realWidth * 1.0f) / imageViewWidth;
                              // float scaleH = (realHeight * 1.0f) / layoutParams.realHeight;
                              // //取大的
                              // float inSampleSize = Math.max(scaleW, scaleH);

                              // Bitmap scaleBitmap = BitmapUtils.createScaleBitmap(bitmap, Math.abs(imageViewWidth), Math.abs(finalHeight), (int) inSampleSize);

                              // imageView.setImageBitmap(bitmap);
                              bitmap.recycle();

                              Glide.with(ctx)
                                      .load(imageUrl)
                                      .asBitmap()
                                      // .Transformation()
                                      .thumbnail(0.1f)
                                      .override(imageViewWidth, finalHeight)
                                      // .placeholder(R.drawable.image_default_202)
                                      .error(R.drawable.iv_logo)
                                      .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                      .into(imageView);
                          }
                      }
                );
    }

    @Override
    public void loadSmallImage(Context ctx, ImageView imageView, String imageUrl) {

    }

    @Override
    public void loadBigImage(Context ctx, ImageView imageView, String imageUrl) {

        Glide.with(ctx)
                .load(imageUrl)
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {

                    }
                });
    }

    /**
     * 先获取缓存文件 ---> 再设置到ImageView中
     * 目的: 在回调中返回 图片File
     *
     * @param ctx
     * @param imageView
     * @param imageUrl
     * @param fileDownloadCallBack
     */
    //@Override
    //public void loadImageCallBackFile(Context ctx, ImageView imageView, String imageUrl,
    //        DownloadImageCallBack fileDownloadCallBack, int targetAngle) {
    //    loadImageCallBackFile(ctx, imageView, imageUrl, 0, 0, fileDownloadCallBack, targetAngle);
    //}
    @Override
    public void loadImageCallBackFile(Context ctx, ImageView imageView, String imageUrl, int reqWidth, int reqHeight,
            GetImageCacheCallBack fileDownloadCallBack, int targetAngle) {
        GetImageCacheAsyncTask task = new GetImageCacheAsyncTask(ctx, imageView, imageUrl,
                fileDownloadCallBack, reqWidth, reqHeight, targetAngle);
        task.execute();
    }

    /**
     * 只下载图片
     *
     * @param ctx
     * @param imageUrl
     * @param destFile
     * @param fileDownloadCallBack
     */
    @Override
    public void downloadImageFileOnly(Context ctx, String imageUrl, File destFile,
            DownloadImageCallBack fileDownloadCallBack) {

        DownloadImageTask task = new DownloadImageTask(ctx, imageUrl, destFile,
                fileDownloadCallBack);

        task.execute();
    }

    // private void loadImageByLoad(ViewHolder holder, String imageUrl, int position) {
    //
    //     // String imagePath = new File(AppUtil.getAppContext().getFilesDir(), position + "haha.jpeg").getAbsolutePath();
    //     //
    //     // final int imageViewSize = AppUtil.getScreenW() / GRIL_COLUMN;
    //     // final ImageView imageView = holder.getView(R.id.iv_girl);
    //     //
    //     // manager.loadImage(imageUrl, imagePath, new IAsyncImageLoadedCallBack() {
    //     //     @Override
    //     //     public void imageLoaded(Bitmap imageBitmap, String imgUrl) {
    //     //         int realWidth = imageBitmap.getWidth();
    //     //         int realHeight = imageBitmap.getHeight();
    //     //
    //     //         // imageView1.setRelative(realWidth / realHeight);
    //     //         // imageView.setImageBitmap(imageBitmap);
    //     //
    //     //         //计算高宽比
    //     //         int finalHeight = (imageViewSize) * realHeight / realWidth;
    //     //
    //     //         ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
    //     //         layoutParams.realHeight = finalHeight;
    //     //         imageView.setImageBitmap(imageBitmap);
    //     //     }
    //     // });
    // }
    //
    // private void loadImageByPicasso(final ViewHolder holder, String imageUrl) {
    //     // final ImageView imageView = holder.getView(R.id.iv_girl);
    //     // ImagePicassoUtil.loadBigImage(imageView, imageUrl);
    // }
    //
    // private void loadImageByGlide(final ViewHolder holder, final String imageUrl) {
    //     // final int imageViewWidth = AppUtil.getScreenW() / GRIL_COLUMN;
    //     // final ImageView imageView = holder.getView(R.id.iv_girl);
    //     // final ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
    //     // //
    //     // Glide.with(getContext())
    //     //         .load(imageUrl)
    //     //         .asBitmap()
    //     //         // .Transformation()
    //     //         // .thumbnail(0.1f)
    //     //         // .fitCenter()
    //     //         // .centerCrop() Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL
    //     //         // .placeholder(R.drawable.image_default_202)
    //     //         .diskCacheStrategy(DiskCacheStrategy.SOURCE)
    //     //         .into(new SimpleTarget<Bitmap>() {
    //     //                   @Override
    //     //                   public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
    //     //
    //     //                       int realWidth = bitmap.getWidth();
    //     //                       int realHeight = bitmap.getHeight();
    //     //                       log.d("onResourceReady(): imageUrl:  " + imageUrl);
    //     //                       log.d("onResourceReady(): realWidth:  " + realWidth + ", realHeight: " + realHeight + "   " + imageUrl);
    //     //
    //     //                       // imageView1.setRelative(realWidth / realHeight);
    //     //                       // imageView.setImageBitmap(bitmap);
    //     //
    //     //                       //计算控件高宽比
    //     //                       int finalHeight = (int) (imageViewWidth * realHeight / realWidth + 0.5f);
    //     //                       layoutParams.realHeight = finalHeight;
    //     //
    //     //                       log.d("onResourceReady(): imageViewWidth: " + imageViewWidth
    //     //                               + " imageViewHeight: " + finalHeight);
    //     //
    //     //                       // //计算inSampleSize
    //     //                       // float scaleW = (realWidth * 1.0f) / imageViewWidth;
    //     //                       // float scaleH = (realHeight * 1.0f) / layoutParams.realHeight;
    //     //                       // //取大的
    //     //                       // float inSampleSize = Math.max(scaleW, scaleH);
    //     //
    //     //                       // Bitmap scaleBitmap = BitmapUtils.createScaleBitmap(bitmap, Math.abs(imageViewWidth), Math.abs(finalHeight), (int) inSampleSize);
    //     //
    //     //                       // imageView.setImageBitmap(bitmap);
    //     //                       bitmap.recycle();
    //     //
    //     //                       Glide.with(getContext())
    //     //                               .load(imageUrl)
    //     //                               .asBitmap()
    //     //                               // .Transformation()
    //     //                               .thumbnail(0.1f)
    //     //                               .override(imageViewWidth, finalHeight)
    //     //                               // .placeholder(R.drawable.image_default_202)
    //     //                               .error(R.drawable.image_default_202)
    //     //                               .diskCacheStrategy(DiskCacheStrategy.RESULT)
    //     //                               .into(imageView);
    //     //                   }
    //     //               }
    //     //         );
    // }
}
