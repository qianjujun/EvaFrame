package com.hello7890.adapter.vm;


import com.hello7890.adapter.BaseViewModule;

abstract class XWrapViewModule<T> extends DataViewModule<T> {
    private BaseViewModule viewModule;
    BaseViewModule wrapVm(BaseViewModule viewModule){
        this.viewModule = viewModule;
        return this;
    }

    @Override
    protected BaseViewModule getWap() {
        return viewModule;
    }

    public BaseViewModule wrapWm(ViewModule viewModule){
        this.viewModule = viewModule;
        return this;
    }

}
