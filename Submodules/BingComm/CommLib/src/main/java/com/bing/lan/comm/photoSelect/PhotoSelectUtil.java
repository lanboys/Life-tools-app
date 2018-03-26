package com.bing.lan.comm.photoSelect;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

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

public class PhotoSelectUtil {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Activity mContext;
    private ImageView mImageView;
    private Uri mCurrentPhotoUri;
    private OnPhotoSelectListener mUploadListener;

    public PhotoSelectUtil(Activity context) {
        mContext = context;
    }

    public void setUploadListener(OnPhotoSelectListener uploadListener) {
        mUploadListener = uploadListener;
    }

    public void showSelectAvatarPopup(ImageView imageView) {

        if (imageView == null) {
            Toast.makeText(mContext, "ImageView不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mImageView = imageView;

        final PhotoSelectPopupWindow popupWindow = new PhotoSelectPopupWindow(mContext);
        popupWindow.setOnItemClickListener(new PhotoSelectPopupWindow.OnItemClickListener() {
            @Override
            public void onItemClickListener(@PhotoSelectSource int type) {
                if (type == PhotoSelectSource.SELECT_CAMERA) {
                    //拍照
                    dispatchTakePictureIntent();
                } else if (type == PhotoSelectSource.SELECT_ALBUM) {
                    //相册 选择
                    selectAvatarFromAlbum();
                }
            }
        });

        popupWindow.showAtLocation(mImageView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    // 拍照
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                //  Uri photoUri = FileProvider.getUriForFile(mContext, "com.yujingtravelagent.globaltrip.ui.mine.fileprovider", photoFile);

                Uri photoUri = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                mCurrentPhotoUri = photoUri;

                mContext.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    // 拍照返回
    public void onSelectActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                //系统拍照  界面返回
                beginCrop(mCurrentPhotoUri);
            } else if (requestCode == Crop.REQUEST_PICK) {
                //系统选择照片  界面返回
                beginCrop(data.getData());
            }
        }

        //裁剪图片 界面返回
        if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    //进入裁剪页面
    private void beginCrop(Uri source) {

        //Uri destination = Uri.fromFile(new File(mContext.getCacheDir(), "cropped"));
        //Crop.of(source, destination).asSquare().start(mContext);

        mImageView.setImageDrawable(null);
        mImageView.setImageURI(source);

        File file = new File(source.getPath());

        //回调
        if (mUploadListener != null) {
            mUploadListener.onPhotoSelect(mImageView, file);
        }
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            mImageView.setImageDrawable(null);

            Uri uri = Crop.getOutput(result);
            File file = new File(uri.getPath());

            mImageView.setImageURI(uri);

            //回调
            if (mUploadListener != null) {
                mUploadListener.onPhotoSelect(mImageView, file);
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(mContext, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // 创建图片路径
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",             /* suffix */
                storageDir          /* directory */
        );

        return image;
    }

    // 选择头像
    private void selectAvatarFromAlbum() {
        //mImageView.setImageDrawable(null);
        Crop.pickImage(mContext);
    }
}
