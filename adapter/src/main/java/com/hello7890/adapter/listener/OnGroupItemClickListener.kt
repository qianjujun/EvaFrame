package com.hello7890.adapter.listener

import com.hello7890.adapter.data.GroupData

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/2 17:35
 * @describe
 */
interface OnGroupItemClickListener<C, G : GroupData<C>> {
    fun onGroupItemClick(group: G, groupIndex: Int, dataPosition: Int, top: Boolean)
}