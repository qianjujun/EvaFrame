package com.hello7890.adapter.data;

import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 15:09
 * @describe
 */
public interface OpData<T> {
    void setList(List<? extends T> list);

    void setData(T data);

    void set(int location, T data);

    void add(T data);

    void add(int location, T data);

    void addAll(List<? extends T> list);

    void addAll(int location, List<? extends T> list);

    void remove(int location);

    void remove(T data);

    void removeAll(List<? extends T> data);

    //void clear();
}
