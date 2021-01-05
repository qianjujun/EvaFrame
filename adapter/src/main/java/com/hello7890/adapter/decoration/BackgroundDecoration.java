package com.hello7890.adapter.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.BaseViewModule;


public class BackgroundDecoration extends ViewModuleItemDecoration {

    private int padding, outPadding, innerPadding;

    private int parentTop,parentBottom;

    private RectF rectF = new RectF();

    public BackgroundDecoration(BaseViewModule viewModule, int outerLr, int innerLr) {
        this(viewModule,outerLr,innerLr,0);
    }

    public BackgroundDecoration(BaseViewModule viewModule, int outerLr, int innerLr,int dividerHeight) {
        super(viewModule,dividerHeight, outerLr + innerLr);
        setNoneBottomDivider(true);
        padding = outerLr + innerLr;
        outPadding = outerLr;
        innerPadding = innerLr;
    }

    @Override
    protected void handlerItemOffsets(Rect outRect, int dataPosition) {
        super.handlerItemOffsets(outRect, dataPosition);
        if (isTopPosition(dataPosition)) {
            outRect.top = padding;
        }
        if (isBottomPosition(dataPosition)) {
            outRect.bottom = padding;
        }
    }



    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (rectF.right == 0) {
            rectF.left = parent.getLeft() + parent.getPaddingLeft() + outPadding;
            rectF.right = parent.getRight() - parent.getPaddingRight() - outPadding;
        }
        parentTop = parent.getTop() + parent.getPaddingTop();
        parentBottom = parent.getBottom() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        View firstChild = null,lastChild = null;
        int firstPosition=0,lastPosition = 0;
        View child;
        for (int i = 0; i < childSize; i++) {
            child = parent.getChildAt(i);
            int adapterPosition = parent.getChildAdapterPosition(child);
            int dataPosition = adapterPosition- viewModule.getStartPosition();
            if(isValidItem(dataPosition)){
                if(firstChild==null){
                    firstChild = child;
                    firstPosition = dataPosition;
                }
                lastChild = child;
                lastPosition = dataPosition;
            }
        }
        if(firstChild==null||lastChild==null){
            return;
        }
        onDrawBackground(firstPosition,firstChild,lastPosition,lastChild,c);
    }



    private void onDrawBackground(int firstPosition, View firstChild, int lastPosition, View lastChild, Canvas canvas) {
        if(firstChild==null||lastChild==null){
            return;
        }

        int top = firstChild.getTop() - innerPadding;
        int bottom = lastChild.getBottom() + innerPadding;

        if(top<parentTop){
            top = parentTop;
        }

        if(top>parentBottom){
            top = parentBottom;
        }

        if(bottom>parentBottom){
            bottom = parentBottom;
        }
        if(bottom<parentTop){
            bottom = parentTop;
        }

        rectF.top = top;
        rectF.bottom = bottom;



        onDrawViewModuleBackground(canvas, rectF, top>parentTop&&isTopPosition(firstPosition), bottom<parentBottom&&isBottomPosition(lastPosition));


    }




    protected boolean isTopPosition(int dataPosition) {
        int spanCount = viewModule.getSpanCount();
        int index = dataPosition % spanCount;
        return dataPosition <= index;
    }

    protected boolean isBottomPosition(int dataPosition) {
        return !isNoBottomItem(dataPosition);
    }




    protected void onDrawViewModuleBackground(Canvas canvas, RectF rectF, boolean top, boolean bottom) {

    }


}
