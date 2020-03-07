package com.hello7890.adapter.vh;

import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.hello7890.adapter.BaseViewHolder;

public abstract class BaseDbViewHolder<T,DB extends ViewDataBinding> extends BaseViewHolder<T> {
    protected DB mDataBinding;

    public BaseDbViewHolder(int layoutId, ViewGroup container) {
        super(layoutId, container);
        mDataBinding = DataBindingUtil.bind(itemView);
    }


}
