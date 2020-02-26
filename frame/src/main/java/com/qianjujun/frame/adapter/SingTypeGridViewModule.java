package com.qianjujun.frame.adapter;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/22 14:14
 * @describe
 */
public abstract class SingTypeGridViewModule<T,DB extends ViewDataBinding> extends GridViewModule<T>{
    @Override
    public BaseViewHolder<T, DB> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<T, DB>(getLayoutId(),parent) {
            @Override
            public void onBindData(T t, int dataPosition, int layoutPosition) {
                SingTypeGridViewModule.this.onBindData(mDataBinding,t,dataPosition,layoutPosition);
            }
        };
    }

    protected abstract @LayoutRes int getLayoutId();

    protected abstract void onBindData(DB dataBinding,T t, int dataPosition, int layoutPosition);
}
