package com.hello7890.adapter.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.BaseViewModule;

public class BackgroundDecoration extends ViewModuleItemDecoration {

    private int padding, outPadding, innerPadding;

    public BackgroundDecoration(BaseViewModule viewModule, int outerLr, int innerLr) {
        super(0, outerLr + innerLr, viewModule);
        setNoneBottomDivider(true);
        padding = outerLr + innerLr;
        outPadding = outerLr;
        innerPadding = innerLr;
    }

    private int left, right, top, bottom, parentTop, parentBottom;

    @Override
    protected void onDrawLine(RecyclerView.LayoutParams layoutParams, Canvas canvas, View child, int dataPosition, int columnNum, int dividerHeight, int size) {
//        if (right == 0 || left == 0) {
//            View view = (View) child.getParent();
//            left = view.getLeft() + view.getPaddingLeft() + outPadding;
//            right = view.getRight() - view.getPaddingRight() - outPadding;
//            parentTop = view.getTop() + view.getPaddingTop();
//            parentBottom = view.getBottom() - view.getPaddingBottom() - outPadding;
//            rectF.left = left;
//            rectF.right = right;
//        }
//
//        draw(child, dataPosition, size, canvas, columnNum);
    }


    // TODO: 2020/3/11 待计算优化

    /**
     * 1，top圆角 bottom圆角
     * 2，top圆角 bottom直角
     * 3，top直角 bottom圆角
     * 4，top直角 bottom直角
     *
     * @param child
     * @param dataPosition
     * @param size
     * @param canvas
     */
    private RectF rectF = new RectF();

    private void draw(View child, int dataPosition, int size, Canvas canvas, int columnNum) {

        if (dataPosition % columnNum > 0) {//如果是多列的 只计算最左边的
            return;
        }

        if (child.getBottom() < parentTop - innerPadding) {
            return;
        }
        if (child.getTop() > parentBottom) {
            return;
        }
        boolean top = isTopPosition(dataPosition);
        boolean bottom = isBottomPosition(dataPosition);

        if (child.getTop() < parentTop && child.getBottom() <= parentBottom) {
            rectF.top = parentTop;
            if (bottom) {
                rectF.bottom = child.getBottom() + innerPadding;
            } else {
                rectF.bottom = child.getBottom();
            }
            onDrawViewModuleBackground(canvas, rectF, false, bottom);
            return;
        }
        if (child.getTop() < parentTop && child.getBottom() > parentBottom) {
            rectF.top = parentTop;
            rectF.bottom = parentBottom + innerPadding;
            onDrawViewModuleBackground(canvas, rectF, false, false);
            return;
        }


        if (child.getTop() >= parentTop && child.getBottom() <= parentBottom) {
            if (top) {
                rectF.top = child.getTop() - innerPadding;
            } else {
                rectF.top = child.getTop();
            }
            if (bottom) {
                rectF.bottom = child.getBottom() + innerPadding;
            } else {
                rectF.bottom = child.getBottom();
            }


            onDrawViewModuleBackground(canvas, rectF, top, bottom);
            return;
        }

        if (child.getTop() >= parentTop && child.getBottom() > parentBottom) {
            if (top) {
                rectF.top = child.getTop() - innerPadding;
            } else {
                rectF.top = child.getTop();
            }
            rectF.bottom = parentBottom + outPadding;
            onDrawViewModuleBackground(canvas, rectF, top, false);
            return;
        }

    }

    @Override
    protected void onDrawLine(int firstPosition, View firstChild, int lastPosition, View lastChild, Canvas canvas) {
        if(firstChild==null||lastChild==null){
            return;
        }
        if (right == 0 || left == 0) {
            View view = (View) firstChild.getParent();
            left = view.getLeft() + view.getPaddingLeft() + outPadding;
            right = view.getRight() - view.getPaddingRight() - outPadding;

            parentTop = view.getTop() + view.getPaddingTop();
            parentBottom = view.getBottom() - view.getPaddingBottom();
            rectF.left = left;
            rectF.right = right;
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


    protected boolean isTopPosition(int dataPosition) {
        int spanCount = viewModule.getSpanCount();
        int index = dataPosition % spanCount;
        return dataPosition <= index;
    }

    protected boolean isBottomPosition(int dataPosition) {
//        int spanCount = viewModule.getSpanCount();
//        int index = dataPosition%spanCount;
//        return dataPosition>=viewModule.size()-index;
        return !isNoBottomItem(dataPosition);
    }

    private boolean isNoBottomItem(int dataPosition) {
        int spanCount = viewModule.getSpanCount();
        if (spanCount == 1) {
            return dataPosition < viewModule.size() - 1;
        }

        int index = (viewModule.size() - 1) % spanCount;

        return dataPosition < viewModule.size() - 1 - index;
    }


    protected void onDrawViewModuleBackground(Canvas canvas, RectF rectF, boolean top, boolean bottom) {

    }


}
