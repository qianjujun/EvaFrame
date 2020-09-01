package com.hello7890.adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.vh.SpaceViewHolder;

import java.util.List;


/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间： 2016/7/2
 */
public class RecyclerViewAdapter2 extends RecyclerViewAdapter{

    public RecyclerViewAdapter2(BaseViewModule... viewModules) {
        super(viewModules);
    }

    public RecyclerViewAdapter2(boolean handlerNullData, BaseViewModule... viewModules) {
        super(handlerNullData, viewModules);
    }

    @Override
    IAdapterHelp createAdapterHelper(BaseViewModule... viewModules) {
        return new AdapterHelpImpl(this,viewModules);
    }
}
