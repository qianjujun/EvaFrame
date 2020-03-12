package com.hello7890.adapter;


import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hello7890.adapter.data.ViewModuleState;
import com.hello7890.adapter.listener.OnModuleItemClickListener;
import com.hello7890.adapter.listener.OnModuleItemLongClickListener;
import com.hello7890.adapter.vh.NoneTViewHolder;

import java.util.List;

/**
 * 莫要继承我  用于过渡包权限
 * @param <T>
 */
public abstract class XXXViewModule<T> extends BaseViewModule<T>{

    @Override
    protected BaseViewModule getWrapViewModule() {
        return super.getWrapViewModule();
    }
}
