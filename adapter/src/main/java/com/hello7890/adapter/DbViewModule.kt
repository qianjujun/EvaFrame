package com.hello7890.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.hello7890.adapter.listener.OnModuleItemClickListener
import com.hello7890.adapter.vh.BaseDbViewHolder

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 16:24
 * @describe
 */
abstract class DbViewModule<T, DB : ViewDataBinding> : ViewModule<T>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseDbViewHolder<T, DB> {
        return object : BaseDbViewHolder<T, DB>(layoutId, parent) {
            override fun onBindData(t: T, dataPosition: Int, adapterPosition: Int) {
                this@DbViewModule.onBindData(mDataBinding, t, dataPosition, adapterPosition)
            }

            override fun onBindData(data: T, dataPosition: Int, adapterPosition: Int, payloads: List<*>) {
                this@DbViewModule.onBindData(mDataBinding, data, dataPosition, adapterPosition,payloads)
            }
        }
    }

    @get:LayoutRes
    protected abstract val layoutId: Int
    protected abstract fun onBindData(dataBinding: DB, data: T, dataPosition: Int, layoutPosition: Int)

    protected abstract fun onBindData(dataBinding: DB, data: T, dataPosition: Int, layoutPosition: Int,payloads: List<*>)
}