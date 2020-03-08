package com.qianjujun.rx;

import com.qianjujun.data.Data;
import com.qianjujun.frame.data.OnResponse;
import com.qianjujun.frame.exception.AppException;

public class OnDataCallBack<T> extends OnResponse<T, Data<T>> {
    @Override
    public void onSuccess(Data<T> date) throws AppException {
        super.onSuccess(date);
    }

    @Override
    public void onBegin() {
        super.onBegin();
    }

    @Override
    public void onEnd(boolean success, int state, String message) {
        super.onEnd(success, state, message);
    }

    @Override
    public void onSuccess(T t) throws AppException {
        super.onSuccess(t);
    }

    @Override
    public void onFail(AppException ex) {
        super.onFail(ex);
    }
}
