package com.qianjujun.frame.adapter.group;

import java.util.List;

public interface IIGroup<T extends IIGroup> {
    boolean isGroup();
    List<T> getChildList();
    int getChildSize();

    void addChild(T child);
}
