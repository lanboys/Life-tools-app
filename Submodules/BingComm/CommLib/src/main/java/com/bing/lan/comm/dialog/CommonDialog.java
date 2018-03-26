package com.bing.lan.comm.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bing.lan.comm.R;

/**
 * Created by Administrator on 2017/10/30.
 */

public class CommonDialog {
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvSure;
    private TextView tvCancel;
    private AlertDialog dialog;

    public CommonDialog(Context context) {
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvSure = (TextView) view.findViewById(R.id.tv_ok);
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        dialog = new AlertDialog.Builder(context).setView(view).create();
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    public void setCancelOutSide(boolean flag) {
        if (dialog != null)
            dialog.setCanceledOnTouchOutside(flag);
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void cancel() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    /**
     * title
     */
    public TextView getTitle() {
        return tvTitle;
    }

    public void setTitleText(String text) {
        if (tvTitle != null)
            tvTitle.setText(text);
    }

    public void setTitleTextSize(float size) {
        if (tvTitle != null)
            tvTitle.setTextSize(size);
    }

    public void setTitleTextColor(int color) {
        if (tvTitle != null)
            tvTitle.setTextColor(color);
    }

    public void setTitleTextBackground(int color) {
        if (tvTitle != null)
            tvTitle.setBackgroundColor(color);
    }

    public void setTitleTextBackground(Drawable drawable) {
        if (tvTitle != null)
            tvTitle.setBackground(drawable);
    }

    public void setTitleVisible(int visible) {
        tvTitle.setVisibility(visible);
    }

    /**
     * content
     */
    public TextView getContent() {
        return tvContent;
    }

    public void setContentText(String text) {
        if (tvContent != null)
            tvContent.setText(text);
    }

    public void setContentTextSize(float size) {
        if (tvContent != null)
            tvContent.setTextSize(size);
    }

    public void setContentTextColor(int color) {
        if (tvContent != null)
            tvContent.setTextColor(color);
    }

    public void setContentTextBackground(int color) {
        if (tvContent != null)
            tvContent.setBackgroundColor(color);
    }

    public void setContentTextBackground(Drawable drawable) {
        if (tvContent != null)
            tvContent.setBackground(drawable);
    }

    /**
     * sure
     */
    public TextView getSure() {
        return tvSure;
    }

    public void setSureText(String text) {
        if (tvSure != null)
            tvSure.setText(text);
    }

    public void setSureTextSize(float size) {
        if (tvSure != null)
            tvSure.setTextSize(size);
    }

    public void setSureTextColor(int color) {
        if (tvSure != null)
            tvSure.setTextColor(color);
    }

    public void setSureTextBackground(int color) {
        if (tvSure != null)
            tvSure.setBackgroundColor(color);
    }

    public void setSureTextBackground(Drawable drawable) {
        if (tvSure != null)
            tvSure.setBackground(drawable);
    }

    public void setSureOnClick(View.OnClickListener listener) {
        if (tvSure != null)
            tvSure.setOnClickListener(listener);
    }

    /**
     * cancel
     */
    public TextView getCancel() {
        return tvCancel;
    }

    public void setCancelText(String text) {
        if (tvCancel != null)
            tvCancel.setText(text);
    }

    public void setCancelTextSize(float size) {
        if (tvCancel != null)
            tvCancel.setTextSize(size);
    }

    public void setCancelTextColor(int color) {
        if (tvCancel != null)
            tvCancel.setTextColor(color);
    }

    public void setCancelTextBackground(int color) {
        if (tvCancel != null)
            tvCancel.setBackgroundColor(color);
    }

    public void setCancelTextBackground(Drawable drawable) {
        if (tvCancel != null)
            tvCancel.setBackground(drawable);
    }

    public void setCancelOnClick(View.OnClickListener listener) {
        if (tvCancel != null)
            dialog.cancel();
        tvCancel.setOnClickListener(listener);
    }
}
