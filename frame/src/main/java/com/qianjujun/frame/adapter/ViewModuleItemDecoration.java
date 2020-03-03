package com.qianjujun.frame.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.qianjujun.frame.utils.Frame;

/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2018-05-18  13:52
 */
public class ViewModuleItemDecoration extends RecyclerView.ItemDecoration{
    public static final String TAG = "ModuleItemDecoration";
    int startPosition;
    int endPosition;
    int mDividerHeight;



    private Paint mPaint;
    private ViewModule viewModule;
    private int dividerColor = Color.RED;
    private boolean noneBottomDivider;
    private int columnNum = 1;
    private int dividerWidth;

    public ViewModuleItemDecoration setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        mPaint.setColor(dividerColor);
        return this;
    }

    public ViewModuleItemDecoration setNoneBottomDivider(boolean noneBottomDivider) {
        this.noneBottomDivider = noneBottomDivider;
        return this;
    }


    public ViewModuleItemDecoration setColumnNum(int columnNum) {
        this.columnNum = columnNum;
        dividerWidth = mDividerHeight*(columnNum-1)/columnNum;
        return this;
    }

    public ViewModuleItemDecoration(ViewModule viewModule, int dividerHeight){
        this.viewModule = viewModule;
        mPaint = new Paint();
        mPaint.setColor(dividerColor);
        this.mDividerHeight = dividerHeight;
    }

    private boolean isValidItem(int dataPosition){
        if(viewModule.size()==0){
            return false;
        }
        startPosition = viewModule.getStartPosition();
        endPosition = startPosition+viewModule.size();
        int end = endPosition;
        if(noneBottomDivider){
            end = end-1;
        }
        return dataPosition>=startPosition&&dataPosition<end;
    }



    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawHorizontal(c,parent);
    }

    private int findIndexInMultipeColumn(int columnNum,int dataPosition){
        return dataPosition%columnNum;
    }



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int adapterPosition = parent.getChildAdapterPosition(view);
        int dataPosition = adapterPosition-viewModule.getStartPosition();
        log1(view.getRight(),parent.getRight(),columnNum,adapterPosition);
        if(isValidItem(adapterPosition)){
            if(columnNum==1){
                outRect.set(0,0,0,mDividerHeight);
            }else {
                int index = findIndexInMultipeColumn(columnNum,dataPosition);
                int itemNum = columnNum-1;
                int left = dividerWidth *index/itemNum;
                int right = dividerWidth *(itemNum-index)/itemNum;
                outRect.set(left,0,right,mDividerHeight);
            }
        }else {
            outRect.set(0,0,0,0);
        }
    }

    private void log1(int viewR,int parentR,int columnNum,int adapterPosition){
        Log.d(TAG, "log1() called with: viewR = [" + viewR + "], parentR = [" + parentR + "], columnNum = [" + columnNum + "], adapterPosition = [" + adapterPosition + "]");
    }



    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int childSize = parent.getChildCount();

        View child;
        RecyclerView.LayoutParams layoutParams;
        for (int i = 0; i < childSize; i++) {
            child = parent.getChildAt(i);
            int dataPosition = parent.getChildAdapterPosition(child);
            if(isValidItem(dataPosition)){
                layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                int left = child.getLeft();
                int right = child.getRight();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mDividerHeight;
                canvas.drawRect(left, top, right, bottom, mPaint);

                if(Frame.DEBUG){
                    log(left,top,right,bottom,dataPosition);
                }

                if(columnNum>1&&dataPosition%columnNum!=columnNum-1){
                    canvas.drawRect(right,child.getTop(),right+mDividerHeight,bottom,mPaint);
                }
            }
        }
    }


    private void log(int left,int top,int right,int bottom,int index){
        Log.d(TAG, "log() called with: left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "], index = [" + index + "]");
    }


    /**
     *
     * @param dividerNum  代表的是A+B1 B2+C1 的值
     * @param column      代表几个人  如ABCD = 4
     * @param index       代表的位置 0 代表A
     * @return            返回数组 0代表左口袋 1代表右口袋  如A new int[0,30]
     */
    private static int[] count(int dividerNum,int column,int index){
        int totalWidth = dividerNum*(column-1);
        int itemWidth = totalWidth/column;
        int itemNum = column-1;
        int left = itemWidth*index/itemNum;
        int right = itemWidth*(itemNum-index)/itemNum;
        return new int[]{left,right};
    }


}
