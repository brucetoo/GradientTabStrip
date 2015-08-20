package com.brucetoo.gradienttabstrip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Bruce Too
 * On 8/20/15.
 * At 10:02
 * 没有对应在xml 中定义属性...
 */
public class ColorGradientView extends View {

    private Paint mTextPaint;
    //字体大小
    private int mTextSize = 30;
    //文本
    private String mText = "WHAT THE FUCK";
    //文本高度
    private int mTextHeight;
    //文本宽度
    private int mTextWidth;
    //文本范围
    private Rect mTextRect;
    //文本其实x坐标
    private int mTextStartX;
    //文本起始y坐标
    private int mTextStartY;
    //渐变的距离
    private float offset;
    //文本左边颜色
    private int mTextLeftColor = 0xffff0000;
    //文本右边颜色
    private int mTextRightColor = 0xff000000;
    //方向
    private static int DIRECTION_LEFT_TO_RIGHT = 0;
    private static int DIRECTION_RIGHT_TO_LEFT = 1;
    //默认方向
    private int mDirection = DIRECTION_LEFT_TO_RIGHT;

    public void setmDirection(int mDirection) {
        this.mDirection = mDirection;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
        invalidate();
    }

    public void setmTextLeftColor(int mTextLeftColor) {
        this.mTextLeftColor = mTextLeftColor;
        invalidate();
    }

    public void setmTextRightColor(int mTextRightColor) {
        this.mTextRightColor = mTextRightColor;
        invalidate();
    }

    public void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        requestLayout();
        invalidate();
    }

    public void setmText(String mText) {
        this.mText = mText;
        requestLayout();
        invalidate();
    }

    public ColorGradientView(Context context) {
        super(context, null);
        init();
    }


    public ColorGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextRightColor);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //计算文本宽高
        mTextWidth = (int) mTextPaint.measureText(mText);

        mTextRect = new Rect();
        mTextPaint.getTextBounds(mText,0,mText.length(),mTextRect);
        mTextHeight = mTextRect.height();
        /**
         在此区别下 FontMetrics,Paint.getTextBounds,Paint.measureText
         FontMetrics:主要是和文字的基准线相关的
         ・FontMetrics.top  文本最顶端
         ・FontMetrics.ascent  文本上面的基准线
         ・FontMetrics.descent 文本下面的基准线
         ・FontMetrics.bottom  文本最底端
         Paint.getTextBounds:获取字符[字符串]占据的矩形区域,意为字体可见部分的矩形区域
         Paint.measureText:字符[字符串]的宽度 相对于上一个 的区别在于 加上了每个字符的留宽 等于 bound.right - bound-left + 字符的留宽

         应用场景
         垂直居中的文字,计算基线位置使用FontMetrics比较方便.
         或者大小不一的问题要实现对齐,使用getTextBounds比较方便.
         获取文字的理想宽度,使用measureText比较方便
         */
        
        //测绘文本最终显示范围  --- 参考textview源码
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        
        //计算文字实际空间的起点 -- 去除padding  左下角的点
        mTextStartX = (getMeasuredWidth() - mTextWidth) / 2;
        mTextStartY = (getMeasuredHeight() - mTextHeight) / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //渐变的中点 左右渐变
        int middle = (int) (mTextStartX + offset*mTextWidth);
        mTextPaint.setTextSize(mTextSize);
        if(mDirection == DIRECTION_LEFT_TO_RIGHT) {
            drawLeft(middle, mTextLeftColor, canvas);
            drawRight(middle,mTextRightColor,canvas);
        }else if(mDirection == DIRECTION_RIGHT_TO_LEFT){
            middle = (int) (mTextStartX + (1-offset)*mTextWidth);
            drawLeft(middle,mTextRightColor,canvas);
            drawRight(middle,mTextLeftColor,canvas);
        }

    }

    private void drawLeft(int middle, int mTextLeftColor,Canvas canvas) {
        mTextPaint.setColor(mTextLeftColor);

        canvas.save();//保存绘制的状态是必须的 -- 如果不保存状态在此有是  渐进的显示出字体 可屏蔽看效果
        canvas.clipRect(mTextStartX, 0, middle, getMeasuredHeight());
         /**
         drawText的时候 Y 值其实是基准线的位置  getMeasuredHeight() / 2 - ((mPaint.descent() + mPaint.ascent()) //此算法比较精准
          (行高-字体高度)/2+字体高度+6  --- 这只是一个取巧的方法
         */
        canvas.drawText(mText, mTextStartX,
                getMeasuredHeight() / 2  //比较精准的算法
                        - ((mTextPaint.descent() + mTextPaint.ascent()) / 2), mTextPaint);
        canvas.restore();
    }

    private void drawRight(int middle, int mTextRightColor,Canvas canvas) {
        mTextPaint.setColor(mTextRightColor);

        canvas.save(); //保存绘制的状态是必须的 -- 如果不保存状态在此有是  渐进的显示出字体 可屏蔽看效果
        //剪裁的矩形区域
        canvas.clipRect(middle, 0, mTextWidth+mTextStartX, getMeasuredHeight());
        //绘制文本 y 值最难确定
        canvas.drawText(mText, mTextStartX,
                getMeasuredHeight() / 2  //比较精准的算法
                        - ((mTextPaint.descent() + mTextPaint.ascent()) / 2), mTextPaint);
        canvas.restore();
    }

    /**
     * 测绘文字最终的显示高度(包括padding值)
     * @param measureSpec
     * @return
     */
    private int measureHeight(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextRect.height();
                result += getPaddingTop() + getPaddingBottom();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }

    private int measureWidth(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);//获取文本的显示的大小
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY://表示父布局给了足够的显示空间，所有不存在超出父布局的问题
                result = val;
                break;
            //这里主要是处理 at_most 和 unspecified情况下 文本的大小必须是给定文本的大小
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextWidth;
                result += getPaddingLeft() + getPaddingRight();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }


    /**
     * 状态的保存,仿照 PagerSlidingTabStrip
     * @param state
     */
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        offset = savedState.offset;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.offset = offset;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        float offset;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            offset = in.readFloat();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeFloat(offset);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
