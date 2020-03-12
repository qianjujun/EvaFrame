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
        viewModule.notifyLoading();
    }

    @Override
    public void onSuccess(T t) throws AppException {

    }

    @Override
    public void onFail(AppException ex) {
        super.onFail(ex);
        viewModule.notifyError(ex.getErrorCode(),ex.getMessage());
    }

    @Override
    public void onEmptyData() {
        super.onEmptyData();
        viewModule.notifyDataSizeChange();
    }



}
