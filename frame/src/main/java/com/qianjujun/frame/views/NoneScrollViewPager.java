package com.qianjujun.frame.views;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NoneScrollViewPager extends ViewPager {
    public NoneScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoneScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean canScrollHorizontally(int direction) {
        return false;
    }
}
