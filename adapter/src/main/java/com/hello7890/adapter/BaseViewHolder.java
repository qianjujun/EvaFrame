package com.hello7890.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hello7890.adapter.listener.OnModuleItemClickListener;
import com.hello7890.adapter.listener.OnModuleItemLongClickListener;

import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 16:26
 * @describe
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{

    private OnModuleItemClickListener<T> mOnModuleItemClickListener;
    private OnModuleItemLongClickListener<T> mOnModuleItemLongClickListener;
    public BaseViewHolder(@LayoutRes int layoutId, ViewGroup container) {
        super(LayoutInflater.from(container.getContext()).inflate(layoutId,container,false));
    }




    final void bindData(T t, int dataPosition, int layoutPosition, List<Object> payloads){

        //当正常更新数据 或者 分组
        if(payloads==null||payloads.isEmpty()||payloads.contains(AdapterHelpImpl.ADAPTER_SIZE_UPDATE_PAYLOAD)){
            if(mOnModuleItemClickListener!=null){
                itemView.setOnClickListener(v -> {
                    mOnModuleItemClickListener.onModuleItemClick(t,dataPosition,layoutPosition);
                });
            }
            if(mOnModuleItemLongClickListener!=null){
                itemView.setOnLongClickListener(v -> mOnModuleItemLongClickListener.onModuleItemLongClick(t,dataPosition,layoutPosition));
            }
        }

        if(payloads==null||payloads.isEmpty()){
            onBindData(t,dataPosition,layoutPosition);
        }else {
            onBindData(t,dataPosition,layoutPosition,payloads);
        }

    }


    public abstract void onBindData(T t, int dataPosition, int layoutPosition);


    public void onBindData(T t, int dataPosition, int layoutPosition, @NonNull List<Object> payloads) {

    }

    public void setOnModuleItemClickListener(OnModuleItemClickListener<T> onModuleItemClickListener) {
        this.mOnModuleItemClickListener = onModuleItemClickListener;
    }

    public void setOnModuleItemLongClickListener(OnModuleItemLongClickListener<T> onModuleItemLongClickListener) {
        this.mOnModuleItemLongClickListener = onModuleItemLongClickListener;
    }
}
