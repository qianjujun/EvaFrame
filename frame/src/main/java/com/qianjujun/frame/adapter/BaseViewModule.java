package com.qianjujun.frame.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.databinding.ViewDataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/22 14:43
 * @describe
 */
public abstract class BaseViewModule<T> {
    protected List<T> dataList = new ArrayList<>();
    private DataChangeListener mDataChangeListener;




    public void setDataChangeListener(DataChangeListener dataChangeListener) {
        this.mDataChangeListener = dataChangeListener;
    }

    public abstract BaseViewHolder<T, ?> onCreateViewHolder(ViewGroup parent, int viewType);

    protected BaseViewHolder<? extends T,?> onCreateEmptyViewHolder(ViewGroup parent) {
        return new SpaceTViewHolder(parent);
    }


    public boolean isStickyItem(int dataPosition){
        return false;
    }





    public @IntRange(from = 0, to = 998)
    int getItemViewType(int dataPosition) {
        return 0;
    }

    //需要在界面上显示的数量
    public int getSize() {
        if(size()==0){
            return 1;
        }
        if(isGridLayout()){
            return size()+1;
        }
        return size();
    }













    void notifyItemInserted(int dataPosition,int itemCount){
        if (mDataChangeListener != null) {
            if(isGridLayout()){//多列的清空  getSize需要实时调整
                mDataChangeListener.onDataSizeChange(this);
            }else {
                mDataChangeListener.onDataSizeChangeByInserted(this,dataPosition,itemCount);
            }
        }
    }
    void notifyItemRemove(int dataPosition,int itemCount){
        if (mDataChangeListener != null) {
            if(isGridLayout()){
                mDataChangeListener.onDataSizeChange(this);
            }else {
                mDataChangeListener.onDataSizeChangeByRemove(this,dataPosition,itemCount);
            }
        }
    }






    public void notifyDataSetChanged() {
        if(mDataChangeListener==null){
            return;
        }
        mDataChangeListener.onDataSizeChange(this);
    }



    public void notifyItemRangeChanged(int dataPosition,int itemCount){
        if (mDataChangeListener != null) {
            mDataChangeListener.onDataItemRangeChanged(this,dataPosition,itemCount,startPosition+dataPosition);
        }
    }

    public void notifyDataChanged(){
        if (mDataChangeListener != null) {
            mDataChangeListener.onDataItemRangeChanged(this,0,size(),startPosition);
        }
    }

    public void notifyItemChanged(int dataPosition){
        if (mDataChangeListener != null) {
            mDataChangeListener.onDataItemRangeChanged(this,dataPosition,1,startPosition+dataPosition);
        }
    }

    public void notifyItemChanged(int dataPosition, Object payload) {
        if (mDataChangeListener != null) {
            mDataChangeListener.onDataItemRangeChanged(this, dataPosition, 1,dataPosition + startPosition, payload);
        }
    }


    protected abstract boolean isGridLayout();



    int getSpanSize(int position) {
        int maxSpanSize = getMaxSpanSize();
        if (maxSpanSize <= 1) {
            return 1;
        }
        int spanCount = _getSpanCount(position);
        if ((maxSpanSize % spanCount) != 0) {
            throw new RuntimeException("最大列数必须是所取列数的整数倍");
        }
        return maxSpanSize / spanCount;
    }


    private int _getSpanCount(int position) {
        int dataPosition = position - getStartPosition();
        if ((dataPosition == getSize() - 1)&&isGridLayout()) {//最后一列占满全列
            return 1;
        }
        if (isStickyItem(dataPosition)) {//吸顶item占满全列
            return 1;
        }
        return getSpanCount(dataPosition);
    }

    protected @IntRange(from = 1) int getSpanCount(int dataPosition){
        return 1;
    }










    private int typeViewFlag;
    private int startPosition;
    private Context context;
    private OnModuleItemClickListener<T> mItemClickListener;
    private OnModuleItemLongClickListener<T> mItemLongClickListener;
    public Context getContext() {
        return context;
    }

    void setTypeViewFlag(int typeViewFlag) {
        this.typeViewFlag = typeViewFlag;
    }

    public int getStartPosition() {
        return startPosition;
    }

    void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    final BaseViewHolder<? extends T, ?> createViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context = parent.getContext();
        }

        if(viewType==ViewType.EMPTY_VIEW_TYPE){
            return onCreateEmptyViewHolder(parent);
        }

        BaseViewHolder<T, ?> viewHolder = onCreateViewHolder(parent, viewType);
        viewHolder.setOnModuleItemClickListener(mItemClickListener);
        viewHolder.setOnModuleItemLongClickListener(mItemLongClickListener);
        return viewHolder;
    }

    final int getAdapterItemViewType(int dataPosition) {
        return typeViewFlag + handlerEmptyViewType(dataPosition);
    }


    int handlerEmptyViewType(int dataPosition){
        if (size() == 0 && getSize() > 0 && dataPosition == 0) {
            return ViewType.EMPTY_VIEW_TYPE;
        }
        return getItemViewType(dataPosition);
    }


    /**
     * 真实数据的数量
     *
     * @return
     */
    public final int size() {
        return dataList.size();
    }

    public void setOnModuleItemClickListener(OnModuleItemClickListener<T> itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnModuleItemLongClickListener(OnModuleItemLongClickListener<T> itemLongClickListener){
        this.mItemLongClickListener = itemLongClickListener;
    }

    public T getItem(int dataPosition) {
        if (dataPosition >= 0 && dataPosition < dataList.size()) {
            return dataList.get(dataPosition);
        }
        return null;
    }

    private int maxSpanSize = 1;

    void setMaxSpanSize(int maxSpanSize) {
        this.maxSpanSize = maxSpanSize;
    }

    int getMaxSpanSize() {
        return maxSpanSize;
    }

    List<Integer> getStickyLayoutPosition() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            if (isStickyItem(i)) {
                list.add(startPosition + i);
            }
        }
        return list;
    }
}
