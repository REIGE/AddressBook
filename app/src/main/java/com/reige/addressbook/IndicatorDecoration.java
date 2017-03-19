package com.reige.addressbook;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

/**
 * Created by REIGE
 * Date :2017/3/18.
 */

public class IndicatorDecoration extends RecyclerView.ItemDecoration {


    private List<? extends ContactsBean> mData;
    private final Context mContext;
    private final Paint mPaint;

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private int mOrientation;
    private Drawable mDivider;


    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    //条目头部标题的宽度
    private final int mTitleHeight;
    private final float mTextSize;


    public IndicatorDecoration(Context ctx, List<? extends ContactsBean> data) {
        super();
        mContext = ctx;
        mData = data;

        final TypedArray a = mContext.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();

        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, ctx
                .getResources().getDisplayMetrics());
        mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, ctx
                .getResources().getDisplayMetrics());
        //抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);
    }


    //而在RecyclerView的draw方法中会先通过super.draw()
    // 调用父类也就是View的draw方法，进而继续调用RecyclerView的OnDraw方法，ItemDecorations的onDraw方法就在此时会被调用
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left;
        int top;
        int right;
        int bottom;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams childParams = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = childParams.getViewLayoutPosition();

            left = parent.getPaddingLeft();
            top = child.getTop() - childParams.topMargin - mTitleHeight;
            right = parent.getWidth() - parent.getPaddingRight();
            bottom = top + mTitleHeight;

            if (position == 0) {

                drawTitle(c, left, top, right, bottom, child, childParams, position);
            } else if (mData.get(position).index != null && !mData.get(position).index .equals(mData.get
                    (position - 1).index) ) {

                drawTitle(c, left, top, right, bottom, child, childParams, position);
            }

        }


    }


    private void drawTitle(Canvas c, int left, int top, int right, int bottom, View child,
                           RecyclerView.LayoutParams
                                   params, int position) {

        mPaint.setColor(mContext.getResources().getColor(R.color.md_blue_300));
        c.drawRect(left, top, right, bottom, mPaint);
        mPaint.setColor(mContext.getResources().getColor(R.color.md_white_1000));

        float textHeight = getTextHeight(mData.get(position).name);

        // 将字母绘制到 title的中间
        c.drawText(mData.get(position).index, child.getPaddingLeft(), child.getTop() - params
                .topMargin - mTitleHeight / 2 + textHeight / 2, mPaint);

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

    //在OnDraw 方法结束后调用
    @Override
    public void onDrawOver(Canvas c, final RecyclerView parent, RecyclerView.State state) {
        int firstPos = ((LinearLayoutManager) parent.getLayoutManager())
                .findFirstVisibleItemPosition();

        View child = parent.findViewHolderForAdapterPosition(firstPos).itemView;

        boolean flag = false;//定义一个flag，Canvas是否位移过的标志
        if (!mData.get(firstPos).index.equals(mData.get(firstPos + 1 ).index)) {
            if (child.getTop() + child.getHeight() < mTitleHeight) {
                //在canvas 移动前先保存他的状态
                c.save();
                flag = true;
                c.translate(0, child.getTop() + child.getHeight() - mTitleHeight);
            }

        }

        //mPaint.setColor(mContext.getResources().getColor(R.color.md_blue_300));
        mPaint.setColor(mContext.getResources().getColor(R.color.md_amber_900));


        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent
                .getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        mPaint.setColor(mContext.getResources().getColor(R.color.md_white_1000));

        float textHeight = getTextHeight(mData.get(firstPos).name);

        // 将字母绘制到 title的中间
        c.drawText(mData.get(firstPos).index, child.getPaddingLeft(), mTitleHeight - mTitleHeight
                / 2 + textHeight / 2, mPaint);

        //如果canvas 移动过 回到他保存前的状态
        if(flag){
            c.restore();
        }

    }


    //设置item周围边框的补偿
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position == 0) {
            outRect.set(0, mTitleHeight, 0, 0);//左上右下
        } else if (mData.get(position).index != null && mData.get(position).index != mData.get
                (position - 1).index) {
            //如果当前条目的index 也就是 拼音首字母 和上一个条目不同 则有title
            outRect.set(0, mTitleHeight, 0, 0);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }


}
