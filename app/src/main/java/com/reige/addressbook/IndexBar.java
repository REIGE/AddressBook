package com.reige.addressbook;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by REIGE
 * Date :2017/3/17.
 */

public class IndexBar extends View {

    private OnSlideListener onSlideListener;
    private String[] arr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private Paint mPaint;
    private int mWidth;
    private float mBoxHeight;

    public IndexBar(Context context) {
        super(context);

    }

    public IndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        //抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.BLUE);
        //因为默认的文字绘制锚点在文字的左下角，这样会使文字的左边在一条直线上 因为字母的宽度不同会使绘制出的字母看上去比较乱 这个方法可以将锚点设置到文字底部中心 从而避免这个问题
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mBoxHeight = getMeasuredHeight() * 1f / arr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < arr.length; i++) {
            float textHeight = getTextHeight(arr[i]);
            float textWidth = getTextWidth(arr[i]);
            float x = mWidth / 2;
            float y =mBoxHeight/2+textWidth/2+ i* mBoxHeight;
            mPaint.setColor(lastIndex == i ? Color.RED : Color.BLUE);


            canvas.drawText(arr[i], x, y, mPaint);
        }
    }

    private int lastIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                float y = event.getY();
                //获取当前点击第几个item
                int index = (int) (y / mBoxHeight);
                if (lastIndex != index) {
                    if (index >= 0 && index < arr.length) {
                        Log.e("index", "index:::" + index);

                        //选择的letter变化 回调
                        if (onSlideListener != null) {
                            onSlideListener.onSlide(arr[index]);
                        }

                    }
                }
                lastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                lastIndex = -1;
                break;
        }

        invalidate();
        return true;
    }

    /**
     * @param text
     * @return 返回文字的高
     */
    private float getTextHeight(String text) {
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }
    private float getTextWidth(String text) {
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();
    }

    /**
     * 设置监听的方法
     */
    public interface OnSlideListener {
        void onSlide(String letter);
    }

    /**
     * 滑动监听
     *
     * @param osl
     */
    public void setOnSlideListener(OnSlideListener osl) {
        this.onSlideListener = osl;
    }
}
