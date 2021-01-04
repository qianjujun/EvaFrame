package com.hello7890.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hello7890.adapter.listener.OnModuleItemClickListener
import com.hello7890.adapter.listener.OnModuleItemLongClickListener

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 16:26
 * @describe
 */
abstract class BaseViewHolder<T>(@LayoutRes layoutId: Int, container: ViewGroup) : ViewHolder(LayoutInflater.from(container.context).inflate(layoutId, container, false)) {
    private var mOnModuleItemClickListener: OnModuleItemClickListener<T>? = null
    private var mOnModuleItemLongClickListener: OnModuleItemLongClickListener<T>? = null
    fun bindData(t: T?, dataPosition: Int, layoutPosition: Int, payloads: List<*>?) {
        itemView.setOnClickListener(null)
        itemView.setOnLongClickListener(null)

        if(t==null){//框架层已过滤空数据到 SpaceViewHolder  无需再处理
            return
        }

        mOnModuleItemClickListener?.let {
            itemView.setOnClickListener { mOnModuleItemClickListener!!.onModuleItemClick(t, dataPosition, layoutPosition) }
        }

        mOnModuleItemLongClickListener?.let {
            itemView.setOnLongClickListener {  mOnModuleItemLongClickListener!!.onModuleItemLongClick(t, dataPosition, layoutPosition) }
        }



        if (payloads == null || payloads.isEmpty()) {
            onBindData(t, dataPosition, layoutPosition)
        } else {
            onBindData(t, dataPosition, layoutPosition, payloads)
        }
    }

    abstract fun onBindData(t: T, dataPosition: Int, adapterPosition: Int)


    open fun onBindData(t: T, dataPosition: Int, adapterPosition: Int, payloads: List<*>) {}



    fun setOnModuleItemClickListener(onModuleItemClickListener: OnModuleItemClickListener<T>?) {
        mOnModuleItemClickListener = onModuleItemClickListener
    }

    fun setOnModuleItemLongClickListener(onModuleItemLongClickListener: OnModuleItemLongClickListener<T>?) {
        mOnModuleItemLongClickListener = onModuleItemLongClickListener
    }

    open fun onViewDetachedFromWindow() {}
    open fun onViewAttachedToWindow() {}
}