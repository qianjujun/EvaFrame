package com.hello7890.adapter.vh;

import android.view.ViewGroup;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.R;

public class SpaceTViewHolder<T> extends BaseViewHolder<T> {
    public SpaceTViewHolder(ViewGroup container) {
        super(R.layout.space_vm, container);
    }

    @Override
    public void onBindData(T t, int dataPosition, int adapterPosition) {

    }
}
