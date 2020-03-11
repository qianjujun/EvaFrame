package com.hello7890.adapter;


import com.hello7890.adapter.vm.ViewModule;

public abstract class WrapViewModule<T> extends BaseViewModule<T>  {
    private ViewModule viewModule;
    public WrapViewModule wrap(ViewModule viewModule){
        this.viewModule = viewModule;
        return this;
    }

    @Override
    BaseViewModule getWrapViewModule() {
        return viewModule;
    }

    @Override
    public final int getSize() {
        return 1;
    }

    @Override
    protected final boolean isGridLayout() {
        return false;
    }


    public void setData(T data){
        this.dataList.clear();
        this.dataList.add(data);
        notifyItemChanged(0);
    }




}
