package com.bing.lan.comm.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bing.lan.comm.R;

/**
 * Created by 520 on 2017/7/12.
 */

public class SelectTimeLayout extends FrameLayout implements View.OnClickListener {

    private TextView mSelectTime1;
    private TextView mSelectTime2;
    private ImageView mImageViewDelete;

    public SelectTimeLayout(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public SelectTimeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_select_time_layout, this);

        mSelectTime1 = (TextView) findViewById(R.id.tv_select_time_1);
        mSelectTime2 = (TextView) findViewById(R.id.tv_select_time_2);
        mImageViewDelete = (ImageView) findViewById(R.id.iv_select_time_delete);

        mSelectTime1.setOnClickListener(this);
        mSelectTime2.setOnClickListener(this);
        mImageViewDelete.setOnClickListener(this);
    }

    OnTimeSelectClickListener mListener;

    public void setOnTimeSelectClickListener(OnTimeSelectClickListener listener) {
        mListener = listener;
    }

    public void setSelectTime1Text(CharSequence text) {
        mSelectTime1.setText(text);
    }

    public void setSelectTime2Text(CharSequence text) {
        mSelectTime2.setText(text);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            if (v.getId() == R.id.tv_select_time_1) {
                mListener.onTimeSelectClick(this, true);
            } else if (v.getId() == R.id.tv_select_time_2) {
                mListener.onTimeSelectClick(this, false);
            } else {
                setVisibility(GONE);
                mListener.onImgDeleteClick(this);
            }
        }
    }

    public interface OnTimeSelectClickListener {

        void onTimeSelectClick(View v, boolean isFirst);

        void onImgDeleteClick(View v);
    }
}
