package com.hello7890.adapter.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.data.ItemInfo;
import com.hello7890.adapter.GroupViewModule;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/3 18:15
 * @describe
 */
public class GroupViewModuleItemDecoration extends RecyclerView.ItemDecoration{
    int startPosition;
    int endPosition;
    int mDividerHeight;
    private Paint mPaint;
    private GroupViewModule viewModule;
    private int dividerColor = Color.TRANSPARENT;
    private boolean noneBottomDivider;

    public GroupViewModuleItemDecoration setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        mPaint.setColor(dividerColor);
        return this;
    }

    public GroupViewModuleItemDecoration setNoneBottomDivider(boolean noneBottomDivider) {
        this.noneBottomDivider = noneBottomDivider;
        return this;
    }



    public GroupViewModuleItemDecoration(GroupViewModule viewModule, int dividerHeight){
        this.viewModule = viewModule;
        mPaint = new Paint();
        mPaint.setColor(dividerColor);
        this.mDividerHeight = dividerHeight;
        itemInfo = new ItemInfo(mDividerHeight,0);
    }

    public GroupViewModuleItemDecoration(GroupViewModule viewModule, int dividerHeight,int leftAndRight){
        this.viewModule = viewModule;
        mPaint = new Paint();
        mPaint.setColor(dividerColor);
        this.mDividerHeight = dividerHeight;
        itemInfo = new ItemInfo(mDividerHeight,leftAndRight);
    }

    public GroupViewModuleItemDecoration(GroupViewModule viewModule, int dividerHeight,int left,int right){
        this.viewModule = viewModule;
        mPaint = new Paint();
        mPaint.setColor(dividerColor);
        this.mDividerHeight = dividerHeight;
        itemInfo = new ItemInfo(mDividerHeight,left,right);
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
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int adapterPosition = parent.getChildAdapterPosition(view);
        int dataPosition = adapterPosition-viewModule.getStartPosition();
        if(!isValidItem(adapterPosition)){
            outRect.set(0,0,0,0);
            return;
        }
        if(viewModule.getDataType(dataPosition)== GroupViewModule.DATA_TYPE_CHILD){
            getChildItemOffsets(outRect,dataPosition);
        }else {
            outRect.set(0,0,0,0);
        }
    }

    private int[] childInfo = new int[2];
    private ItemInfo itemInfo;

    private int findChildIndex(int dataPosition){
        viewModule.getChildLocationInfo(dataPosition,childInfo);
        return childInfo[0]%viewModule.getChildSpanCount();
    }

    protected void getChildItemOffsets(@NonNull Rect outRect,int dataPosition){
        if(viewModule.getChildSpanCount()==1){
            outRect.set(0,0,0,mDividerHeight);
        }else {

            viewModule.getChildLocationInfo(dataPosition,childInfo);
            int childDataPosition = childInfo[0];

            int[] result = itemInfo.count(childDataPosition,viewModule.getChildSpanCount());

            int left = result[0];
            int right = result[1];
            Log.d("qianjujun", "getChildItemOffsets() called with: outRect = [" + left + "], dataPosition = [" + right + "]"+mDividerHeight);
            outRect.set(left,0,right,mDividerHeight);
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawLine(c,parent);
    }

    private void drawLine(Canvas canvas, RecyclerView parent) {
        final int childSize = parent.getChildCount();

        View child;
        RecyclerView.LayoutParams layoutParams;
        for (int i = 0; i < childSize; i++) {
            child = parent.getChildAt(i);
            int adapterPosition = parent.getChildAdapterPosition(child);
            if(!isValidItem(adapterPosition)){
                continue;
            }
            int dataPosition = adapterPosition-viewModule.getStartPosition();
            if(viewModule.getDataType(dataPosition)== GroupViewModule.DATA_TYPE_CHILD){//只绘制子项
                layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                int left = child.getLeft();
                int right = child.getRight();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mDividerHeight;
                canvas.drawRect(left, top, right, bottom, mPaint);
                if(viewModule.getChildSpanCount()>1&&findChildIndex(dataPosition)!=viewModule.getChildSpanCount()-1){
                    canvas.drawRect(right,child.getTop(),right+mDividerHeight,bottom,mPaint);
                }
            }
        }
    }

}
