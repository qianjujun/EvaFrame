package com.qianjujun.frame.adapter;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 16:24
 * @describe
 */
public abstract class SingleTypeViewModule<T,DB extends ViewDataBinding> extends ViewModule<T> implements OnModuleItemClickListener<T>{

    public SingleTypeViewModule(){
        setOnModuleItemClickListener(this);
    }

    @Override
    public BaseViewHolder<T, DB> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<T, DB>(getLayoutId(),parent) {
            @Override
            public void onBindData(T t, int dataPosition, int layoutPosition) {
                SingleTypeViewModule.this.onBindData(mDataBinding,t,dataPosition,layoutPosition);
            }
        };
    }

    protected abstract @LayoutRes int getLayoutId();

    protected abstract void onBindData(DB dataBinding,T t, int dataPosition, int layoutPosition);

    @Override
    public void onModuleItemClick(T t, int dataPosition, int layoutPosition) {

    }
}
