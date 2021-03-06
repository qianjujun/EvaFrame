package com.hello7890.adapter.vm;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.listener.OnModuleItemClickListener;
import com.hello7890.adapter.vh.BaseDbViewHolder;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 16:24
 * @describe
 */
public abstract class SingleDbViewModule<T,DB extends ViewDataBinding> extends ViewModule<T> implements OnModuleItemClickListener<T> {

    public SingleDbViewModule(){
        setOnModuleItemClickListener(this);
    }

    @Override
    public final BaseDbViewHolder<T, DB> onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("SingleDbViewModule", "onCreateViewHolder() called with: parent = [" + "this" + "], viewType = [" + viewType + "]");

        return new BaseDbViewHolder<T, DB>(getLayoutId(),parent) {
            @Override
            public void onBindData(T t, int dataPosition, int adapterPosition) {
                SingleDbViewModule.this.onBindData(mDataBinding,t,dataPosition, adapterPosition);
            }
        };
    }

    protected abstract @LayoutRes int getLayoutId();

    protected abstract void onBindData(DB dataBinding,T t, int dataPosition, int layoutPosition);

    @Override
    public void onModuleItemClick(T t, int dataPosition, int adapterPosition) {

    }


}
