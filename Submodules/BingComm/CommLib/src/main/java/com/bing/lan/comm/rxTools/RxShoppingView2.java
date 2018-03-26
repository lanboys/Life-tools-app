package com.bing.lan.comm.rxTools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class RxShoppingView2 extends RxShoppingView {

    public RxShoppingView2(Context context) {
        super(context);
    }

    public RxShoppingView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RxShoppingView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mState == STATE_NONE) {//修改
                    if (isPointInCircle(new PointF(event.getX(), event.getY()), new PointF(MAX_WIDTH - MAX_HEIGHT / 2, MAX_HEIGHT / 2), MAX_HEIGHT / 2)) {
                        mNum = 1;
                        startMoveAnim();
                        startScaleAnim(true);
                        if (mShoppingClickListener != null) {
                            mShoppingClickListener.onAddClick(this, mNum,  getStartLocation());
                        }
                    }
                    return true;
                }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 绘制移动的背景
     *
     * @param canvas 画板
     */
    @Override
    protected void drawBgMove(Canvas canvas) {
        // canvas.drawArc(new RectF(mWidth, 0, mWidth + MAX_HEIGHT, MAX_HEIGHT), 90, 180, false, mPaintBg);
        // canvas.drawRect(new RectF(mWidth + MAX_HEIGHT / 2, 0, MAX_WIDTH - MAX_HEIGHT / 2, MAX_HEIGHT), mPaintBg);
        // canvas.drawArc(new RectF(MAX_WIDTH - MAX_HEIGHT, 0, MAX_WIDTH, MAX_HEIGHT), 180, 270, false, mPaintBg);
        drawAddBtn(canvas);//修改
    }

    /**
     * 绘制购物车文案
     *
     * @param canvas 画板
     */
    protected void drawShoppingText(Canvas canvas) {
        // canvas.drawText(mShoppingText, MAX_WIDTH / 2 - getTextWidth(mPaintText, mShoppingText) / 2f,
        //         MAX_HEIGHT / 2 + getTextHeight(mShoppingText, mPaintText) / 2f, mPaintText);
    }
}
