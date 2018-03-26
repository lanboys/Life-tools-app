package com.mjn.libs.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BitmapTools {
    /**
     * 从文件解析出Bitmap格式的图片 质量压缩
     *
     * @return
     */
    public static Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            InputStream openStream = new URL(url).openStream();
            in = new BufferedInputStream(openStream, 4 * 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 4 * 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap decodeUriAsBitmapFromNet(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[4 * 1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    public static Bitmap decodeFile(String path, int maxWidth, int maxHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap image = null;
        image = BitmapFactory.decodeFile(path, options);
        double ratio = 1D;
        if (maxWidth > 0 && maxHeight <= 0) {
            // 限定宽度，高度不做限制
            ratio = Math.ceil(options.outWidth / maxWidth);
        } else if (maxHeight > 0 && maxWidth <= 0) {
            // 限定高度，不限制宽度
            ratio = Math.ceil(options.outHeight / maxHeight);
        } else if (maxWidth > 0 && maxHeight > 0) {
            // 高度和宽度都做了限制，这时候我们计算在这个限制内能容纳的最大的图片尺寸，不会使图片变形
            double _widthRatio = Math.ceil(options.outWidth / maxWidth);
            double _heightRatio = (double) Math.ceil(options.outHeight
                    / maxHeight);
            ratio = _widthRatio > _heightRatio ? _widthRatio : _heightRatio;
        }
        if (ratio > 1) {
            options.inSampleSize = (int) ratio;

        }
        options.inSampleSize = calcScaleRatio(maxWidth, maxHeight,
                options.outWidth, options.outHeight);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        image = BitmapFactory.decodeFile(path, options);
        return image;
    }

    /**
     * 从文件解析出Bitmap格式的图片并保存图片
     *
     * @param path
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    @SuppressLint("NewApi")
    public static Bitmap decodeFile(String path, int maxWidth, int maxHeight, String savePath) {
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = true;
        Bitmap image = null;
//		image = BitmapFactory.decodeFile(path, options);
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
        options.inJustDecodeBounds = true;
        FileInputStream fin;
        try {
            fin = new FileInputStream(path);
            // 取得图片
//	        InputStream temp = this.getAssets().open(path);   

            // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
            BitmapFactory.decodeStream(fin, null, options);
            // 关闭流
            fin.close();


            double ratio = 1D;
            if (maxWidth > 0 && maxHeight <= 0) {
                // 限定宽度，高度不做限制
                ratio = Math.ceil(options.outWidth / maxWidth);
            } else if (maxHeight > 0 && maxWidth <= 0) {
                // 限定高度，不限制宽度
                ratio = Math.ceil(options.outHeight / maxHeight);
            } else if (maxWidth > 0 && maxHeight > 0) {
                // 高度和宽度都做了限制，这时候我们计算在这个限制内能容纳的最大的图片尺寸，不会使图片变形
                double _widthRatio = Math.ceil(options.outWidth / maxWidth);
                double _heightRatio = (double) Math.ceil(options.outHeight
                        / maxHeight);
                ratio = _widthRatio > _heightRatio ? _widthRatio : _heightRatio;
            }
            if (ratio > 1) {
                options.inSampleSize = (int) ratio;

            } else {
                options.inSampleSize = 1;
            }
            fin = new FileInputStream(path);
//		options.inSampleSize = calcScaleRatio(maxWidth, maxHeight,
//				options.outWidth, options.outHeight);
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            image = BitmapFactory.decodeStream(fin, null, options);
            int digree = readPictureDegree(path);
//		Bitmap bm=Util.decodeFile(picturePath1, 480, 800);
            image = rotate(image, digree);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        image.getByteCount();
        return compressImage(image, savePath);
    }

    private static int calcScaleRatio(int maxWidth, int maxHeight,
                                      int outWidth, int outHeight) {
        // 图像原始高度和宽度

        final int height = outHeight;

        final int width = outWidth;

        int inSampleSize = 1;

        if (height > maxHeight || width > maxWidth) {

            if (width > height) {

                inSampleSize = Math.round((float) height / (float) maxHeight);

            } else {

                inSampleSize = Math.round((float) width / (float) maxWidth);

            }

        }

        return inSampleSize;
    }

    /**
     * 图片大小压缩
     *
     * @param image
     * @return
     */

    public static Bitmap compressImage1(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        try {
            isBm.close();
            baos.close();
            if (image != null) {
                image.recycle();
                System.gc();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 质量压缩，保存图片
     *
     * @param image
     * @return
     */
    @SuppressLint("NewApi")
    private static Bitmap compressImage(Bitmap image, String srcPath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 90, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 20;// 每次都减少10
            image.compress(Bitmap.CompressFormat.PNG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
//		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
//		System.out.println("图片大小"+isBm.available());
//		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        try {
            FileOutputStream fos = new FileOutputStream(srcPath);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
//				if(image!=null){
//					image.recycle();
//					System.gc();
//				}
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        image.getByteCount();
        return image;
    }

    /**
     * 从输入流读取数据
     *
     * @param inStream
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static byte[] read(InputStream inStream) throws IOException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    /**
     * 根据指定的压缩比例，获得合适的Bitmap（方法二）
     *
     * @param inStream InputStream
     * @param width    指定的宽度
     * @param height   指定的高度
     * @return Bitmap
     * @throws IOException
     */
    public static Bitmap decodeStream(InputStream inStream, int width, int height) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 从输入流读取数据
        byte[] data = read(inStream);
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        int w = options.outWidth;
        int h = options.outHeight;
        // 从服务器端获取的图片大小为：80x120
        // 我们想要的图片大小为：40x40
        // 缩放比：120/40 = 3，也就是说我们要的图片大小为原图的1/3
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int ratio = 1; // 默认为不缩放
        if (w >= h && w > width) {
            ratio = (int) (w / width);
        } else if (w < h && h > height) {
            ratio = (int) (h / height);
        }
        if (ratio <= 0) {
            ratio = 1;
        }
        options.inJustDecodeBounds = false;
        // 属性值inSampleSize表示缩略图大小为原始图片大小的几分之一，即如果这个值为2，
        // 则取出的缩略图的宽和高都是原始图片的1/2，图片大小就为原始大小的1/4。
        options.inSampleSize = ratio;
        Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        return compressImage1(bm);
    }

    /**
     * 根据指定的压缩比例，获得合适的Bitmap（方法二）
     *
     * @param width  指定的宽度
     * @param height 指定的高度
     * @return Bitmap
     * @throws IOException
     */
    public static Bitmap decodeStream(byte[] data, int width, int height) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 从输入流读取数据
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        int w = options.outWidth;
        int h = options.outHeight;
        // 从服务器端获取的图片大小为：80x120
        // 我们想要的图片大小为：40x40
        // 缩放比：120/40 = 3，也就是说我们要的图片大小为原图的1/3
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int ratio = 1; // 默认为不缩放
        if (w >= h && w > width) {
            ratio = (int) (w / width);
        } else if (w < h && h > height) {
            ratio = (int) (h / height);
        }
        if (ratio <= 0) {
            ratio = 1;
        }
        options.inJustDecodeBounds = false;
        // 属性值inSampleSize表示缩略图大小为原始图片大小的几分之一，即如果这个值为2，
        // 则取出的缩略图的宽和高都是原始图片的1/2，图片大小就为原始大小的1/4。
        options.inSampleSize = ratio;
        Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        return bm;
    }


    public static Bitmap revitionImageSize(String path, int size, String srcPath) throws IOException {

        FileInputStream fin = new FileInputStream(path);
        // 取得图片
//		        InputStream temp = this.getAssets().open(path);   
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
        options.inJustDecodeBounds = true;
        // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
        BitmapFactory.decodeStream(fin, null, options);
        // 关闭流
        fin.close();

        // 生成压缩的图片
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            // 这一步是根据要设置的大小，使宽和高都能满足
            if ((options.outWidth >> i <= size)
                    && (options.outHeight >> i <= size)) {
                // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
                fin = new FileInputStream(path);
                // 这个参数表示 新生成的图片为原始图片的几分之一。
                options.inSampleSize = (int) Math.pow(2.0D, i);
                // 这里之前设置为了true，所以要改为false，否则就创建不出图片
                options.inJustDecodeBounds = false;

                bitmap = BitmapFactory.decodeStream(fin, null, options);
                break;
            }
            i += 1;
        }
        return compressImage(bitmap, srcPath);
    }


    /**
     * 图片大小压缩
     *
     * @param image
     * @return
     */

    public static Bitmap compressImage2(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 400) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(Bitmap.CompressFormat.PNG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        try {
            isBm.close();
            baos.close();
            if (image != null) {
                image.recycle();
                System.gc();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static int readPictureDegree(String path) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap rotate(Bitmap b, int degrees) {
        if (degrees == 0) {
            return b;
        }
        if (degrees != 0 && b != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) b.getWidth(), (float) b.getHeight());
            try {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if (b != b2) {
                    b.recycle();
                    b = b2;
                }
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
            }
        }
        return b;
    }


    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }
}
