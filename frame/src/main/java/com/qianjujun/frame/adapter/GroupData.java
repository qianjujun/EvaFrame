package com.qianjujun.frame.adapter;

import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/22 14:25
 * @describe
 */
public interface GroupData<T extends ChildData> {
     T convertToChild();

    List<T> getData();

}
