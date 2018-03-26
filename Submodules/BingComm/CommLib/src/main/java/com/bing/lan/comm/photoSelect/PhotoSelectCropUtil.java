package com.bing.lan.comm.photoSelect;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ScaleDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bing.lan.comm.R;
import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.dialog.RxDialog;
import com.bing.lan.comm.utils.BitmapUtil;
import com.bing.lan.comm.utils.FileUtil;
import com.bing.lan.comm.utils.LogUtil;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Author: 蓝兵
 * Email: lan_bing2013@163.com
 * Time: 2017/4/13  12:19
 */

/**
 * 注意注册activity
 * <p>
 * <activity
 * android:name="com.soundcloud.android.crop.CropImageActivity"
 * android:screenOrientation="portrait">
 * </activity>
 */

public class PhotoSelectCropUtil {

    private static final int REQUEST_IMAGE_CAPTURE = 891;
    private static final int REQUEST_IMAGE_PICK = 8921;

    private static final int ACTION_CAMERA = 1;
    private static final int ACTION_ALBUM = 2;
    private static final int ACTION_OTHER = -1;

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    protected static final LogUtil log1 = LogUtil.getLogUtil(PhotoSelectCropUtil.class, LogUtil.LOG_VERBOSE);
    private Activity mContext;
    private ImageView mPhotoImageView;
    private Uri mCurrentPhotoUri;
    private OnPhotoSelectListener mPhotoSelectListener;
    private boolean isCrop = false;
    private boolean isCapture;

    //350千字节(kb)=358400字节(b)
    //400千字节(kb)=409600字节(b)
    //500千字节(kb)=512000字节(b)
    // 600千字节(kb)=614400字节(b)
    // 0.7兆字节(mb)=734003.2字节(b)
    // 1兆字节(mb)=1048576字节(b)

    private long maxSize = 358400;//字节
    private ScaleDrawable mScaleDrawable;
    private ProgressDrawable mProgressDrawable;

    public PhotoSelectCropUtil(Activity context) {
        mContext = context;
    }

    public boolean isCrop() {
        return isCrop;
    }

    public void setCrop(boolean crop) {
        isCrop = crop;
    }

    public void setPhotoSelectListener(OnPhotoSelectListener photoSelectListener) {
        mPhotoSelectListener = photoSelectListener;
    }

    public void showSelectPhotoDialog(final ImageView imageView) {

        final RxDialog rxDialog = new RxDialog(imageView.getContext());
        rxDialog.getLayoutParams().gravity = Gravity.BOTTOM;

        View dialogView1 = LayoutInflater.from(imageView.getContext()).inflate(
                R.layout.dialog_picker_pictrue, null);
        // R.layout.dialog_camero_show
        // R.layout.dialog_picker_pictrue

        // Window window = rxDialog.getWindow();
        // window.setGravity(Gravity.BOTTOM);

        TextView tv_camera = (TextView) dialogView1
                .findViewById(R.id.tv_camera);
        TextView tv_file = (TextView) dialogView1
                .findViewById(R.id.tv_file);
        TextView tv_cancel = (TextView) dialogView1
                .findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                rxDialog.cancel();
            }
        });
        tv_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectPhotoFromCamera(imageView);
                rxDialog.cancel();
            }
        });
        tv_file.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectPhotoFromAlbum(imageView);
                rxDialog.cancel();
            }
        });
        rxDialog.setContentView(dialogView1);
        rxDialog.show();
    }

    public void showSelectPhotoPopup(final ImageView imageView) {

        if (imageView == null) {
            Toast.makeText(mContext, "ImageView不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // mPhotoImageView = imageView;

        final PhotoSelectPopupWindow popupWindow = new PhotoSelectPopupWindow(mContext);
        popupWindow.setOnItemClickListener(new PhotoSelectPopupWindow.OnItemClickListener() {
            @Override
            public void onItemClickListener(@PhotoSelectSource int type) {
                if (type == PhotoSelectSource.SELECT_CAMERA) {
                    //拍照
                    selectPhotoFromCamera(imageView);
                } else if (type == PhotoSelectSource.SELECT_ALBUM) {
                    //相册 选择
                    selectPhotoFromAlbum(imageView);
                }
            }
        });

        popupWindow.showAtLocation(mPhotoImageView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    // 拍照
    public void selectPhotoFromCamera(ImageView imageView) {
        if (imageView == null) {
            Toast.makeText(mContext, "ImageView不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mPhotoImageView = imageView;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(mContext, null);
                if (photoFile != null) {
                    Uri photoUri;
                    if (Build.VERSION.SDK_INT >= 24) {
                        ContentValues contentValues = new ContentValues(1);
                        contentValues.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
                        //contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                        photoUri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    } else {
                        //Uri photoUri = FileProvider.getUriForFile(mContext, FILE_PROVIDER, photoFile);
                        photoUri = Uri.fromFile(photoFile);
                    }
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    mCurrentPhotoUri = photoUri;
                    log.i("selectPhotoFromCamera(): mCurrentPhotoUri: " + mCurrentPhotoUri);// content://media/external/images/media/56727
                    mContext.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            } catch (Exception e) {
                log.e("selectPhotoFromCamera(): ", e);
            }
        }
    }

    // 获取到照片返回
    public void onSelectActivityResult(int requestCode, int resultCode, Intent data) {

        ImageView imageView = mPhotoImageView;

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                //系统拍照  界面返回
                //log.e("onSelectActivityResult(): mCurrentPhotoUri---" + mCurrentPhotoUri);
                //file:///storage/emulated/0/Android/data/com.bing.lan.comm/files/Pictures/JPEG_20170426_144058_435020664.jpg
                isCapture = true;

                if (isCrop) {
                    beginCrop(mCurrentPhotoUri, ACTION_CAMERA);
                } else {
                    handleCapture(imageView, mCurrentPhotoUri, ACTION_CAMERA);
                }
            } else if (requestCode == Crop.REQUEST_PICK || requestCode == REQUEST_IMAGE_PICK) {
                //系统选择照片  界面返回
                isCapture = false;

                if (isCrop) {
                    beginCrop(data.getData(), ACTION_ALBUM);
                } else {
                    handlePick(imageView, data.getData(), ACTION_ALBUM);
                }
            }
        }

        // 流程：启动相机/相册 --> 拍摄/选中照片 --> 返回主页面 --> 需要裁剪 --> 直接进入裁剪界面 --> 裁剪完成再次返回主页面

        //裁剪图片 界面返回
        if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(imageView, resultCode, data);
        }
    }

    //进入裁剪页面
    private void beginCrop(Uri source, int action) {

        String newPath = null;
        File file = null;

        try {
            //拍照 裁剪 返回 原路径加后缀保存
            if (action == ACTION_CAMERA) {
                String path = source.getPath();
                if (!isCapture) {
                    final File file1 = getAbsolutePathFile(mContext, source);
                    if (file1 != null) {
                        path = file1.getAbsolutePath();
                    }
                }

                String substring = path.substring(0, path.lastIndexOf("."));
                newPath = substring + "_crop.jpg";
                file = new File(newPath);
            }
        } catch (Exception e) {
            log.e("beginCrop():  " + e.getLocalizedMessage());
        }

        //相册选择 裁剪 返回
        if (file == null) {
            file = createImageFile(mContext, "crop");
        }

        Uri destination = Uri.fromFile(file);
        // log.e("beginCrop()--------source--Uri: " + source);//     file:///storage/emulated/0/Android/data/com.bing.lan.comm/files/Pictures/JPEG_20170426_144058_435020664.jpg
        // log.e("beginCrop()---destination--Uri: " + destination);//   file:///data/user/0/com.bing.lan.comm/cache/cropped
        // log.e("beginCrop()--destination--file: " + file);//    /data/user/0/com.bing.lan.comm/cache/cropped

        Crop.of(source, destination)/*.asSquare()*/.start(mContext);
    }

    //拍照
    private void handleCapture(final ImageView mImageView, Uri source, final int action) {
        if (source == null) {
            return;
        }

        //final File sourceFile = new File(source.getPath());
        final File sourceFile = getAbsolutePathFile(mContext, source);

        new Thread() {
            @Override
            public void run() {
                boolean isError = false;
                while (sourceFile.length() <= 0 && !isError) {
                    try {
                        Thread.sleep(300);
                        log.e("run(): 图片轮询中");
                    } catch (Exception e) {
                        isError = true;
                        log.e("run():  " + e.getLocalizedMessage());
                    }
                }

                if (!isError) {
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            checkPhotoSize(mImageView, sourceFile, action);
                        }
                    });
                } else {
                    log.e("run(): 出错了 ");
                }
            }
        }.start();

        //if (mProgressDrawable == null) {
        //    mProgressDrawable = new ProgressDrawable();
        //    mProgressDrawable.setColor(0xff666666);
        //
        //    float ratio = 0.2f;
        //    float scaleWidth = mProgressDrawable.getIntrinsicWidth() * ratio;
        //    float scaleHeight = mProgressDrawable.getIntrinsicHeight() * ratio;
        //    mProgressDrawable.setBounds(0, 0, (int) scaleWidth, (int) scaleHeight);
        //
        //    mScaleDrawable = new ScaleDrawable(mProgressDrawable, Gravity.CENTER, scaleWidth, scaleHeight);
        //    mProgressDrawable.setLevel(1);
        //
        //    //mImageView.getBackground()
        //}
        //mImageView.setImageDrawable(mProgressDrawable );
        //mProgressDrawable.start();

        mImageView.setImageResource(R.drawable.loading_progress);
        final Animatable animatable = (Animatable) mImageView.getDrawable();
        if (animatable != null) {
            animatable.start();
        }
    }

    //相册选照片
    private void handlePick(ImageView mImageView, Uri source, int action) {

        if (source == null) {
            Toast.makeText(AppUtil.getAppContext(), "请选择一张照片", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = getAbsolutePathFile(mContext, source);
        checkPhotoSize(mImageView, file, action);
    }

    private void handleCrop(ImageView mImageView, int resultCode, Intent result) {

        if (resultCode == RESULT_OK) {

            Uri uri = Crop.getOutput(result);
            File file = getAbsolutePathFile(mContext, uri);
            checkPhotoSize(mImageView, file, ACTION_OTHER);
        } else if (resultCode == Crop.RESULT_ERROR) {

            Toast.makeText(mContext, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPhotoSize(@NonNull final ImageView mImageView, final @NonNull File sourceFile, final int action) {

        // TODO: 2017/8/19 700 600 尺寸不对
        BitmapUtil.BitmapResult bitmapResult = BitmapUtil.decodeSampledBitmapFromFile(sourceFile.getAbsolutePath(),
                mImageView.getWidth(), mImageView.getHeight());
        final Bitmap bitmap = bitmapResult.bitmap;
        // mImageView.getMeasuredWidth(), mImageView.getMeasuredHeight());

        // final Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromFile(sourceFile.getAbsolutePath(), 700, 600);
        mImageView.setImageBitmap(bitmap);
        calculateBitmapSize(bitmap);

        if (mProgressDrawable != null && mProgressDrawable.isRunning()) {
            mProgressDrawable.stop();
        }

        long fileSize = 0;
        try {
            fileSize = FileUtil.getFileSize(sourceFile);
        } catch (Exception e) {
            log.e("checkPhotoSize():  " + e.getLocalizedMessage());
        }
        if (fileSize > maxSize) {
            //压缩
            // TODO: 2017/7/17  用rxjava 替代

            new Thread() {
                @Override
                public void run() {
                    File newFile = null;

                    //相册选中的照片 压缩后保存路径更改
                    if (action == ACTION_ALBUM) {
                        newFile = createImageFile(mContext, "small");
                    }

                    // 拍照后的照片 按原路径 加后缀
                    if (newFile == null) {
                        String path = sourceFile.getAbsolutePath();
                        String substring = path.substring(0, path.lastIndexOf("."));
                        String newPath = substring + "_small.jpg";
                        newFile = new File(newPath);
                        log.e("run(): newFile : " + newFile.toString());
                    }

                    // 质量压缩  bitmap-->文件 此时内存中显示仍然 未压缩的bitmap
                    BitmapUtil.compressImage(bitmap, newFile, (int) (maxSize / 1024));//单位 kb
                    calculateBitmapSize(bitmap);
                    returnResult(mImageView, newFile);
                }
            }.start();
        } else {
            calculateBitmapSize(bitmap);
            returnResult(mImageView, sourceFile);
        }
    }

    private void calculateBitmapSize(Bitmap bitmap) {
        long bitmapSize = BitmapUtil.getBitmapSize(bitmap);
        String fileSize = Formatter.formatFileSize(AppUtil.getAppContext(), bitmapSize / 8);
        log.e("calculateBitmapSize(): 图片bitmap的大小:" + fileSize);
    }

    private synchronized void returnResult(final ImageView mImageView, final File sourceFile) {

        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //   mPhotoImageView.setImageBitmap(thumbnail);
                //回调
                if (mPhotoSelectListener != null) {
                    mPhotoSelectListener.onPhotoSelect(mImageView, sourceFile);
                }

                // 最后通知图库更新
                mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(sourceFile)));
            }
        });
    }

    // 创建图片路径
    public static File createImageFile(Context context, String str) {
        String timeStamp = new SimpleDateFormat("MMdd_HHmmss").format(new Date());//yyyyMMdd_
        String imageFileName = "J_" + timeStamp;
        if (str != null) {
            imageFileName += "_" + str + "_";
        }

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
            log1.e("createImageFile():  ", e);

            File cacheDir = new File(context.getCacheDir(), "pic");
            if (!cacheDir.mkdirs() && (!cacheDir.exists() || !cacheDir.isDirectory())) {
                return null;
            }
            image = new File(cacheDir, imageFileName + ".jpg");
        }

        log1.e("createImageFile() 图片路径: " + image);
        return image;
    }

    // 相册选择
    public void selectPhotoFromAlbum(ImageView imageView) {
        if (imageView == null) {
            Toast.makeText(mContext, "ImageView不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        mPhotoImageView = imageView;

        //mPhotoImageView.setImageDrawable(null);
        //Crop.pickImage(mContext);

        //调用相册
        //Intent intent1 = new Intent(Intent.ACTION_PICK,
        //        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mContext.startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    public static File getAbsolutePathFile(final Context context, final Uri uri) {
        return GetPathUtil.getFilePathByUri(context, uri);
    }
}
