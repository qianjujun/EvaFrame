package com.hello7890.adapter.data;

import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/28 14:53
 * @describe
 */
public interface TwoGroupData<C1,C2> {
    List<C1> getChild1List();

    List<C2> getChild2List();

    C1 getChild1(int childPosition);

    C2 getChild2(int childPosition);

    int getChild1Size();

    int getChild2Size();
}
