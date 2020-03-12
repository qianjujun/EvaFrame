package com.hello7890.adapter.vm;

import android.view.ViewGroup;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.BaseViewModule;

public abstract class WrapViewModule<T> extends XWrapViewModule<T>{
    @Override
    public BaseViewModule wrapVm(BaseViewModule viewModule) {
        return super.wrapVm(viewModule);
    }
}
