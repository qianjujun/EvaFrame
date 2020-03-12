package com.hello7890.adapter.vm;

import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.XXXViewModule;
import com.hello7890.adapter.data.OpData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 16:59
 * @describe
 */
public abstract class ViewModule<T> extends XXXViewModule<T> implements OpData<T> {



    @Override
    public void setList(List<? extends T> list) {
        if(list==null||list.isEmpty()){
            clear();
            return;
        }

        int oldSize = getSize();
        if (!dataList.isEmpty()) {
            this.dataList.clear();
        }
        dataList.addAll(list);
        int newSize = getSize();

        updateDate(oldSize,newSize);
    }




    protected final void updateDate(int oldSize,int newSize){
        if(oldSize==newSize){
            notifyItemRangeChanged(0,newSize);
        }else if(oldSize>newSize){
            notifyItemRangeChanged(0,newSize);
            notifyItemRemove(newSize,oldSize-newSize);
        }else {
            notifyItemRangeChanged(0,oldSize);
            notifyItemInserted(oldSize,newSize-oldSize);
        }
    }

    protected final void updateDate(int oldSize,int newSize,int anchorPosition){
        if(oldSize==newSize){
            notifyItemRangeChanged(0,newSize);
        }else if(oldSize>newSize){
            notifyItemRangeChanged(0,anchorPosition);
            notifyItemRemove(anchorPosition,oldSize-newSize);
        }else {
            notifyItemRangeChanged(0,anchorPosition);
            notifyItemInserted(anchorPosition,newSize-oldSize);
        }
    }



    @Override
    public void setData(T data) {
        List<T> list = new ArrayList<>();
        list.add(data);
        setList(list);
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

        int oldSize = getSize();
        dataList.add(location, data);
        int newSize = getSize();
        updateDate(oldSize,newSize,location);
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
        int oldSize = getSize();
        dataList.addAll(location, list);
        int newSize = getSize();
        updateDate(oldSize,newSize);
    }

    @Override
    public void remove(int location) {
        if (location < 0 || location >= size()) {
            return;
        }

        int oldSize = getSize();
        dataList.remove(location);
        int newSize = getSize();
        updateDate(oldSize,newSize,location);
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
        int oldSize = getSize();
        dataList.removeAll(list);
        int newSize = getSize();
        updateDate(oldSize,newSize);
    }




    @Override
    protected final BaseViewModule getWrapViewModule() {
        return getSpanCount()>1?new SpaceViewModule():null;
    }
}
