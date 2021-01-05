package com.hello7890.adapter.listener

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 14:26
 * @describe
 */
interface OnModuleItemLongClickListener<T> {
    fun onModuleItemLongClick(t: T, dataPosition: Int, adapterPosition: Int): Boolean
}