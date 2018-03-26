package com.mjn.libs.view.pullRefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.mjn.libs.R;

/**
 * Created by 蓝兵 on 2018/3/23.
 */

public class BaoGaoLoadingLayout extends LoadingLayoutCopy {

    static final int ROTATION_ANIMATION_DURATION = 1200;

    private final Animation mRotateAnimation;

    private boolean mRotateDrawableWhilePulling = true;

    CircleProgressDrawable circleProgressDrawable;
    private int max_progress = 95;

    public BaoGaoLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);

        if (attrs != null) {
            mRotateDrawableWhilePulling = attrs.getBoolean(R.styleable.PullToRefresh_ptrRotateDrawableWhilePulling, true);
        }

        mRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);

        //FrameLayout parent = (FrameLayout) mHeaderImage.getParent();
        //ImageView imageView = new ImageView(context);
        //FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
        //        FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        ////imageView.setLayoutParams(layoutParams);
        ////parent.addView(imageView);
        //parent.addView(imageView,0,layoutParams);
        //imageView.setImageResource(R.drawable.loading_icon);

        circleProgressDrawable = new CircleProgressDrawable(getResources().getColor(R.color.loading_color));
        //mHeaderImage.setBackgroundResource(R.drawable.loading_icon);
        mHeaderImage.setImageDrawable(circleProgressDrawable);

        switch (mode) {
            case PULL_FROM_END:
                // Load in labels
                mPullLabel = "上拉更多..";
                mRefreshingLabel = "载入中..";
                mReleaseLabel = "松开后刷新..";
                break;

            case PULL_FROM_START:
            default:
                // Load in labels
                mPullLabel = "下拉刷新..";
                mRefreshingLabel = "载入中..";
                mReleaseLabel = "松开后刷新..";
                break;
        }
    }

    public void onLoadingDrawableSet(Drawable imageDrawable) {
        //mHeaderImage.setImageDrawable(imageDrawable);
    }

    protected void onPullImpl(float scaleOfLayout) {
        float angle;
        if (mRotateDrawableWhilePulling) {
            angle = scaleOfLayout * 90f;
        } else {
            angle = Math.max(0f, Math.min(180f, scaleOfLayout * 360f - 180f));
        }
        int progress = (int) angle * 180 / 360;
        if (progress >= max_progress) {
            progress = max_progress;
        }
        circleProgressDrawable.setProgress(progress);

        Log.d("onPullImpl", "===========================");
        Log.d("onPullImpl", scaleOfLayout + "");
        Log.d("onPullImpl", angle + "");
        Log.d("onPullImpl", progress + "");
    }

    @Override
    protected void refreshingImpl() {
        circleProgressDrawable.setProgress(max_progress);
        mHeaderImage.startAnimation(mRotateAnimation);
    }

    @Override
    protected void resetImpl() {
        mHeaderImage.clearAnimation();
    }

    @Override
    protected void pullToRefreshImpl() {
        // NO-OP
    }

    @Override
    protected void releaseToRefreshImpl() {
        // NO-OP
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {

    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.default_ptr_rotate;
    }
}

