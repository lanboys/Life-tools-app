package com.bing.lan.comm.popup;

/**
 * Author: 蓝兵
 * Email: lan_bing2013@163.com
 * Time: 2017/4/13  10:58
 */

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IntDef;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bing.lan.comm.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 分享选择
 */
public class SharePopupWindow extends BasePopupWindow implements View.OnClickListener {

    private TextView tv_share_wx;
    private TextView tv_share_fr;
    private TextView tv_share_sina;

    private OnItemClickListener mItemClickListener;

    public SharePopupWindow(Context context) {
        super(context);
        initView();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_share, null);

        tv_share_wx = (TextView) view.findViewById(R.id.tv_share_wx);
        tv_share_fr = (TextView) view.findViewById(R.id.tv_share_fr);
        tv_share_sina = (TextView) view.findViewById(R.id.tv_share_sina);

        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setAnimationStyle(R.style.PopupWindowAnimation);

        this.setFocusable(true);//获取焦点
        this.setOutsideTouchable(true);//点击pop外部关闭
        this.setBackgroundDrawable(new ColorDrawable(0xb0000000));

        tv_share_wx.setOnClickListener(this);
        tv_share_fr.setOnClickListener(this);
        tv_share_sina.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_share_wx) {
            itemCallBack(PopupItemType.SHARE_WX);
        } else if (i == R.id.tv_share_fr) {
            itemCallBack(PopupItemType.SHARE_FR);
        } else if (i == R.id.tv_share_sina) {
            itemCallBack(PopupItemType.SHARE_SINA);
        } else {
        }

        dismiss();
    }

    private void itemCallBack(int type) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClickListener(type);
        }
    }

    public interface OnItemClickListener {

        void onItemClickListener(@PopupItemType.Type int type);
    }

    /**
     * 选择 分享的类型
     */
    public static class PopupItemType {

        public static final int SHARE_WX = 0;         // 微信
        public static final int SHARE_FR = 1;      // 朋友圈
        public static final int SHARE_SINA = 2;             // 新浪

        @IntDef({SHARE_WX, SHARE_FR, SHARE_SINA})
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {

        }
    }
}
