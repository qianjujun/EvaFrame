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

import com.hello7890.adapter.vm.GroupViewModule;

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
    private int columnNum = 1;
    private int dividerWidth;
    private int leftAndRight;

    public GroupViewModuleItemDecoration setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        mPaint.setColor(dividerColor);
        return this;
    }

    public GroupViewModuleItemDecoration setNoneBottomDivider(boolean noneBottomDivider) {
        this.noneBottomDivider = noneBottomDivider;
        return this;
    }


    public GroupViewModuleItemDecoration setChildColumnNum(int columnNum) {
        this.columnNum = columnNum;
        dividerWidth = mDividerHeight*(columnNum-1)/columnNum;
        return this;
    }
    public GroupViewModuleItemDecoration(GroupViewModule viewModule, int dividerHeight){
        this.viewModule = viewModule;
        mPaint = new Paint();
        mPaint.setColor(dividerColor);
        this.mDividerHeight = dividerHeight;
    }

    public GroupViewModuleItemDecoration(GroupViewModule viewModule, int dividerHeight,int leftAndRight){
        this.viewModule = viewModule;
        mPaint = new Paint();
        mPaint.setColor(dividerColor);
        this.mDividerHeight = dividerHeight;
        this.leftAndRight = leftAndRight;
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
        if(state.isMeasuring()){
            //outRect.set(0,0,0,0);
            //return;
        }
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
        return childInfo[0]%columnNum;
    }

    protected void getChildItemOffsets(@NonNull Rect outRect,int dataPosition){
        if(columnNum==1){
            outRect.set(0,0,0,mDividerHeight);
        }else {
            if(itemInfo==null){
                itemInfo = new ItemInfo(mDividerHeight,leftAndRight);
            }
            viewModule.getChildLocationInfo(dataPosition,childInfo);
            int childDataPosition = childInfo[0];

            int[] result = itemInfo.count(childDataPosition,columnNum);

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
                if(columnNum>1&&findChildIndex(dataPosition)!=columnNum-1){
                    canvas.drawRect(right,child.getTop(),right+mDividerHeight,bottom,mPaint);
                }
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
