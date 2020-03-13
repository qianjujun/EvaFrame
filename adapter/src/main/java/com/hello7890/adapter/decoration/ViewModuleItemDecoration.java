package com.hello7890.adapter.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.vm.ViewModule;

import java.util.Arrays;


/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2018-05-18  13:52
 */
public class ViewModuleItemDecoration extends RecyclerView.ItemDecoration{
    public static final String TAG = "ModuleItemDecoration";

    private int mDividerHeight = 1;



    private Paint mPaint;
    protected BaseViewModule viewModule;
    private int dividerColor = Color.parseColor("#dddddd");
    private boolean noneBottomDivider;

    private final ItemInfo itemInfo;

    private int parentTop,parentBottom;


    public ViewModuleItemDecoration setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        mPaint.setColor(dividerColor);
        return this;
    }

    public ViewModuleItemDecoration setNoneBottomDivider(boolean noneBottomDivider) {
        this.noneBottomDivider = noneBottomDivider;
        return this;
    }



    public ViewModuleItemDecoration(ViewModule viewModule, int dividerHeight){
        this(viewModule,dividerHeight,0);
    }
    public ViewModuleItemDecoration(ViewModule viewModule, int dividerHeight, int leftAndRight){
        this.viewModule = viewModule;
        this.mDividerHeight = dividerHeight;
        this.itemInfo = new ItemInfo(dividerHeight,leftAndRight);
        mPaint = new Paint();
        mPaint.setColor(dividerColor);
    }


    public ViewModuleItemDecoration(BaseViewModule viewModule, int dividerHeight, int leftAndRight){
        this.viewModule = viewModule;
        this.mDividerHeight = dividerHeight;
        this.itemInfo = new ItemInfo(dividerHeight,leftAndRight);
        mPaint = new Paint();
        mPaint.setColor(dividerColor);
    }




    protected boolean isValidItem(int dataPosition){
        if(viewModule.size()==0){
            return false;
        }
        if(viewModule.getItem(dataPosition)==null){
            return false;
        }

        if(dataPosition<0||dataPosition>=viewModule.size()){
            return false;
        }

        return true;
    }

    protected boolean isNoBottomItem(int dataPosition){
        int spanCount = viewModule.getSpanCount();
        if(spanCount==1){
            return dataPosition<viewModule.size()-1;
        }

        int index = (viewModule.size()-1)%spanCount;

        return dataPosition<viewModule.size()-1-index;
    }

    private int getBottomHeight(int dataPosition){
        if(noneBottomDivider&&isNoBottomItem(dataPosition)){
            return mDividerHeight;
        }
        return 0;
    }








    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int adapterPosition = parent.getChildAdapterPosition(view);
        int dataPosition = adapterPosition-viewModule.getStartPosition();
        if(isValidItem(dataPosition)){
            int[] lr = itemInfo.count(dataPosition,viewModule.getSpanCount());
            outRect.set(lr[0],0,lr[1],getBottomHeight(dataPosition));
            handlerItemOffsets(outRect,dataPosition);
        }else {
            outRect.set(0,0,0,0);
        }
    }


    protected void handlerItemOffsets(Rect outRect,int dataPosition){

    }











    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if(mDividerHeight==0){
            return;
        }

        parentTop = parent.getTop()+parent.getPaddingTop();
        parentBottom = parent.getBottom()-parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        View child;
        RecyclerView.LayoutParams layoutParams;
        for (int i = 0; i < childSize; i++) {
            child = parent.getChildAt(i);
            int adapterPosition = parent.getChildAdapterPosition(child);
            int dataPosition = adapterPosition-viewModule.getStartPosition();
            if(isValidItem(dataPosition)){
                layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                onDrawLine(layoutParams,c,child,dataPosition,viewModule.getSpanCount(),mDividerHeight,viewModule.size());
            }
        }
    }


    protected void onDrawLine(RecyclerView.LayoutParams layoutParams, Canvas canvas, View child, int dataPosition, int columnNum, int dividerHeight, int size){
        int left = child.getLeft();
        int right = child.getRight();
        int top = child.getBottom() + layoutParams.bottomMargin;
        int bottom = top + mDividerHeight;
        if(top<parentTop){
            top = parentTop;
        }
        if(bottom>parentBottom){
            bottom = parentBottom;
        }
        if(top<bottom){
            //横线部分
            if(!noneBottomDivider||isNoBottomItem(dataPosition)){
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }

        top = child.getTop();
        if(!isNoBottomItem(dataPosition)){
            bottom = child.getBottom();
        }
        if(top<parentTop){
            top = parentTop;
        }
        if(bottom>parentBottom){
            bottom = parentBottom;
        }

        if(top<bottom){
            //纵向部分
            if(itemInfo.needDrawGrid(dataPosition)){
                canvas.drawRect(right,top,right+mDividerHeight,bottom,mPaint);
            }
        }

    }






    static class ItemInfo{
        int columnNum;
        int dividerWidth;//分割线的宽度
        /**
         * 最左和最右边的宽度
         */
        int leftAndRight;
        /**
         * 总宽度 为 60  columnNum*dividerWidth/(columnNum-1)
         * --  --  --
         *   30  30
         */
        int totalDividerWidth;

        /**
         * 每个item要分配给divider的宽度   如上图 分割线总长为60，有3个item  则每个item分配出去的宽度为60/3
         */
        int itemColumnWidth;

        int firstLeft;
        int lastLeft;
        int itemStep;

        int[] tempLR = new int[2];

        public ItemInfo(int dividerWidth, int leftAndRight) {
            this.dividerWidth = dividerWidth;
            this.leftAndRight = leftAndRight;

        }

        private void update(@IntRange(from = 2) int columnNum){//列数变化
            if(columnNum<2){
                throw new IllegalArgumentException("columnNum must >= 2");
            }
            this.columnNum = columnNum;
            totalDividerWidth = leftAndRight*2+dividerWidth*(columnNum-1);
            itemColumnWidth = totalDividerWidth/columnNum;
            firstLeft = leftAndRight;
            lastLeft = itemColumnWidth - leftAndRight;
            itemStep = (leftAndRight-lastLeft)/(columnNum-1);
        }

        int[] count(int dataPosition,int columnNum){
            if(columnNum<1){
                throw new IllegalArgumentException("columnNum must >= 1");
            }
            if(columnNum==1){
                tempLR[0] = leftAndRight;
                tempLR[1] = leftAndRight;
                return tempLR;
            }
            if(this.columnNum!=columnNum){
                update(columnNum);
            }
            int columnIndex = dataPosition%columnNum;
            int left = leftAndRight - columnIndex*itemStep;
            int right = itemColumnWidth-left;
            tempLR[0] = left;
            tempLR[1] = right;
            return tempLR;
        }


        boolean needDrawGrid(int dataPosition){
            return columnNum>1&&dataPosition%columnNum!=(columnNum-1);
        }





    }




}
