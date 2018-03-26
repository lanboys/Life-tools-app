package com.bing.lan.comm.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bing.lan.comm.R;
import com.bing.lan.comm.utils.LogUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by win7 on 2017/5/17.
 */
public class MyToolbar extends Toolbar implements View.OnClickListener {

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    OnTitleClickListener mClickListener;
    private TextView mToolBarTitle;
    private TextView mTvLeftTitle;
    private TextView mTvRightTitle;
    private RelativeLayout mRlContainer;

    public MyToolbar(Context context) {
        this(context, null);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public float px2dp(float pxValue) {
        return (pxValue / Resources.getSystem().getDisplayMetrics().density);
    }

    public int dp2px(int dip) {
        return (int) (dip * Resources.getSystem().getDisplayMetrics().density + .5f);
    }

    public void setTitleClickListener(OnTitleClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void initView(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_toolbar_layout, this);
        mRlContainer = (RelativeLayout) findViewById(R.id.toolbar_container);
        mToolBarTitle = (TextView) findViewById(R.id.toolbar_title);

        mToolBarTitle.setOnClickListener(this);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyToolbar);
            String toolbar_title = a.getString(R.styleable.MyToolbar_toolbar_title);
            int toolbar_title_gravity = a.getInt(R.styleable.MyToolbar_toolbar_title_gravity, -1);
            boolean toolbar_title_centerInParent = a.getBoolean(R.styleable.MyToolbar_toolbar_title_centerInParent, false);
            float toolbar_title_marginLeft = a.getDimension(R.styleable.MyToolbar_toolbar_title_marginLeft, -1.0f);
            float toolbar_title_marginRight = a.getDimension(R.styleable.MyToolbar_toolbar_title_marginRight, -1.0f);

            if (toolbar_title_gravity != -1) {
                mToolBarTitle.setGravity(toolbar_title_gravity);
            }

            if (toolbar_title != null) {
                mToolBarTitle.setText(toolbar_title);
            }
            if (toolbar_title_centerInParent) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mToolBarTitle.getLayoutParams();
                params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            }
            if (toolbar_title_marginLeft != -1.0f) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mToolBarTitle.getLayoutParams();
                params.setMargins((int) toolbar_title_marginLeft, 0, 0, 0);
            }
            if (toolbar_title_marginRight != -1.0f) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mToolBarTitle.getLayoutParams();
                params.setMargins(0, 0, (int) toolbar_title_marginRight, 0);
            }

            a.recycle();
        }
    }

    public TextView getToolBarTitle() {
        return mToolBarTitle;
    }

    public void setToolBarTitleText(@Nullable String toolBarTitle) {
        if (mToolBarTitle != null) {
            mToolBarTitle.setText(toolBarTitle);
        }
    }

    public void setToolBarTitleGravity(int gravity) {
        mToolBarTitle.setGravity(gravity);
    }

    public void addChildView(View child, ViewGroup.LayoutParams params) {
        if (mRlContainer != null) {
            mRlContainer.addView(child, params);
        }
    }

    public TextView getToolBarLeftTitle() {
        return mTvLeftTitle;
    }

    public TextView getToolBarRightTitle() {
        return mTvRightTitle;
    }

    public void setToolBarLeftTitleTextSize(float textSizeDp) {
        if (mTvLeftTitle != null) {
            mTvLeftTitle.setTextSize(textSizeDp);//不填写单位，默认是dp
        }
    }

    public void setToolBarRightTitleTextSize(float textSizeDp) {
        if (mTvRightTitle != null) {
            mTvRightTitle.setTextSize(textSizeDp);//不填写单位，默认是dp
        }
    }

    public TextView setToolBarLeftTitle(@NonNull String leftTitle) {
        if (mTvLeftTitle == null) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            //如果需要取消
            //layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 0);
            mTvLeftTitle = new TextView(getContext());
            float textSize = mToolBarTitle.getTextSize();
            mTvLeftTitle.setTextSize(px2dp(textSize) - 5);//不填写单位，默认是dp
            mTvLeftTitle.setTextColor(mToolBarTitle.getTextColors());
            mTvLeftTitle.setGravity(Gravity.CENTER);
            mTvLeftTitle.setOnClickListener(this);
            mRlContainer.addView(mTvLeftTitle, layoutParams);
        }
        mTvLeftTitle.setText(leftTitle);
        return mTvLeftTitle;
    }

    /**
     * 设置toolbar右边的标题和图片
     *
     * @param rightTitle 右边标题  可以只设置图片,标题为  ""
     * @param resId      图片
     */
    public TextView setToolBarRightTitle(@NonNull String rightTitle, @DrawableRes int resId) {
        if (mTvRightTitle == null) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            // layoutParams.setMargins(0, 0, dp2px(13), 0);
            layoutParams.rightMargin = dp2px(13);

            mTvRightTitle = new TextView(getContext());
            float textSize = mToolBarTitle.getTextSize();
            mTvRightTitle.setTextSize(px2dp(textSize) - 5);//不填写单位，默认是dp
            mTvRightTitle.setTextColor(mToolBarTitle.getTextColors());
            mTvRightTitle.setGravity(Gravity.CENTER);
            mTvRightTitle.setOnClickListener(this);
            mRlContainer.addView(mTvRightTitle, layoutParams);
        }
        setTextViewDrawableRight(mTvRightTitle, resId);
        mTvRightTitle.setText(rightTitle);
        return mTvRightTitle;
    }

    private void setTextViewDrawableRight(TextView textView, @DrawableRes int resId) {

        if (textView != null && resId != 0) {
            Drawable drawable = getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, null, drawable, null);
        }
    }

    private void setTextViewDrawableLeft(TextView textView, @DrawableRes int resId) {

        if (textView != null && resId != 0) {
            Drawable drawable = getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
        }
    }

    @Override
    public void onClick(View v) {
        if (mClickListener == null) {
            return;
        }
        if (v == mTvRightTitle) {
            mClickListener.onTitleClick(v, TitleType.RIGHT_TITLE);
        } else if (v == mToolBarTitle) {
            mClickListener.onTitleClick(v, TitleType.TITLE);
        } else if (v == mTvLeftTitle) {
            mClickListener.onTitleClick(v, TitleType.LEFT_TITLE);
        }
    }

    public interface OnTitleClickListener {

        void onTitleClick(View view, @TitleType.Type int titleType);
    }

    public static class TitleType {

        public static final int TITLE = 0;         //标题
        public static final int LEFT_TITLE = 1;       //左标题
        public static final int RIGHT_TITLE = 2;        //右标题

        @IntDef({TITLE, LEFT_TITLE, RIGHT_TITLE})
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {

        }
    }

    //    switch (titleType) {
    //     case MyToolbar.TitleType.TITLE:
    //         break;
    //     case MyToolbar.TitleType.LEFT_TITLE:
    //         break;
    //     case MyToolbar.TitleType.RIGHT_TITLE:
    //         break;
    // }
}
