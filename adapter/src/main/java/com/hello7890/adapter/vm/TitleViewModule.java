package com.hello7890.adapter.vm;

import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.vh.BaseDbViewHolder;

import java.util.List;


/**
 * todo 暂未实现
 * 带title的viewModule
 * @param <T>
 */
abstract class TitleViewModule<T,DB extends ViewDataBinding> extends ViewModule<T>{

    private final ViewModule<T> viewModule;



    protected TitleViewModule(ViewModule<T> viewModule) {
        this.viewModule = viewModule;
        dataList.add(null);//代表Title头
    }






}
