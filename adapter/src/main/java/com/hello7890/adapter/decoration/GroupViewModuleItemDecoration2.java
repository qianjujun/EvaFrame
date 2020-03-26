package com.hello7890.adapter.decoration;

import android.graphics.Rect;

import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.vm.ViewModule;

public class GroupViewModuleItemDecoration2 extends ViewModuleItemDecoration {
    public GroupViewModuleItemDecoration2(ViewModule viewModule, int dividerHeight) {
        super(viewModule, dividerHeight);
    }

    public GroupViewModuleItemDecoration2(ViewModule viewModule, int dividerHeight, int leftAndRight) {
        super(viewModule, dividerHeight, leftAndRight);
    }

    public GroupViewModuleItemDecoration2(BaseViewModule viewModule, int dividerHeight, int leftAndRight) {
        super(viewModule, dividerHeight, leftAndRight);
    }


    @Override
    protected void handlerItemOffsets(Rect outRect, int dataPosition) {
        super.handlerItemOffsets(outRect, dataPosition);
    }
}
