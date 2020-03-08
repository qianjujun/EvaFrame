package com.qianjujun.frame.base;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BetterCustomModuleFragment<DB extends ViewDataBinding> extends BetterModuleFragment {
    protected DB mDataBinding;

    @Override
    protected final void initModule(RecyclerView recyclerView, View contentView) {
        mDataBinding = DataBindingUtil.bind(contentView);
        initModule(recyclerView, mDataBinding);
    }


    @Override
    protected final int getLayoutId() {
        return getIncludeRecyclerViewLayoutId();
    }

    protected abstract int getIncludeRecyclerViewLayoutId();


    protected abstract void initModule(RecyclerView recyclerView, DB dataBinding);
}
