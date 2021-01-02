package com.hello7890.adapter.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.data.ItemInfo;
import com.hello7890.adapter.ViewModule;


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










}
