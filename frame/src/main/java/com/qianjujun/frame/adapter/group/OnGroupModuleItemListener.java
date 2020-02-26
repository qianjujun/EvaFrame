package com.qianjujun.frame.adapter.group;

public interface OnGroupModuleItemListener<T extends IIGroup<T>> {
    void onChildItemClick(T child,T group,int groupIndex,int childIndex,int dataPosition);

    void onGroupItemClick(T group,int groupIndex,int dataPosition);
}
