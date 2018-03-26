package com.bing.lan.comm.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlowLayout extends ViewGroup implements View.OnClickListener {

    LineFillProvider mLineFillProvider = new LineFillProvider() {
        @Override
        public boolean fillLine(int allLineSize, int lineIndex, int lineChildAllSize, int childIndex, float avg) {
            return lineIndex != allLineSize - 1;
        }
    };
    ColorProvider mDefaultColorProvider = new ColorProvider() {
        Random random = new Random();
        int[] color = new int[4];

        @Override
        public int textColor(int position) {
            return Color.BLUE;
        }

        @Override
        public int normalBackgroundColor(int position) {
            color[0] = 255;
            color[1] = random.nextInt(190) + 30;
            color[2] = random.nextInt(190) + 30;
            color[3] = random.nextInt(190) + 30;
            return Color.argb(color[0], color[1], color[2], color[3]);
        }

        @Override
        public int normalStrokeColor(int position) {
            color[0] = 255;
            color[1] = random.nextInt(190) + 30;
            color[2] = random.nextInt(190) + 30;
            color[3] = random.nextInt(190) + 30;
            return Color.argb(color[0], color[1], color[2], color[3]);
        }

        @Override
        public int selectBackgroundColor(int position) {
            color[0] = 255;
            color[1] = random.nextInt(190) + 30;
            color[2] = random.nextInt(190) + 30;
            color[3] = random.nextInt(190) + 30;
            return Color.argb(color[0], color[1], color[2], color[3]);
        }

        @Override
        public int selectStrokeColor(int position) {
            color[0] = 255;
            color[1] = random.nextInt(190) + 30;
            color[2] = random.nextInt(190) + 30;
            color[3] = random.nextInt(190) + 30;
            return Color.argb(color[0], color[1], color[2], color[3]);
        }
    };
    private List<Line> mLines = new ArrayList<>(); // 用来记录描述有多少行View
    private int mHorizontalSpace = 10;
    private int mVerticalSpace = 10;
    private OnFlowItemClickListener listener;
    private List<FlowBean> mFlowBeanList = new ArrayList<>();
    private int mBackgroundRadius = dp2px(8);
    private int mTextSize = dp2px(8);

    private int left = dp2px(2);
    private int top = dp2px(2);
    private int right = dp2px(2);
    private int bottom = dp2px(2);

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public FlowLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public int dp2px(int dip) {
        // denstity*dip=px;
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dip * density + .5f);
    }

    public int px2dp(int px) {
        // denstity*dip=px;
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (px / density + .5f);
    }

    public void setLineFillProvider(LineFillProvider lineFillProvider) {
        mLineFillProvider = lineFillProvider;
    }

    public void setSpace(int horizontalSpace, int verticalSpace) {
        this.mHorizontalSpace = horizontalSpace;
        this.mVerticalSpace = verticalSpace;
    }

    public void setBackgroundRadius(int backgroundRadius) {
        mBackgroundRadius = backgroundRadius;
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }

    public void setTextPadding(int textPadding) {
        setTextPadding(textPadding, textPadding, textPadding, textPadding);
    }

    public void setTextPadding(int left, int top) {
        setTextPadding(left, top, left, top);
    }

    public void setTextPadding(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 清空
        mLines.clear();
        Line currentLine = null;

        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);

        // 获取行最大的宽度
        int maxLineWidth = layoutWidth - getPaddingLeft() - getPaddingRight();

        // 测量孩子
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);

            // 如果孩子不可见
            if (view.getVisibility() == View.GONE) {
                continue;
            }

            // 测量孩子
            measureChild(view, widthMeasureSpec, heightMeasureSpec);

            // 往lines添加孩子
            if (currentLine == null) {
                // 说明还没有开始添加孩子
                currentLine = new Line(mLines.size(), maxLineWidth, mHorizontalSpace);

                // 添加到 Lines中
                mLines.add(currentLine);

                // 行中一个孩子都没有
                currentLine.addView(view);
            } else {
                // 行不为空,行中有孩子了
                boolean canAdd = currentLine.canAdd(view);
                if (canAdd) {
                    // 可以添加
                    currentLine.addView(view);
                } else {
                    // 不可以添加,装不下去
                    // 换行

                    // 新建行
                    currentLine = new Line(mLines.size(), maxLineWidth, mHorizontalSpace);
                    // 添加到lines中
                    mLines.add(currentLine);
                    // 将view添加到line
                    currentLine.addView(view);
                }
            }
        }

        // 设置自己的宽度和高度
        int measuredWidth = layoutWidth;
        // paddingTop + paddingBottom + 所有的行间距 + 所有的行的高度

        float allHeight = 0;
        for (int i = 0; i < mLines.size(); i++) {
            Line line = mLines.get(i);
            float mHeight = line.mHeight;

            // 加行高
            allHeight += mHeight;
            // 加间距
            if (i != 0) {
                allHeight += mVerticalSpace;
            }
        }

        int measuredHeight = (int) (allHeight + getPaddingTop() + getPaddingBottom() + 0.5f);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 给Child 布局---> 给Line布局

        int paddingLeft = getPaddingLeft();
        int offsetTop = getPaddingTop();
        int size = mLines.size();
        for (int i = 0; i < size; i++) {
            Line line = mLines.get(i);
            line.setAllLineSize(size);

            // 给行布局
            line.layout(paddingLeft, offsetTop);
            offsetTop += line.mHeight + mVerticalSpace;
        }
    }

    public void addAllChild(List<FlowBean> flowBeen) {
        removeAllViews();
        for (FlowBean flowBean : flowBeen) {
            addChild(flowBean);
        }
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();

        if (mFlowBeanList != null) {
            mFlowBeanList.clear();
        }
    }

    public void setSelectFlowItem(int selectPosition) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            view.setSelected(selectPosition == i);
        }
    }

    public void addChild(FlowBean flowBeen) {
        this.addView(createChild(flowBeen, getChildCount()));
    }

    // TODO: 2017/8/16 有bug 外部添加时插到中间，导致两个相邻颜色一致
    public void addChild(FlowBean flowBeen, int position) {
        this.addView(createChild(flowBeen, position), position);
    }

    public void setColorProvider(ColorProvider colorProvider) {
        mDefaultColorProvider = colorProvider;
    }

    private View createChild(FlowBean flowBean, int position) {

        mFlowBeanList.add(flowBean);
        CharSequence string = flowBean.getFlowText();

        TextView textView = new TextView(getContext());
        textView.setText(string);

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        textView.setGravity(Gravity.CENTER);
        int color = mDefaultColorProvider.textColor(position);
        if (color != 0) {
            textView.setTextColor(color);
        }
        textView.setPadding(left, top, right, bottom);

        //LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //textView.setLayoutParams(lp);
        //lp.width = 4;

        //正常时候的效果
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setCornerRadius(mBackgroundRadius);//圆角处的弧度
        color = mDefaultColorProvider.normalBackgroundColor(position);
        if (color != 0) {
            normalDrawable.setColor(color);
        }
        color = mDefaultColorProvider.normalStrokeColor(position);
        if (color != 0) {
            normalDrawable.setStroke(1, color);
        }

        //选中时候的效果
        GradientDrawable pressDrawable = new GradientDrawable();
        pressDrawable.setCornerRadius(mBackgroundRadius);
        color = mDefaultColorProvider.selectBackgroundColor(position);
        if (color != 0) {
            pressDrawable.setColor(color);
        }
        color = mDefaultColorProvider.selectStrokeColor(position);
        if (color != 0) {
            pressDrawable.setStroke(1, color);
        }

        //选择器
        StateListDrawable listDrawable = new StateListDrawable();

        listDrawable.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
        listDrawable.addState(new int[]{android.R.attr.state_selected}, pressDrawable);
        listDrawable.addState(new int[]{}, normalDrawable);

        //设置背景
        textView.setBackground(listDrawable);

        textView.setClickable(true);
        textView.setOnClickListener(this);
        textView.setTag(flowBean);
        textView.setContentDescription(position + "");

        return textView;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onFlowItemClick(this, v, Integer.valueOf(String.valueOf(v.getContentDescription())), (FlowBean) v.getTag());
        }
    }

    public void setOnItemClickListener(OnFlowItemClickListener listener) {
        this.listener = listener;
    }

    //用于控制某行某个view宽度是否增加剩余空间的平均值
    public interface LineFillProvider {

        /**
         * @param allLineSize      总行数
         * @param lineIndex        第几行
         * @param lineChildAllSize 每行总个数
         * @param childIndex       第几个数
         * @param avg              填充时,每个view 宽度增加值
         * @return true 表示当前行填充
         */
        boolean fillLine(int allLineSize, int lineIndex, int lineChildAllSize, int childIndex, float avg);
    }

    public interface ColorProvider {

        int textColor(int position);

        int normalBackgroundColor(int position);

        int normalStrokeColor(int position);

        int selectBackgroundColor(int position);

        int selectStrokeColor(int position);
    }

    public interface OnFlowItemClickListener {

        void onFlowItemClick(FlowLayout flowLayout, View view, int position, FlowBean flowBean);
    }

    public interface FlowBean {

        CharSequence getFlowText();
    }

    class Line {

        // 属性
        private List<View> mLineViews = new ArrayList<>();    // 用来记录每一行有几个View
        private float mMaxWidth;                            // 行最大的宽度
        private float mUsedWidth;                        // 已经使用了多少宽度
        private float mHeight;                            // 行的高度
        private float mMarginLeft;
        private float mMarginRight;
        private float mMarginTop;
        private float mMarginBottom;
        private float mHorizontalSpace;                    // View和view之间的水平间距
        private int lineIndex; //排在第几行
        private int allLineSize;//总行数

        // 构造
        public Line(int index, int maxWidth, int horizontalSpace) {
            this.lineIndex = index;
            this.mMaxWidth = maxWidth;
            this.mHorizontalSpace = horizontalSpace;
        }

        int getAllLineSize() {
            return allLineSize;
        }

        void setAllLineSize(int allLineSize) {
            this.allLineSize = allLineSize;
        }

        // 方法

        /**
         * 添加view，记录属性的变化
         *
         * @param view
         */
        public void addView(View view) {
            // 加载View的方法

            int size = mLineViews.size();
            int viewWidth = view.getMeasuredWidth();
            int viewHeight = view.getMeasuredHeight();
            // 计算宽和高
            if (size == 0) {
                // 说还没有添加View
                if (viewWidth > mMaxWidth) {
                    mUsedWidth = mMaxWidth;
                } else {
                    mUsedWidth = viewWidth;
                }
                mHeight = viewHeight;
            } else {
                // 多个view的情况
                mUsedWidth += viewWidth + mHorizontalSpace;
                mHeight = mHeight < viewHeight ? viewHeight : mHeight;
            }

            // 将View记录到集合中
            mLineViews.add(view);
        }

        /**
         * 用来判断是否可以将View添加到line中
         *
         * @param view
         * @return
         */
        public boolean canAdd(View view) {
            // 判断是否能添加View

            int size = mLineViews.size();

            if (size == 0) {
                return true;
            }

            int viewWidth = view.getMeasuredWidth();

            // 预计使用的宽度
            float planWidth = mUsedWidth + mHorizontalSpace + viewWidth;

            return planWidth <= mMaxWidth;
        }

        /**
         * 给孩子布局
         *
         * @param offsetLeft
         * @param offsetTop
         */
        public void layout(int offsetLeft, int offsetTop) {
            // 给孩子布局

            int currentLeft = offsetLeft;

            int size = mLineViews.size();
            // 判断已经使用的宽度是否小于最大的宽度
            float extra = 0;
            float widthAvg = 0;
            if (mMaxWidth > mUsedWidth) {
                extra = mMaxWidth - mUsedWidth;
                widthAvg = extra / size;
            }

            for (int i = 0; i < size; i++) {
                View view = mLineViews.get(i);
                int viewWidth = view.getMeasuredWidth();
                int viewHeight = view.getMeasuredHeight();

                // 判断是否有富余
                if (widthAvg != 0) {

                    if (mLineFillProvider.fillLine(allLineSize, lineIndex, size, i, widthAvg)) {
                        // 改变宽度
                        int newWidth = (int) (viewWidth + widthAvg - 0.5f);
                        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY);
                        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY);
                        view.measure(widthMeasureSpec, heightMeasureSpec);
                        viewWidth = view.getMeasuredWidth();
                        viewHeight = view.getMeasuredHeight();
                    }
                }

                // 布局
                int left = currentLeft;
                int top = (int) (offsetTop + (mHeight - viewHeight) / 2 +
                        0.5f);
                // int top = offsetTop;
                int right = left + viewWidth;
                int bottom = top + viewHeight;
                view.layout(left, top, right, bottom);

                currentLeft += viewWidth + mHorizontalSpace;
            }
        }
    }
}
