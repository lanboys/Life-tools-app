package com.bing.lan.comm.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.SystemClock;

import com.bing.lan.comm.utils.loader.AssetsLoader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * bitmap相关操作工具类
 *
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.utils
 * @date 2016/11/28  09:45
 */

public class BitmapUtil {

    protected static final LogUtil log = LogUtil.getLogUtil(BitmapUtil.class, LogUtil.LOG_VERBOSE);

    public static final int WRAP_CONTENT_LENGTH = -1;

    public static class BitmapResult {

        //源文件宽高
        public int srcWidth;
        public int srcHeight;

        //要求的宽高
        public int reqWidth;
        public int reqHeight;
        //加载到内存的宽高
        public int realWidth;
        public int realHeight;
        //加载到内存中的 宽/高 比例
        public float realRatio;

        public int inSampleSize = 1;
        public Bitmap bitmap;

        @Override
        public String toString() {
            return "BitmapResult{" +
                    "srcWidth=" + srcWidth +
                    ", srcHeight=" + srcHeight +
                    ", reqWidth=" + reqWidth +
                    ", reqHeight=" + reqHeight +
                    ", realWidth=" + realWidth +
                    ", realHeight=" + realHeight +
                    ", realRatio=" + realRatio +
                    ", inSampleSize=" + inSampleSize +
                    ", bitmap=" + bitmap +
                    '}';
        }
    }

    /**
     * 计算图片的压缩比率(不计算放大)
     *
     * @param options   参数
     * @param reqWidth  目标的宽度
     * @param reqHeight 目标的高度
     */
    private static BitmapResult calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        BitmapResult bitmapResult = new BitmapResult();
        bitmapResult.srcHeight = options.outHeight;
        bitmapResult.srcWidth = options.outWidth;

        int inSampleSize = 1;

        // 一个为0 就按原来比例
        if (reqHeight == 0 || reqWidth == 0) {
            bitmapResult.inSampleSize = inSampleSize;
            bitmapResult.reqHeight = reqHeight;
            bitmapResult.reqWidth = reqWidth;

            bitmapResult.realHeight = height / inSampleSize;
            bitmapResult.realWidth = width / inSampleSize;

            return bitmapResult;
        }

        if (reqHeight == WRAP_CONTENT_LENGTH) {
            reqHeight = height;
        }

        if (reqWidth == WRAP_CONTENT_LENGTH) {
            reqWidth = width;
        }
        //https://www.jianshu.com/p/67cfd38c52dc
        //if (realHeight > reqHeight || realWidth > reqWidth) {
        //    // Calculate ratios of realHeight and realWidth to requested realHeight and realWidth,计算出实际宽高和目标宽高的比率
        //    final int widthRatio = Math.round((float) realWidth / (float) reqWidth);
        //    final int heightRatio = Math.round((float) realHeight / (float) reqHeight);
        //
        //    // 选择宽和高中   最小的比率   作为inSampleSize的值，
        //    // 这样可以保证最终图片的宽和高一定都会   大于等于   目标的宽和高。
        //    inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        //
        //    // Anything more than 2x the requested pixels we'll sample down
        //    final float totalPixels = realWidth * realHeight;
        //    // further
        //    final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        //    while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
        //        inSampleSize++;
        //    }
        //}

        if (height > reqHeight || width > reqWidth) {
            //  计算出实际宽高和目标宽高的比率
            //final int widthRatio = Math.round((float) realWidth / (float) reqWidth);
            //final int heightRatio = Math.round((float) realHeight / (float) reqHeight);
            float widthRatio = (float) width / (float) reqWidth;
            float heightRatio = (float) height / (float) reqHeight;

            // 选择宽和高中   最大的比率   作为inSampleSize的值，
            // 这样可以保证最终图片的宽和高一定都会    小于等于   目标的宽和高。
            float v = heightRatio > widthRatio ? heightRatio : widthRatio;
            inSampleSize = (int) (v + 0.5);
        }

        //if (realHeight > reqHeight || realWidth > reqWidth) {
        //    final int halfHeight = realHeight / 2;
        //    final int halfWidth = realWidth / 2;
        //
        //    // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        //    // realHeight and realWidth larger than the requested realHeight and realWidth.
        //
        //    //http://blog.csdn.net/niu0147/article/details/45582899
        //    //https://www.cnblogs.com/androidxiaoyang/p/3752905.html
        //    //官网的这个方法是: 将图片  一半一半  的压缩,直到压缩成成  大于  所需宽高数的那个最低值
        //
        //    //while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
        //    //    inSampleSize *= 2;
        //    //}
        //    // 简化如下
        //    float h = halfHeight / inSampleSize;
        //    float w = halfWidth / inSampleSize;
        //
        //    while (h >= reqHeight && w >= reqWidth) {
        //        inSampleSize *= 2;
        //        h = halfHeight / inSampleSize;
        //        w = halfWidth / inSampleSize;
        //    }
        //}

        bitmapResult.reqHeight = reqHeight;
        bitmapResult.reqWidth = reqWidth;
        if (inSampleSize < 1) {
            inSampleSize = 1;
        }

        bitmapResult.inSampleSize = inSampleSize;

        //bitmapResult.realHeight = realHeight / inSampleSize;
        //bitmapResult.realWidth = realWidth / inSampleSize;

        return bitmapResult;
    }

    /**
     * 从Resources中加载图片
     */
    public static Bitmap decodeSampledBitmapFromResource(
            Resources res, int resId, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置成了true,不占用内存，只获取bitmap宽高
        options.inJustDecodeBounds = true;
        // 读取图片长款
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        BitmapResult bitmapResult = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inSampleSize = bitmapResult.inSampleSize;
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        // 载入一个稍大的缩略图
        Bitmap src = BitmapFactory.decodeResource(res, resId, options);
        // 进一步得到目标大小的缩略图
        return createScaleBitmap(src, reqWidth, reqHeight, options.inSampleSize);
    }

    /**
     * 通过传入的bitmap，进行压缩，得到符合尺寸标准的bitmap
     */
    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight, int inSampleSize) {
        //如果inSampleSize是2的倍数，也就说这个src已经是我们想要的缩略图了，直接返回即可。
        if (inSampleSize % 2 == 0) {
            return src;
        }
        // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，
        // 我们这里是缩小图片，所以直接设置为false
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    /**
     * 按原比例 从SD卡加载图片
     */
    public static Bitmap decodeSampledBitmapFromFile(String pathName) {
        return decodeSampledBitmapFromFile(pathName, 0);
    }

    /**
     * 按原比例 从SD卡加载图片
     */
    public static Bitmap decodeSampledBitmapFromFile(String pathName, int targetAngle) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        int bitmapAngle = getBitmapDegree(pathName);
        if (bitmapAngle != targetAngle) {
            //对图片进行旋转校验 如果图片已经出现旋转了  现在开始复位并返回
            src = rotateBitmapByAngle(src, bitmapAngle - targetAngle);
            log.d("decodeSampledBitmapFromFile(): 照片旋转角度:  " + (bitmapAngle - targetAngle));
        }

        return src;
    }

    /**
     * 根据给定的宽高 从SD卡上加载图片
     */
    public static BitmapResult decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
        return decodeSampledBitmapFromFile(pathName, reqWidth, reqHeight, 0);
    }

    /**
     * 根据给定的宽高 从SD卡上加载图片
     */
    public static BitmapResult decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight, int targetAngle) {

        log.e("decodeSampledBitmapFromFile() reqWidth: " + reqWidth + " , reqHeight: " + reqHeight);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        BitmapResult bitmapResult = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inSampleSize = bitmapResult.inSampleSize;
        log.e("decodeSampledBitmapFromFile():  options.inSampleSize " + options.inSampleSize);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);

        int bitmapAngle = getBitmapDegree(pathName);
        if (bitmapAngle != targetAngle) {
            //对图片进行旋转校验 如果图片已经出现旋转了  现在开始复位并返回
            src = rotateBitmapByAngle(src, bitmapAngle - targetAngle);
            log.d("decodeSampledBitmapFromFile(): 照片旋转角度:  " + (bitmapAngle - targetAngle));
        }
        bitmapResult.bitmap = src;

        bitmapResult.realHeight = src.getHeight();
        bitmapResult.realWidth = src.getWidth();

        bitmapResult.realRatio = (float) bitmapResult.realWidth / (float) bitmapResult.realHeight;

        return bitmapResult;
    }

    public static Bitmap decodeSampledBitmapFromFileAndScale(String pathName, int reqWidth, int reqHeight) {
        return decodeSampledBitmapFromFileAndScale(pathName, reqWidth, reqHeight, 0);
    }

    public static Bitmap decodeSampledBitmapFromFileAndScale(String pathName, int reqWidth, int reqHeight, int targetAngle) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        BitmapResult bitmapResult = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inSampleSize = bitmapResult.inSampleSize;
        log.e("decodeSampledBitmapFromFile():  options.inSampleSize " + options.inSampleSize);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);

        int bitmapAngle = getBitmapDegree(pathName);
        if (bitmapAngle != targetAngle) {
            //对图片进行旋转校验 如果图片已经出现旋转了  现在开始复位并返回
            src = rotateBitmapByAngle(src, bitmapAngle - targetAngle);
            log.d("decodeSampledBitmapFromFile(): 照片旋转角度:  " + (bitmapAngle - targetAngle));
        }
        return createScaleBitmap(src, reqWidth, reqHeight, options.inSampleSize);
    }

    /**
     * 获取bitmap的大小(字节)  注意bitmap与file大小不一样
     *
     * @param bitmap
     * @return 字节
     */
    public static long getBitmapSize(Bitmap bitmap) {

        if (bitmap == null) {
            log.e("getBitmapSize(): bitmap 为 null");
            return 0;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

    public static String saveBitmapToFile(Bitmap bitmap, String path, String fileName) {
        String filePath = "";
        if (bitmap == null) {
            return filePath;
        } else {

            filePath = path + fileName;
            File destFile = new File(filePath);
            OutputStream os = null;
            try {
                os = new FileOutputStream(destFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
            } catch (IOException e) {
                filePath = "";
            }
        }
        return filePath;
    }

    public static File saveBitmapToFile(Bitmap bitmap, File destFile, int quality) {
        if (bitmap == null) {
            throw new NullPointerException("bitmap==null");
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(destFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
            os.flush();
            os.close();
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            return destFile;
        } catch (IOException e) {
            log.e("saveBitmapToFile():  " + e.getLocalizedMessage());
        }
        return null;
    }

    public static File saveAssetsBitmapToFile(Context context, String fileName, File destFile) {
        Bitmap bitmap = AssetsLoader.loadBitmap(context, fileName);
        if (bitmap != null) {
            return saveBitmapToFile(bitmap, destFile, 50);
        }
        return null;
    }

    /**
     * 原比例压缩压缩
     */
    public static File compressImage(File srcFile, File destFile, int maxSize) {
        final Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromFile(srcFile.getAbsolutePath());
        BitmapUtil.compressImage(bitmap, destFile, maxSize);
        return destFile;
    }

    /**
     * 按比例压缩压缩
     */
    public static File compressImage(File srcFile, File destFile, int reqWidth, int reqHeight, int maxSize) {
        final BitmapResult bitmap = BitmapUtil.decodeSampledBitmapFromFile(srcFile.getAbsolutePath(), reqWidth, reqHeight);
        BitmapUtil.compressImage(bitmap.bitmap, destFile, maxSize);
        return destFile;
    }

    /**
     * 质量压缩方法 ,将bitmap---->文件
     *
     * @param bitmap
     * @param maxSize 单位 kb    1kb = 1024 byte = 1024 X 8 bit
     */
    public static File compressImage(Bitmap bitmap, File destFile, int maxSize) {

        long startTime = SystemClock.currentThreadTimeMillis();
        log.i("compressImage() startTime: " + startTime);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 90;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

        while (baos.toByteArray().length / 1024 > maxSize && quality > 10) {
            baos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            log.i("compressImage(): 压缩 quality: " + quality);
        }

        try {
            baos.writeTo(new FileOutputStream(destFile));
        } catch (Exception e) {
            log.e("compressImage():  " + e.getLocalizedMessage());
        } finally {
            try {
                baos.flush();
                baos.close();
            } catch (IOException e) {
                log.e("compressImage():  " + e.getLocalizedMessage());
            }
        }

        // if (!bitmap.isRecycled()) {
        //     bitmap.recycle();
        // }
        long endTime = SystemClock.currentThreadTimeMillis();
        log.i("compressImage() endTime: " + endTime);
        log.i("compressImage() 压缩用时: " + (endTime - startTime));
        try {
            log.i("compressImage() 压缩后大小: " + FileUtil.formatFileSize(FileUtil.getFileSize(destFile)));
        } catch (Exception e) {
            log.e("compressImage():  " + e.getLocalizedMessage());
        }

        return destFile;
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image, int maxSize) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > maxSize) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        try {
            baos.flush();
            baos.close();
            isBm.close();
        } catch (IOException e) {
            log.e("compressImage():  " + e.getLocalizedMessage());
        }
        // if (!image.isRecycled()) {
        //     image.recycle();
        // }

        return bitmap;
    }

    /**
     * 将图片路径Uri所表示的图片转换成指定大小的照片显示出来
     */
    public static Bitmap getThumbnail(Bitmap srcBmp, int reqWidth, int reqHeight) {
        Bitmap dstBmp;
        if (srcBmp.getWidth() < reqWidth && srcBmp.getHeight() < reqHeight) {
            dstBmp = ThumbnailUtils.extractThumbnail(srcBmp, reqWidth, reqHeight);
            // Otherwise the ratio between measures is calculated to fit requested thumbnail's one
        } else {
            int x = 0, y = 0, width = srcBmp.getWidth(), height = srcBmp.getHeight();
            float ratio = ((float) reqWidth / (float) reqHeight) * ((float) srcBmp.getHeight() / (float) srcBmp.getWidth());
            if (ratio < 1) {
                x = (int) (srcBmp.getWidth() - srcBmp.getWidth() * ratio) / 2;
                width = (int) (srcBmp.getWidth() * ratio);
            } else {
                y = (int) (srcBmp.getHeight() - srcBmp.getHeight() / ratio) / 2;
                height = (int) (srcBmp.getHeight() / ratio);
            }
            dstBmp = Bitmap.createBitmap(srcBmp, x, y, width, height);
        }
        return dstBmp;
    }

    /**
     * 获取位图的方向	0	90	180	  270
     *
     * @param path 图片的本地的文件地址
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            log.e("getBitmapDegree():  " + e.getLocalizedMessage());
        }
        return degree;
    }

    /**
     * 当程序员发现如果返回的图片出现了旋转 可以通过该方法进行复位
     */
    public static Bitmap rotateBitmapByAngle(Bitmap bmp, int degress) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degress);
        Bitmap result = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        return result;
    }

    /**
     * 原比例压缩压缩
     */
    public static File compressImage(Context context, String srcFile, int maxSize) {
        final Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromFile(srcFile);
        File destFile = createImageFile(context);
        BitmapUtil.compressImage(bitmap, destFile, maxSize);
        return destFile;
    }

    /**
     * 删除压缩图片
     */
    public static void deleteCompressImage(Context context) {
        File storageDir = context.getExternalCacheDir();//放在缓存中--->抛异常，必须主动生成目录会报错
        storageDir = new File(storageDir, "PhotoSelectCache");
        FileUtil.deleteFileOrFolder(storageDir);
    }

    // 创建图片路径
    public static File createImageFile(Context context) {
        String timeStamp = new SimpleDateFormat("MMdd_HHmmss").format(new Date());//yyyyMMdd_
        String imageFileName = "Y_" + timeStamp;

        //File storageDir = context.getExternalFilesDir("PhotoSelectCache");
        //File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = context.getExternalCacheDir();//放在缓存中--->抛异常，必须主动生成目录会报错
        storageDir = new File(storageDir, "PhotoSelectCache");

        if (!storageDir.mkdirs() && (!storageDir.exists() || !storageDir.isDirectory())) {
            return null;
        }

        File image;
        try {
            image = File.createTempFile(
                    imageFileName,      /* prefix 文件名前缀*/
                    ".jpg",              /* suffix 后缀*/
                    storageDir          /* directory 路径*/
            );
        } catch (IOException e) {

            File cacheDir = new File(context.getCacheDir(), "pic");
            if (!cacheDir.mkdirs() && (!cacheDir.exists() || !cacheDir.isDirectory())) {
                return null;
            }
            image = new File(cacheDir, imageFileName + ".jpg");
        }

        return image;
    }
}
