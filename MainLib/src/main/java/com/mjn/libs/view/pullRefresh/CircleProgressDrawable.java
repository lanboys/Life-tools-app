package com.mjn.libs.view.pullRefresh;


import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class CircleProgressDrawable extends Drawable {
    private Paint mPaint;
    private RectF mArcRect;
    private int progress = 0;
    private int padding = 2;

    public CircleProgressDrawable(int color) {
        mPaint = new Paint();
        mPaint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        if (mArcRect == null) {
            //在view中的位置 左上 右下
            mArcRect = new RectF(padding, padding, getBounds().width() - padding, getBounds().width() - padding);
        }
        mPaint.setStyle(Paint.Style.STROKE);//描边
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStrokeWidth(4);//边宽度
        canvas.drawArc(mArcRect, -120, 360 * progress / 100, false, mPaint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
