package com.hello7890.adapter.listener;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 14:26
 * @describe
 */
public interface OnModuleItemLongClickListener<T> {
    boolean onModuleItemLongClick(T t, int dataPosition, int adapterPosition);
}
