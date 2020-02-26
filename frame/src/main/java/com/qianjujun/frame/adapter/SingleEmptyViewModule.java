package com.qianjujun.frame.adapter;

import androidx.databinding.ViewDataBinding;

public abstract class SingleEmptyViewModule<T,DB extends ViewDataBinding> extends SingleTypeViewModule<T,DB> {
    @Override
    public int getSize() {
        return size()+1;
    }


}
