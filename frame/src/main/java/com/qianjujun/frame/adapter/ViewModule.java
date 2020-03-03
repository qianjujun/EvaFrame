package com.qianjujun.frame.adapter;

import androidx.annotation.IntRange;

import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 16:59
 * @describe
 */
public abstract class ViewModule<T> extends BaseViewModule<T> implements OpData<T> {

    @Override
    protected boolean isGridLayout() {
        return false;
    }

    @Override
    public void setList(List<? extends T> list) {
        if (dataList.isEmpty()) {
            addAll(list);
            return;
        }
        this.dataList.clear();
        if (list != null) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public void setData(T data) {
        if (dataList.isEmpty()) {
            add(data);
            return;
        }
        this.dataList.clear();
        this.dataList.add(data);
        notifyItemInserted(0, 1);
    }


    @Override
    public void set(int location, T data) {
        if (location < 0 || location >= dataList.size()) {
            return;
        }
        dataList.set(location, data);
        notifyItemChanged(location);
    }

    @Override
    public void add(T data) {
        add(size(), data);
    }

    @Override
    public void add(int location, T data) {
        if (location < 0) {
            location = 0;
        }
        if (location > dataList.size()) {
            location = dataList.size();
        }
        dataList.add(location, data);
        notifyItemInserted(location, 1);
    }

    @Override
    public void addAll(List<? extends T> list) {
        addAll(size(), list);
    }

    @Override
    public void addAll(int location, List<? extends T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (location < 0) {
            location = 0;
        }
        if (location > dataList.size()) {
            location = dataList.size();
        }
        dataList.addAll(location, list);
        notifyItemInserted(location, list.size());
    }

    @Override
    public void remove(int location) {
        if (location < 0 || location >= size()) {
            return;
        }
        dataList.remove(location);
        notifyItemRemove(location, 1);
    }

    @Override
    public void remove(T data) {
        int location = dataList.indexOf(data);
        remove(location);
    }

    @Override
    public void removeAll(List<? extends T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        dataList.removeAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        if (this.dataList.isEmpty()) {
            return;
        }
        int size = size();
        this.dataList.clear();
        notifyItemRemove(0, size);
    }








}
