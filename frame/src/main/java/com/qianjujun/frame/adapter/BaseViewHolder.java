package com.qianjujun.frame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.qianjujun.frame.utils.ClickHandler;

import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 16:26
 * @describe
 */
public abstract class BaseViewHolder<T,DB extends ViewDataBinding> extends RecyclerView.ViewHolder{
    protected DB mDataBinding;
    private OnModuleItemClickListener<T> mOnModuleItemClickListener;
    private OnModuleItemLongClickListener<T> mOnModuleItemLongClickListener;
    public BaseViewHolder(@LayoutRes int layoutId, ViewGroup container) {
        super(LayoutInflater.from(container.getContext()).inflate(layoutId,container,false));
        mDataBinding = DataBindingUtil.bind(itemView);
    }

    BaseViewHolder(View contentView){
        super(contentView);
        mDataBinding = DataBindingUtil.bind(itemView);
    }


    final void bindData(T t, int dataPosition, int layoutPosition, List<Object> payloads){

        if(payloads==null||payloads.isEmpty()||payloads.contains(AdapterHelpImpl.ANIM_UPDATE_PAYLOAD)){
            if(mOnModuleItemClickListener!=null){
                itemView.setOnClickListener(v -> {
                    if(ClickHandler.isValidClick()){
                        mOnModuleItemClickListener.onModuleItemClick(t,dataPosition,layoutPosition);
                    }
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
