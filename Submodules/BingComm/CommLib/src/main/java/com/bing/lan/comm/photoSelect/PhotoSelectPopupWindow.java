package com.bing.lan.comm.photoSelect;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.bing.lan.comm.R;

/**
 * 头像选择
 */
public class PhotoSelectPopupWindow extends PopupWindow implements View.OnClickListener {

    private Context mContext;
    private Button btn_take_photo;
    private Button btn_photo_album;
    private Button btn_cancel;

    private OnItemClickListener mItemClickListener;

    public PhotoSelectPopupWindow(Context context) {
        mContext = context;

        initView();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_photo_select, null);

        btn_take_photo = (Button) view.findViewById(R.id.btn_take_photo);
        btn_photo_album = (Button) view.findViewById(R.id.btn_photo_album);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        this.setAnimationStyle(R.style.PopupWindowAnimation);

        this.setFocusable(true);//获取焦点
        this.setOutsideTouchable(true);//点击pop外部关闭
        this.setBackgroundDrawable(new ColorDrawable(0xb0000000));

        btn_take_photo.setOnClickListener(this);
        btn_photo_album.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_take_photo) {
            itemCallBack(PhotoSelectSource.SELECT_CAMERA);
        } else if (i == R.id.btn_photo_album) {
            itemCallBack(PhotoSelectSource.SELECT_ALBUM);
        }

        if (isShowing()) {
            dismiss();
        }
    }

    private void itemCallBack(int type) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClickListener(type);
        }
    }

    public interface OnItemClickListener {

        void onItemClickListener(@PhotoSelectSource int type);
    }
}
