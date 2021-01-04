package com.hello7890.adapter.vh

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hello7890.adapter.BaseViewHolder

abstract class BaseDbViewHolder<T, DB : ViewDataBinding>(layoutId: Int, container: ViewGroup) : BaseViewHolder<T>(layoutId, container) {

    @JvmField
    protected  var mDataBinding: DB = DataBindingUtil.bind(itemView)!!

}