package com.bing.lan.comm.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bing.lan.comm.R;

public class ProgressDialogUtil extends ProgressDialog {

    private ProgressBar pgb_progress;
    private TextView tv_message;

    // private String mMessage = "加载中...";
    private CharSequence mMessage = "";

    public ProgressDialogUtil(Context context) {
        super(context, R.style.ProgressDialogTheme);
    }

    public ProgressDialogUtil(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.progress_dialog);
        pgb_progress = (ProgressBar) findViewById(R.id.pgb_progress);
        tv_message = (TextView) findViewById(R.id.tv_message);

        tv_message.setVisibility(View.VISIBLE);
        tv_message.setText(mMessage);
    }

    /**
     * 设置内容
     *
     */
    public void setProgressMessage(CharSequence message) {
        super.setMessage(message);
        mMessage = message;
        if (tv_message != null) {
            tv_message.setText(mMessage);
        }
    }
}
