package com.hello7890.adapter.listener;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 14:08
 * @describe
 */
public interface OnModuleItemClickListener<T> {
    void onModuleItemClick(T t, int dataPosition, int adapterPosition);
}
