package com.hello7890.adapter.vm;


import com.hello7890.adapter.BaseViewModule;

public abstract class DataViewModule<T> extends BaseViewModule<T> {


    public void setData(T data){
        this.dataList.clear();
        this.dataList.add(data);
        notifyItemChanged(0);
    }


    public T getData() {
        return dataList.isEmpty()?null:dataList.get(0);
    }




    BaseViewModule getWap(){
        return null;
    }
}
