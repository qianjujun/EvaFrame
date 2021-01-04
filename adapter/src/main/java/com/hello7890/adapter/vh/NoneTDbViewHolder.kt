package com.hello7890.adapter.vh

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class NoneTDbViewHolder<DB : ViewDataBinding>(layoutId: Int, container: ViewGroup) : NoneTViewHolder(layoutId, container) {
    protected var mDataBinding: DB = DataBindingUtil.bind(itemView)!!


    override fun onBindData(dataPosition: Int, layoutPosition: Int) {
        onBindData(mDataBinding, dataPosition, layoutPosition)
    }

    protected abstract fun onBindData(dataBind: DB, dataPosition: Int, layoutPosition: Int)

}