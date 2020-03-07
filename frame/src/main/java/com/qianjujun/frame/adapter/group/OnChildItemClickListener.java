package com.qianjujun.frame.adapter.group;

import com.qianjujun.frame.adapter.GroupData;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/2 17:35
 * @describe
 */
public interface OnChildItemClickListener<C, G extends GroupData<C>> {
    void onChildItemClick(C child, G group, int groupIndex, int childIndex, int dataPosition);
}
