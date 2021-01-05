package com.hello7890.adapter;



public abstract class DataViewModule<T> extends BaseViewModule<T> {


    public void setData(T data){
        _getDataList().clear();
        _getDataList().add(data);
        notifyItemChanged(0);
    }
    public T getData() {
        return _getDataList().isEmpty()?null:_getDataList().get(0);
    }
}
