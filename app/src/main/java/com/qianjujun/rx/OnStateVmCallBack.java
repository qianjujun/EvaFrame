package com.qianjujun.rx;

import com.hello7890.adapter.data.ViewModuleState;
import com.hello7890.adapter.vm.ViewModule;
import com.qianjujun.frame.exception.AppException;

public class OnStateVmCallBack<T> extends OnDataCallBack<T>{
    private final ViewModule viewModule;

    public OnStateVmCallBack(ViewModule viewModule) {
        this.viewModule = viewModule;
    }

    @Override
    public void onBegin() {
        super.onBegin();
        viewModule.setState(ViewModuleState.LOADING);
    }

    @Override
    public void onSuccess(T t) throws AppException {
        viewModule.setState(ViewModuleState.EMPTY);//值为空状态 ，有数据自动切换到数据状态
    }

    @Override
    public void onFail(AppException ex) {
        super.onFail(ex);
        viewModule.setState(ViewModuleState.FAIL);
    }

    @Override
    public void onEmptyData() {
        super.onEmptyData();
        viewModule.setState(ViewModuleState.EMPTY);
    }



}
