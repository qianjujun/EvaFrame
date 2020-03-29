package com.hello7890.adapter.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.data.ItemInfo;
import com.hello7890.adapter.data.Group2Data;
import com.hello7890.adapter.vm.Group2ViewModule;

public class Group2ViewModuleItemDecoration extends RecyclerView.ItemDecoration {
    int startPosition;
    int endPosition;
    int mDividerHeight;
    protected Group2ViewModule viewModule;
    private ItemInfo itemInfo;


    public Group2ViewModuleItemDecoration(Group2ViewModule viewModule, int dividerHeight) {
        this.viewModule = viewModule;
        this.mDividerHeight = dividerHeight;
        itemInfo = new ItemInfo(mDividerHeight, 0);
    }

    public Group2ViewModuleItemDecoration(Group2ViewModule viewModule, int dividerHeight, int leftAndRight) {
        this.viewModule = viewModule;
        this.mDividerHeight = dividerHeight;
        itemInfo = new ItemInfo(mDividerHeight, leftAndRight);
    }

    public Group2ViewModuleItemDecoration(Group2ViewModule viewModule, int dividerHeight, int left, int right) {
        this.viewModule = viewModule;
        this.mDividerHeight = dividerHeight;
        itemInfo = new ItemInfo(mDividerHeight, left, right);
    }

    protected boolean isValidItem(int dataPosition) {
        if (viewModule.size() == 0) {
            return false;
        }
        startPosition = viewModule.getStartPosition();
        endPosition = startPosition + viewModule.size();
        int end = endPosition;
        return dataPosition >= startPosition && dataPosition < end;
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int adapterPosition = parent.getChildAdapterPosition(view);
        int dataPosition = adapterPosition - viewModule.getStartPosition();
        if (!isValidItem(adapterPosition)) {
            outRect.set(0, 0, 0, 0);
            return;
        }

        Group2ViewModule.DataInfo<Group2Data> dataInfo = viewModule.getDataType(dataPosition);
        switch (dataInfo.getDataType()) {
            case Group2ViewModule.DATA_TYPE_CHILD1:
                getChild1ItemOffsets(outRect,dataPosition,dataInfo);
                break;
            case Group2ViewModule.DATA_TYPE_CHILD2:
                getChild2ItemOffsets(outRect,dataPosition,dataInfo);
                break;
            default:
                outRect.set(0, 0, 0, 0);
                break;
        }
    }

    protected void getChild1ItemOffsets(@NonNull Rect outRect, int dataPosition, Group2ViewModule.DataInfo<Group2Data> dataInfo){
        int[] result = itemInfo.count(dataInfo.getChildPosition(),viewModule.getChild1SpanCount(dataInfo.getGroupPosition(),dataInfo.getData()));
        int left = result[0];
        int right = result[1];
        outRect.set(left,0,right,mDividerHeight);
    }


    protected void getChild2ItemOffsets(@NonNull Rect outRect, int dataPosition, Group2ViewModule.DataInfo<Group2Data> dataInfo){
        int[] result = itemInfo.count(dataInfo.getChildPosition(),viewModule.getChild2SpanCount(dataInfo.getGroupPosition(),dataInfo.getData()));
        int left = result[0];
        int right = result[1];
        outRect.set(left,0,right,mDividerHeight);
    }

}
