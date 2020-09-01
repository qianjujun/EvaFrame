package com.hello7890.adapter.decoration;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.data.BackgroundBuild;
import com.hello7890.adapter.data.Group2Data;
import com.hello7890.adapter.data.ItemInfo;
import com.hello7890.adapter.vm.Group2ViewModule;

public class BackgroundGroup2Decoration extends Group2ViewModuleItemDecoration{
    private int parentTop,parentBottom;

    private BackgroundBuild backgroundBuild1;
    private BackgroundBuild backgroundBuild2;

    private ItemInfo itemInfo1,itemInfo2;

    private Rect rectF1,rectF2;

    private Rect tempTop = new Rect();
    private Rect tempBottom = new Rect();

    public BackgroundGroup2Decoration(Group2ViewModule viewModule) {
        super(viewModule, 0, 0, 0);
    }

    public BackgroundGroup2Decoration setBackgroundBuild1(BackgroundBuild backgroundBuild1) {
        this.backgroundBuild1 = backgroundBuild1;
        return this;
    }

    public BackgroundGroup2Decoration setBackgroundBuild2(BackgroundBuild backgroundBuild2) {
        this.backgroundBuild2 = backgroundBuild2;
        return this;
    }

    @Override
    protected void getChild1ItemOffsets(@NonNull Rect outRect, int dataPosition, Group2ViewModule.DataInfo<Group2Data> dataInfo) {
        if(backgroundBuild1==null){
            outRect.set(0,0,0,0);
            return;
        }
        if(itemInfo1==null){
            itemInfo1 = new ItemInfo(backgroundBuild1.getDivider(),backgroundBuild1.getLeft(),backgroundBuild1.getRight());
        }
        int spanCount = viewModule.getChild1SpanCount(dataInfo.getGroupPosition(),dataInfo.getData());
        int[] result = itemInfo1.count(dataInfo.getChildPosition(),spanCount);
        int left = result[0];
        int right = result[1];
        outRect.set(left,0,right,backgroundBuild1.getDivider());

        if(dataInfo.isTopChild(spanCount)){
            outRect.top = backgroundBuild1.getInnerTop()+backgroundBuild1.getOuterTop();
            handlerTopChild1(outRect,dataInfo.getGroupPosition());
        }
        if(dataInfo.isBottomChild(spanCount)){//直接处理掉最后一行  不需要分割
            outRect.bottom = backgroundBuild1.getInnerBottom()+backgroundBuild1.getOuterBottom();
        }
    }


    protected void handlerTopChild1(@NonNull Rect outRect,int groupPosition){

    }



    @Override
    protected void getChild2ItemOffsets(@NonNull Rect outRect, int dataPosition, Group2ViewModule.DataInfo<Group2Data> dataInfo) {
        if(backgroundBuild2==null){
            outRect.set(0,0,0,0);
            return;
        }
        if(itemInfo2==null){
            itemInfo2 = new ItemInfo(backgroundBuild2.getDivider(),backgroundBuild2.getLeft(),backgroundBuild2.getRight());
        }
        int spanCount = viewModule.getChild2SpanCount(dataInfo.getGroupPosition(),dataInfo.getData());
        int[] result = itemInfo2.count(dataInfo.getChildPosition(),spanCount);
        int left = result[0];
        int right = result[1];
        outRect.set(left,0,right,backgroundBuild2.getDivider());

        if(dataInfo.isTopChild(spanCount)){
            outRect.top = backgroundBuild2.getInnerTop()+backgroundBuild2.getOuterTop();
        }
        if(dataInfo.isBottomChild(spanCount)){//直接处理掉最后一行  不需要分割
            outRect.bottom = backgroundBuild2.getInnerBottom()+backgroundBuild2.getOuterBottom();
        }
    }


    private ViewGroup.MarginLayoutParams lp;

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        if(backgroundBuild1!=null&&rectF1==null){
            rectF1 = new Rect();
            rectF1.left = parent.getLeft() + parent.getPaddingLeft() + backgroundBuild1.getOuterLeft();
            rectF1.right = parent.getRight() - parent.getPaddingRight() - backgroundBuild1.getOuterRight();
        }
        if(backgroundBuild2!=null&&rectF2==null){
            rectF2 = new Rect();
            rectF2.left = parent.getLeft() + parent.getPaddingLeft() + backgroundBuild2.getOuterLeft();
            rectF2.right = parent.getRight() - parent.getPaddingRight() - backgroundBuild2.getOuterRight();
        }

        if(rectF1==null&&rectF2==null){
            return;
        }
        parentTop = parent.getTop() + parent.getPaddingTop();
        parentBottom = parent.getBottom() - parent.getPaddingBottom();



        final int childSize = parent.getChildCount();
        View firstChild = null,lastChild = null;

        View firstChild2 = null,lastChild2 = null;


        View child;
        int lastType = -1;
        for (int i = 0; i < childSize; i++) {
            child = parent.getChildAt(i);
            int adapterPosition = parent.getChildAdapterPosition(child);
            int dataPosition = adapterPosition-viewModule.getStartPosition();
            if(!isValidItem(dataPosition)){
                continue;
            }

            Group2ViewModule.DataInfo<Group2Data> dataInfo = viewModule.getDataType(dataPosition);
            if(lastType!=dataInfo.getDataType()){
                lastType = dataInfo.getDataType();

                if(firstChild!=null){
                    onDrawBackground(firstChild,lastChild,c,backgroundBuild1,rectF1);
                    onDrawBackground1(rectF1,tempTop,tempBottom,c);
                    firstChild = null;
                }else  if(firstChild2!=null){
                    onDrawBackground(firstChild2,lastChild2,c,backgroundBuild2,rectF2);
                    onDrawBackground2(rectF2,tempTop,tempBottom,c);
                    firstChild2 = null;
                }

            }



            if(dataInfo.getDataType()==Group2ViewModule.DATA_TYPE_CHILD1&&backgroundBuild1!=null){


                if(firstChild==null){
                    firstChild = child;
                }
                lastChild = child;
            }

            if(dataInfo.getDataType()==Group2ViewModule.DATA_TYPE_CHILD2&&backgroundBuild2!=null){
                if(firstChild2==null){
                    firstChild2 = child;
                }
                lastChild2 = child;
            }
        }
        if(firstChild!=null){
            onDrawBackground(firstChild,lastChild,c,backgroundBuild1,rectF1);
            onDrawBackground1(rectF1,tempTop,tempBottom,c);
        }
        if(firstChild2!=null){
            onDrawBackground(firstChild2,lastChild2,c,backgroundBuild2,rectF2);
            onDrawBackground2(rectF2,tempTop,tempBottom,c);
        }
    }



    private void onDrawBackground(View firstChild, View lastChild, Canvas canvas,BackgroundBuild backgroundBuild,Rect rectF) {
        if(firstChild==null||lastChild==null){
            return;
        }

        lp = (ViewGroup.MarginLayoutParams) firstChild.getLayoutParams();
        int top = firstChild.getTop()-lp.topMargin;
        lp = (ViewGroup.MarginLayoutParams) lastChild.getLayoutParams();
        int bottom = lastChild.getBottom()+lp.bottomMargin;
        //onDrawBackground(firstPosition2,firstChild2,lastPosition2,lastChild2,c,backgroundBuild2,rectF2);
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




        top = rectF.top - backgroundBuild.getInnerTop();
        bottom = rectF.bottom + backgroundBuild.getInnerBottom();
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



        tempTop.left = rectF.left;
        tempTop.right = rectF.right;
        tempTop.top = top;
        tempTop.bottom = rectF.top;

        tempBottom.left = rectF.left;
        tempBottom.right = rectF.right;
        tempBottom.top = rectF.bottom;
        tempBottom.bottom = bottom;
    }


    protected void onDrawBackground1(Rect rectF,Rect topRectF,Rect bottomRectF,Canvas canvas){

    }

    protected void onDrawBackground2(Rect rectF,Rect topRectF,Rect bottomRectF,Canvas canvas){
    }


}
