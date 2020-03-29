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
public class RecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private boolean handlerNullData;

    private IAdapterHelp adapterHelper;


    public IAdapterHelp getAdapterHelper() {
        return adapterHelper;
    }

    public RecyclerViewAdapter(BaseViewModule... viewModules){
        this(true,viewModules);
    }

    public RecyclerViewAdapter(boolean handlerNullData,BaseViewModule... viewModules){
        adapterHelper = new AdapterHelpImpl2(this,viewModules);
        this.handlerNullData = handlerNullData;
    }

    boolean isHandlerNullData() {
        return handlerNullData;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder() called with: parent = [" + "parent" + "], viewType = [" + viewType + "]");
        if(viewType==ViewType.GLOBE_NULL_DATA_VIEW_TYPE){
            return new SpaceViewHolder(parent);
        }
        BaseViewModule viewModule = adapterHelper.findViewModule(viewType);
        int moduleViewType = adapterHelper.getModuleViewType(viewType);
        return viewModule.createViewHolder(parent,moduleViewType);

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        BaseViewModule viewModule = adapterHelper.findViewModuleByPosition(position);
        int dataPosition = adapterHelper.getDataPosition(viewModule,position);
        Object data = viewModule.getItem(dataPosition);
        holder.setOnModuleItemClickListener(viewModule.getItemClickListener());
        holder.setOnModuleItemLongClickListener(viewModule.getItemLongClickListener());
        holder.bindData(data,dataPosition,position,null);
        Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
        if(payloads==null||payloads.isEmpty()){
            onBindViewHolder(holder,position);
            return;
        }
        BaseViewModule viewModule = adapterHelper.findViewModuleByPosition(position);
        holder.setOnModuleItemClickListener(viewModule.getItemClickListener());
        holder.setOnModuleItemLongClickListener(viewModule.getItemLongClickListener());
        int dataPosition = adapterHelper.getDataPosition(viewModule,position);
        Object data = viewModule.getItem(dataPosition);
        holder.bindData(data,dataPosition,position,payloads);
    }

    @Override
    public int getItemCount() {
        return adapterHelper.getSize();
    }

    @Override
    public int getItemViewType(int position) {
        return adapterHelper.getAdapterViewType(position);
    }

    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(BaseViewHolder holder) {
        return super.onFailedToRecycleView(holder);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        adapterHelper.setLayoutManager(recyclerView.getLayoutManager());
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();

    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
    }



}
