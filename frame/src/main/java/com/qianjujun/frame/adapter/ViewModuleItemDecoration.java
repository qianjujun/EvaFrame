package com.qianjujun.frame.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2018-05-18  13:52
 */
public class ViewModuleItemDecoration extends RecyclerView.ItemDecoration{
    public static final String TAG = "TestItemD";
    int startPosition;
    int endPosition;

    int colum;

    int space;

    boolean topSpace;


    private Drawable mDivider;
    private Paint mPaint;

    private ViewModule viewModule;


    public ViewModuleItemDecoration(ViewModule viewModule, int space){
        this.viewModule = viewModule;


    }


    private int lr;
    public ViewModuleItemDecoration(ViewModule viewModule, int space, int lr){
        this(viewModule,space);
        this.lr = lr;
    }

    public ViewModuleItemDecoration setTopSpace(boolean topSpace) {
        this.topSpace = topSpace;
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        Log.d(TAG, "onDraw: startPosition:"+startPosition+"   endPosition"+endPosition);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        startPosition = viewModule.getStartPosition();
        endPosition = startPosition+viewModule.getSize();
        int adapterPosition = parent.getChildAdapterPosition(view);


        Log.d("getItemOffsets","adapterPosition:"+adapterPosition+"  startPosition:"+startPosition+"  endPosition:"+endPosition);

        if(adapterPosition<startPosition||adapterPosition>=endPosition){
            return;
        }


        //使得每个Item左右 rect 上下rect相加之后相等


        if(colum==1){
            outRect.set(0,topSpace?space*2:0,0,topSpace?0:space*2);
        }else {
            outRect.set(space,topSpace?space*2:0,space,topSpace?0:space*2);
        }

        if(lr-space>0&&colum==2){
            int dataPosition = adapterPosition-startPosition;
            if(dataPosition%colum==0){
                outRect.left = lr;
            }
            if(dataPosition%colum==colum-1){
                outRect.right = lr;
            }
        }

    }



    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();

        View child;
        RecyclerView.LayoutParams layoutParams;
        for (int i = 0; i < childSize; i++) {
            child = parent.getChildAt(i);
            layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = topSpace? child.getTop()-layoutParams.topMargin-space:child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + space;


            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }else {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }



    /***/
    private void drawGrideview(Canvas canvas, RecyclerView parent) {
        int childSize = parent.getChildCount();
        int top, bottom, left, right;
        View child;
        RecyclerView.LayoutParams layoutParams;

        for (int i = 0; i < childSize; i++) {
            child = parent.getChildAt(i);
            //画横线
            layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            top = topSpace? child.getTop()-layoutParams.topMargin-space:child.getBottom() + layoutParams.bottomMargin;
            bottom = top + space;

            left = child.getLeft();
            right = child.getRight();

            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }else {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }

            //画竖线
            top = child.getTop();
            bottom = child.getBottom();

            left = child.getRight() + layoutParams.rightMargin;
            right = left + space;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }


        }
    }

}
