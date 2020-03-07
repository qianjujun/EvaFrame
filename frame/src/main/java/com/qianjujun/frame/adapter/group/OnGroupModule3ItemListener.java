package com.qianjujun.frame.adapter.group;

import com.qianjujun.frame.adapter.GroupData;

public interface OnGroupModule3ItemListener<C, G extends GroupData<C>> {
    void onChildItemClick(C child, G group, int groupIndex, int childIndex, int dataPosition);

    void onGroupItemClick(G group, int groupIndex, int dataPosition,boolean top);
}
