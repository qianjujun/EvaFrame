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

        //当正常更新数据 或者 size发生变化
        if(payloads==null||payloads.isEmpty()||payloads.contains(AdapterHelpImpl2.ADAPTER_SIZE_UPDATE_PAYLOAD)){
            // TODO: 2020/3/9 预留  只需在这个地方需要重新绑定事件   暂未测试正确性
        }

        if(dataPosition==0&&t==null){//状态Holder
            itemView.setOnClickListener(null);
            itemView.setOnLongClickListener(null);
        }else {
            if(mOnModuleItemClickListener!=null){
                itemView.setOnClickListener(v -> {
                    mOnModuleItemClickListener.onModuleItemClick(t,dataPosition,layoutPosition);
                });
            }else {
                itemView.setOnClickListener(null);
            }
            if(mOnModuleItemLongClickListener!=null){
                itemView.setOnLongClickListener(v -> mOnModuleItemLongClickListener.onModuleItemLongClick(t,dataPosition,layoutPosition));
            }else {
                itemView.setOnLongClickListener(null);
            }
        }

        if(payloads==null||payloads.isEmpty()){
            onBindData(t,dataPosition,layoutPosition);
        }else {
            onBindData(t,dataPosition,layoutPosition,payloads);
        }

    }


    public abstract void onBindData(T t, int dataPosition, int adapterPosition);


    public void onBindData(T t, int dataPosition, int adapterPosition, @NonNull List<Object> payloads) {

    }

    public void setOnModuleItemClickListener(OnModuleItemClickListener<T> onModuleItemClickListener) {
        this.mOnModuleItemClickListener = onModuleItemClickListener;
    }

    public void setOnModuleItemLongClickListener(OnModuleItemLongClickListener<T> onModuleItemLongClickListener) {
        this.mOnModuleItemLongClickListener = onModuleItemLongClickListener;
    }


    public void onViewDetachedFromWindow(){

    }

    public void onViewAttachedToWindow(){

    }
}
