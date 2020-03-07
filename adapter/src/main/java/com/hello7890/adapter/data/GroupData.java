package com.hello7890.adapter.data;

import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/22 14:25
 * @describe
 */
public interface GroupData<T> {

    List<T> getChildList();

    T getChild(int childPosition);

    int getChildSize();

}
