package com.hello7890.adapter.vm;


import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.XXXViewModule;

public abstract class DataViewModule<T> extends XXXViewModule<T> {


    public void setData(T data){
        this.dataList.clear();
        this.dataList.add(data);
        notifyItemChanged(0);
    }


    public T getData() {
        return dataList.isEmpty()?null:dataList.get(0);
    }

    /**
     * 不允许
     * @return
     */
    @Override
    protected final BaseViewModule getWrapViewModule() {
        return getWap();
    }


    BaseViewModule getWap(){
        return null;
    }
}
