package com.qianjujun.frame.adapter.group;

import java.util.List;

public interface IGroup {
    boolean isGroup();
    List<? extends IGroup> getChildList2();
}
