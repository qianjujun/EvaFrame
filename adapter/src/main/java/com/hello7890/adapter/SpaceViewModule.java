package com.hello7890.adapter;

import android.view.ViewGroup;

import com.hello7890.adapter.vh.SpaceTViewHolder;

/**
 * 空module 占位
 */
public final class SpaceViewModule extends DataViewModule<Integer> {

    public SpaceViewModule(){
        this.dataList.add(0);
    }

    @Override
    public BaseViewHolder<Integer> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpaceTViewHolder<>(parent);
    }
}
