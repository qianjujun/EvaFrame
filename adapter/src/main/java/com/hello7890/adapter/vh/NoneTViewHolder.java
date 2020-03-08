package com.hello7890.adapter.vh;

import android.view.ViewGroup;

import com.hello7890.adapter.BaseViewHolder;

public abstract class NoneTViewHolder extends BaseViewHolder<Object> {
    public NoneTViewHolder(int layoutId, ViewGroup container) {
        super(layoutId, container);
    }

    @Override
    public final void onBindData(Object o, int dataPosition, int adapterPosition) {
        onBindData(dataPosition, adapterPosition);
    }

    protected abstract void onBindData(int dataPosition, int layoutPosition);
}
