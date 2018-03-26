package com.bing.lan.comm.rxTools;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.bing.lan.comm.R;
import com.bing.lan.comm.utils.LogUtil;

import java.util.ArrayList;
import java.util.Collection;

public class RxShoppingView extends View {

    protected final static int STATE_NONE = 0;
    protected final static int STATE_MOVING = 1;
    protected final static int STATE_MOVE_OVER = 2;
    protected final static int STATE_ROTATING = 3;
    protected final static int STATE_ROTATE_OVER = 4;

    protected final static int DEFAULT_DURATION = 250;
    protected final static String DEFAULT_SHOPPING_TEXT = "加入购物车";
    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    protected Paint mPaintBg, mPaintText, mPaintNum;
    protected Paint mPaintMinus;
    //是否是向前状态（= = 名字不好取，意思就是区分向前和回退状态）
    protected boolean mIsBack = true;
    //动画时长
    protected int mDuration;
    //购买数量
    protected int mNum = 0;
    //展示文案
    protected String mShoppingText;
    //当前状态
    protected int mState = STATE_NONE;
    //属性值
    protected int mWidth = 0;
    protected int mAngle = 0;
    protected int mTextPosition = 0;
    protected int mMinusBtnPosition = 0;
    protected int mAlpha = 0;
    protected int MAX_WIDTH;
    protected int MAX_HEIGHT;
    protected int PADDING = 10;//为按钮变大变小动画而设置
    protected ShoppingClickListener mShoppingClickListener;
    protected float mAddScale = 1.0f;
    protected float mMinusScale = 1.0f;

    public RxShoppingView(Context context) {
        this(context, null);
    }

    public RxShoppingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RxShoppingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    protected void init(AttributeSet attrs) {

        TypedArray typeArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.ShoppingView);
        mDuration = typeArray.getInt(R.styleable.ShoppingView_sv_duration, DEFAULT_DURATION);
        mShoppingText = TextUtils.isEmpty(typeArray.getString(R.styleable.ShoppingView_sv_text)) ? DEFAULT_SHOPPING_TEXT : typeArray.getString(R.styleable.ShoppingView_sv_text);
        //展示文案大小
        int textSize = (int) typeArray.getDimension(R.styleable.ShoppingView_sv_text_size, sp2px(16));
        //背景色
        int bgColor = typeArray.getColor(R.styleable.ShoppingView_sv_bg_color, ContextCompat.getColor(getContext(), android.R.color.holo_blue_light));
        typeArray.recycle();

        mPaintBg = new Paint();
        mPaintBg.setColor(bgColor);
        mPaintBg.setStyle(Paint.Style.FILL);
        mPaintBg.setAntiAlias(true);
        mPaintMinus = new Paint();
        mPaintMinus.setColor(bgColor);
        mPaintMinus.setStyle(Paint.Style.STROKE);
        mPaintMinus.setAntiAlias(true);
        mPaintMinus.setStrokeWidth(textSize / 6);
        mPaintText = new Paint();
        mPaintText.setColor(Color.WHITE);
        mPaintText.setStrokeWidth(textSize / 6);
        mPaintText.setTextSize(textSize);
        mPaintText.setAntiAlias(true);
        mPaintNum = new Paint();
        mPaintNum.setColor(Color.BLACK);
        mPaintNum.setTextSize(textSize / 3 * 4);
        mPaintNum.setStrokeWidth(textSize / 6);
        mPaintNum.setAntiAlias(true);

        MAX_WIDTH = getTextWidth(mPaintText, mShoppingText) / 5 * 8;
        MAX_HEIGHT = textSize * 2;

        if (MAX_WIDTH / (float) MAX_HEIGHT < 3.5) {
            MAX_WIDTH = (int) (MAX_HEIGHT * 3.5);
        }

        mTextPosition = MAX_WIDTH / 2;
        mMinusBtnPosition = MAX_HEIGHT / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MAX_WIDTH + PADDING, MAX_HEIGHT + PADDING);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mState == STATE_NONE) {
            drawBgMove(canvas);
            drawShoppingText(canvas);
        } else if (mState == STATE_MOVING) {
            drawBgMove(canvas);
        } else if (mState == STATE_MOVE_OVER) {
            // mState = STATE_ROTATING;
            if (mIsBack) {
                drawAddBtn(canvas);
                startRotateAnim();
            } else {
                drawBgMove(canvas);
                drawShoppingText(canvas);
                mState = STATE_NONE;
                mIsBack = true;
                mNum = 0;
            }
        } else if (mState == STATE_ROTATING) {
            mPaintMinus.setAlpha(mAlpha);
            mPaintNum.setAlpha(mAlpha);
            drawMinusBtn(canvas, mAngle);
            drawNumText(canvas);
            drawAddBtn(canvas);
        } else if (mState == STATE_ROTATE_OVER) {
            drawMinusBtn(canvas, mAngle);
            drawNumText(canvas);
            drawAddBtn(canvas);
            if (!mIsBack) {
                startMoveAnim();
            }
        }
    }

    public int[] getStartLocation() {
        int[] start_location = new int[2];
        getLocationInWindow(start_location);

        // int[] location = new int[2];
        // getLocationOnScreen(location);
        // Log.e("getStartLocation()", "----location[0]----" + location[0] + "----");
        // Log.e("getStartLocation()", "----location[1]----" + location[1] + "----");
        // Log.e("getStartLocation()", "=========================");

        Log.e("getStartLocation()", "----start_location[0]----" + start_location[0] + "----");
        Log.e("getStartLocation()", "----start_location[1]----" + start_location[1] + "----");
        Log.e("getStartLocation()", "----MAX_WIDTH----" + MAX_WIDTH + "--getWidth--" + getWidth());
        Log.e("getStartLocation()", "----MAX_HEIGHT----" + MAX_HEIGHT + "--getHeight--" + getHeight());
        // 这一组是获取相对在它父亲里的坐标
        // http://www.cnblogs.com/rison13/p/5526105.html
        Log.e("getStartLocation()", "----getLeft()----" + getLeft() + "--  getRight()--" + getRight());
        Log.e("getStartLocation()", "----getTop()----" + getTop() + "--  getBottom()--" + getBottom());

        start_location[0] = start_location[0] + (MAX_WIDTH - MAX_HEIGHT);
        // start_location[1]+=MAX_HEIGHT/2;

        Log.e("getStartLocation()", "--------------------------------------------------");
        Log.e("getStartLocation()", "----start_location[0]----" + start_location[0] + "----");
        Log.e("getStartLocation()", "----start_location[1]----" + start_location[1] + "----");

        return start_location;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (mState == STATE_NONE) {
                    mNum = 1;
                    startMoveAnim();
                    if (mShoppingClickListener != null) {
                        mShoppingClickListener.onAddClick(this, mNum, getStartLocation());
                    }
                    return true;
                } else if (mState == STATE_ROTATE_OVER) {
                    if (isPointInCircle(new PointF(event.getX(), event.getY()), new PointF(MAX_WIDTH - MAX_HEIGHT / 2, MAX_HEIGHT / 2), MAX_HEIGHT / 2)) {
                        //添加
                        if (mNum > 0) {
                            mNum++;
                            // mIsBack = true;
                            if (mShoppingClickListener != null) {
                                mShoppingClickListener.onAddClick(this, mNum, getStartLocation());
                            }
                            startScaleAnim(true);
                            invalidate();
                            return true;
                        }
                    } else if (isPointInCircle(new PointF(event.getX(), event.getY()), new PointF(MAX_HEIGHT / 2, MAX_HEIGHT / 2), MAX_HEIGHT / 2)) {
                        //减少
                        if (mNum > 1) {
                            mNum--;
                            if (mShoppingClickListener != null) {
                                mShoppingClickListener.onMinusClick(this, mNum);
                            }
                            startScaleAnim(false);
                            invalidate();
                        } else {
                            if (mShoppingClickListener != null) {
                                mShoppingClickListener.onMinusClick(this, 0);
                            }
                            // mState = STATE_ROTATING;
                            mIsBack = false;
                            startRotateAnim();
                        }
                        return true;
                    }
                }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 绘制移动的背景
     *
     * @param canvas 画板
     */
    protected void drawBgMove(Canvas canvas) {
        canvas.drawArc(new RectF(mWidth, 0, mWidth + MAX_HEIGHT, MAX_HEIGHT), 90, 180, false, mPaintBg);
        canvas.drawRect(new RectF(mWidth + MAX_HEIGHT / 2, 0, MAX_WIDTH - MAX_HEIGHT / 2, MAX_HEIGHT), mPaintBg);
        canvas.drawArc(new RectF(MAX_WIDTH - MAX_HEIGHT, 0, MAX_WIDTH, MAX_HEIGHT), 180, 270, false, mPaintBg);
    }

    /**
     * 绘制购物车文案
     *
     * @param canvas 画板
     */
    protected void drawShoppingText(Canvas canvas) {
        canvas.drawText(mShoppingText, MAX_WIDTH / 2 - getTextWidth(mPaintText, mShoppingText) / 2f,
                MAX_HEIGHT / 2 + getTextHeight(mShoppingText, mPaintText) / 2f, mPaintText);
    }

    /**
     * 绘制加号按钮
     *
     * @param canvas 画板
     */
    protected void drawAddBtn(Canvas canvas) {

        float radius = MAX_HEIGHT / 2;
        radius *= mAddScale;

        canvas.drawCircle(MAX_WIDTH - MAX_HEIGHT / 2, MAX_HEIGHT / 2, radius, mPaintBg);
        canvas.drawLine(MAX_WIDTH - MAX_HEIGHT / 2, MAX_HEIGHT / 4, MAX_WIDTH - MAX_HEIGHT / 2, MAX_HEIGHT / 4 * 3, mPaintText);
        canvas.drawLine(MAX_WIDTH - MAX_HEIGHT / 2 - MAX_HEIGHT / 4, MAX_HEIGHT / 2, MAX_WIDTH - MAX_HEIGHT / 4, MAX_HEIGHT / 2, mPaintText);
    }

    /**
     * 绘制减号按钮
     *
     * @param canvas 画板
     * @param angle  旋转角度
     */
    protected void drawMinusBtn(Canvas canvas, float angle) {
        if (angle != 0) {
            canvas.rotate(angle, mMinusBtnPosition, MAX_HEIGHT / 2);
        }
        float radius = MAX_HEIGHT / 2 - MAX_HEIGHT / 20;
        radius *= mMinusScale;

        canvas.drawCircle(mMinusBtnPosition, MAX_HEIGHT / 2, radius, mPaintMinus);
        canvas.drawLine(mMinusBtnPosition - MAX_HEIGHT / 4, MAX_HEIGHT / 2,
                mMinusBtnPosition + MAX_HEIGHT / 4, MAX_HEIGHT / 2, mPaintMinus);
        if (angle != 0) {
            canvas.rotate(-angle, mMinusBtnPosition, MAX_HEIGHT / 2);
        }
    }

    /**
     * 绘制购买数量
     *
     * @param canvas 画板
     */
    protected void drawNumText(Canvas canvas) {
        drawText(canvas, String.valueOf(mNum), mTextPosition - getTextWidth(mPaintNum, String.valueOf(mNum)) / 2f, MAX_HEIGHT / 2 + getTextHeight(String.valueOf(mNum), mPaintNum) / 2f, mPaintNum, mAngle);
    }

    /**
     * 绘制Text带角度
     *
     * @param canvas 画板
     * @param text   文案
     * @param x      x坐标
     * @param y      y坐标
     * @param paint  画笔
     * @param angle  旋转角度
     */
    protected void drawText(Canvas canvas, String text, float x, float y, Paint paint, float angle) {
        if (angle != 0) {
            canvas.rotate(angle, x, y);
        }
        canvas.drawText(text, x, y, paint);
        if (angle != 0) {
            canvas.rotate(-angle, x, y);
        }
    }

    /**
     * 开始移动动画
     */
    protected void startScaleAnim(final boolean isAdd) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 1.2f, 1f);

        valueAnimator.setDuration(mDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (isAdd) {
                    mAddScale = (Float) valueAnimator.getAnimatedValue();
                    Log.e("startScaleAnim", mAddScale + "");
                } else {
                    mMinusScale = (Float) valueAnimator.getAnimatedValue();
                    Log.e("startScaleAnim", mMinusScale + "");
                }

                invalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 开始移动动画
     */
    protected void startMoveAnim() {
        mState = STATE_MOVING;
        ValueAnimator valueAnimator;
        final int move = MAX_WIDTH - MAX_HEIGHT;
        if (mIsBack) {
            valueAnimator = ValueAnimator.ofInt(0, move);
        } else {
            valueAnimator = ValueAnimator.ofInt(move, 0);
        }
        valueAnimator.setDuration(mDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mWidth = (Integer) valueAnimator.getAnimatedValue();

                if (mIsBack) {
                    if (mWidth == move) {
                        mState = STATE_MOVE_OVER;
                    }
                } else {
                    if (mWidth == 0) {
                        mState = STATE_MOVE_OVER;
                    }
                }

                invalidate();
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                log.d("onAnimationStart(): startMoveAnim");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                log.d("onAnimationEnd(): startMoveAnim");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                log.d("onAnimationCancel(): startMoveAnim");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                log.d("onAnimationRepeat(): startMoveAnim");
            }
        });
        valueAnimator.start();
    }

    /**
     * 开始旋转动画
     */
    protected void startRotateAnim() {
        mState = STATE_ROTATING;

        Collection<Animator> animatorList = new ArrayList<>();

        ValueAnimator animatorTextRotate;
        if (mIsBack) {
            animatorTextRotate = ValueAnimator.ofInt(0, 360);
        } else {
            animatorTextRotate = ValueAnimator.ofInt(360, 0);
        }
        animatorTextRotate.setDuration(mDuration);
        animatorTextRotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mAngle = (Integer) valueAnimator.getAnimatedValue();

                if (mIsBack) {
                    if (mAngle == 360) {
                        mState = STATE_ROTATE_OVER;
                    }
                } else {
                    if (mAngle == 0) {
                        mState = STATE_ROTATE_OVER;
                    }
                }
            }
        });

        animatorList.add(animatorTextRotate);

        ValueAnimator animatorAlpha;
        if (mIsBack) {
            animatorAlpha = ValueAnimator.ofInt(0, 255);
        } else {
            animatorAlpha = ValueAnimator.ofInt(255, 0);
        }
        animatorAlpha.setDuration(mDuration);
        animatorAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mAlpha = (Integer) valueAnimator.getAnimatedValue();

                if (mIsBack) {
                    if (mAlpha == 255) {
                        mState = STATE_ROTATE_OVER;
                    }
                } else {
                    if (mAlpha == 0) {
                        mState = STATE_ROTATE_OVER;
                    }
                }
            }
        });

        animatorList.add(animatorAlpha);

        ValueAnimator animatorTextMove;
        if (mIsBack) {
            animatorTextMove = ValueAnimator.ofInt(MAX_WIDTH - MAX_HEIGHT / 2, MAX_WIDTH / 2);
        } else {
            animatorTextMove = ValueAnimator.ofInt(MAX_WIDTH / 2, MAX_WIDTH - MAX_HEIGHT / 2);
        }
        animatorTextMove.setDuration(mDuration);
        animatorTextMove.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mTextPosition = (Integer) valueAnimator.getAnimatedValue();

                if (mIsBack) {
                    if (mTextPosition == MAX_WIDTH / 2) {
                        mState = STATE_ROTATE_OVER;
                    }
                } else {
                    if (mTextPosition == MAX_WIDTH - MAX_HEIGHT / 2) {
                        mState = STATE_ROTATE_OVER;
                    }
                }
            }
        });

        animatorList.add(animatorTextMove);

        ValueAnimator animatorBtnMove;
        if (mIsBack) {
            animatorBtnMove = ValueAnimator.ofInt(MAX_WIDTH - MAX_HEIGHT / 2, MAX_HEIGHT / 2);
        } else {
            animatorBtnMove = ValueAnimator.ofInt(MAX_HEIGHT / 2, MAX_WIDTH - MAX_HEIGHT / 2);
        }
        animatorBtnMove.setDuration(mDuration);
        animatorBtnMove.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mMinusBtnPosition = (Integer) valueAnimator.getAnimatedValue();

                if (mIsBack) {
                    if (mMinusBtnPosition == MAX_HEIGHT / 2) {
                        mState = STATE_ROTATE_OVER;
                    }
                } else {
                    if (mMinusBtnPosition == MAX_WIDTH - MAX_HEIGHT / 2) {
                        mState = STATE_ROTATE_OVER;
                    }
                }

                invalidate();
            }
        });

        animatorList.add(animatorBtnMove);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(mDuration);
        animatorSet.playTogether(animatorList);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                log.d("onAnimationStart(): startRotateAnim");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                log.d("onAnimationEnd(): startRotateAnim");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                log.d("onAnimationCancel(): startRotateAnim");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                log.d("onAnimationRepeat(): startRotateAnim");
            }
        });
        animatorSet.start();
    }

    /**
     * 设置购买数量
     *
     * @param num 购买数量
     */
    public void setTextNum(int num) {
        mNum = num;
        if (mNum == 0) {
            mState = STATE_NONE;
            // mWidth = MAX_WIDTH - MAX_HEIGHT;
            // mIsBack = true;
        } else {
            mState = STATE_ROTATE_OVER;
        }

        invalidate();
    }

    public void setOnShoppingClickListener(ShoppingClickListener shoppingClickListener) {
        this.mShoppingClickListener = shoppingClickListener;
    }

    /**
     * 判断点是否在圆内
     *
     * @param pointF 待确定点
     * @param circle 圆心
     * @param radius 半径
     * @return true在圆内
     */
    protected boolean isPointInCircle(PointF pointF, PointF circle, float radius) {
        return Math.pow((pointF.x - circle.x), 2) + Math.pow((pointF.y - circle.y), 2) <= Math.pow(radius, 2);
    }

    protected int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    //获取Text高度
    protected int getTextHeight(String str, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return (int) (rect.height() / 33f * 29);
    }

    //获取Text宽度
    protected int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    public interface ShoppingClickListener {

        void onAddClick(View rxShoppingView, int num, int[] start_location);

        void onMinusClick(View rxShoppingView, int num);
    }
}
