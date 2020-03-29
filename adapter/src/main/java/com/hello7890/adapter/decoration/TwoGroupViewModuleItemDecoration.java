package com.hello7890.adapter.decoration;

import android.graphics.Color;
import android.graphics.Paint;

import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.vm.GroupViewModule;
import com.hello7890.adapter.vm.TwoGroupViewModule;

public class TwoGroupViewModuleItemDecoration extends RecyclerView.ItemDecoration{
    int startPosition;
    int endPosition;
    int mDividerHeight;
    private TwoGroupViewModule viewModule;
    private boolean noneBottomDivider;
}
