package com.hello7890.adapter.vh;

import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class NoneTDbViewHolder<DB extends ViewDataBinding> extends NoneTViewHolder{
    protected DB mDataBinding;
    public NoneTDbViewHolder(int layoutId, ViewGroup container) {
        super(layoutId, container);
        mDataBinding = DataBindingUtil.bind(itemView);
    }

    @Override
    protected final void onBindData(int dataPosition, int layoutPosition) {
        onBindData(mDataBinding,dataPosition,layoutPosition);
    }

    protected abstract void onBindData(DB dataBind, int dataPosition, int layoutPosition) ;
}
