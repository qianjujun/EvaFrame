package com.hello7890.adapter

import android.view.ViewGroup
import com.hello7890.adapter.vh.SpaceTViewHolder

/**
 * 空module 占位
 */
class SpaceViewModule : DataViewModule<Int>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Int> {
        return SpaceTViewHolder(parent)
    }

    init {
        dataList.add(0)
    }
}