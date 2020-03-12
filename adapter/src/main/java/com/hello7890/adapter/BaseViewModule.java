package com.hello7890.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.IntRange;

import com.hello7890.adapter.listener.DataStateChangeListener;
import com.hello7890.adapter.listener.OnModuleItemClickListener;
import com.hello7890.adapter.listener.OnModuleItemLongClickListener;
import com.hello7890.adapter.vh.NoneTViewHolder;
import com.hello7890.adapter.vh.SpaceViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/22 14:43
 * @describe
 */
public abstract class BaseViewModule<T> implements ViewType {
    protected List<T> dataList = new ArrayList<>();

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    private List<DataStateChangeListener> dataStateChangeListeners = new ArrayList<>();


    BaseViewModule getWrapViewModule() {
        return null;
    }



    public void notifyLoading(){
        for(DataStateChangeListener stateChangeListener:dataStateChangeListeners){
            stateChangeListener.onLoading();
        }
    }

    public void notifyForceLoading(){
        clear();
        for(DataStateChangeListener stateChangeListener:dataStateChangeListeners){
            stateChangeListener.onLoading();
        }
    }




    public void notifyError(int errorCode,String message){
        for(DataStateChangeListener stateChangeListener:dataStateChangeListeners){
            stateChangeListener.onFail(errorCode,message);
        }
    }


    public void notifyDataSizeChange(){
        for(DataStateChangeListener stateChangeListener:dataStateChangeListeners){
            stateChangeListener.onSizeChange(size());
        }
    }



    public final void notifyItemInserted(int dataPosition, int itemCount) {
        for (DataChangeListener mDataChangeListener : dataChangeListeners) {
            mDataChangeListener.onDataSizeChangeByInserted(this, dataPosition, itemCount);
        }
        notifyDataSizeChange();
    }

    public final void notifyItemRemove(int dataPosition, int itemCount) {
        for (DataChangeListener mDataChangeListener : dataChangeListeners) {
            mDataChangeListener.onDataSizeChangeByRemove(this, dataPosition, itemCount);
        }
        notifyDataSizeChange();
    }


    public final void notifyDataSetChanged() {
        for (DataChangeListener mDataChangeListener : dataChangeListeners) {
            mDataChangeListener.onDataSizeChange(this);
        }
        notifyDataSizeChange();

    }


    public final void notifyItemRangeChanged(int dataPosition, int itemCount) {
        for (DataChangeListener mDataChangeListener : dataChangeListeners) {
            mDataChangeListener.onDataItemRangeChanged(this, dataPosition, itemCount, startPosition + dataPosition);
        }
    }

    public final void notifyDataChanged() {
        for (DataChangeListener mDataChangeListener : dataChangeListeners) {
            mDataChangeListener.onDataItemRangeChanged(this, 0, size(), startPosition);
        }
    }

    public final void notifyItemChanged(int dataPosition) {
        for (DataChangeListener mDataChangeListener : dataChangeListeners) {
            mDataChangeListener.onDataItemRangeChanged(this, dataPosition, 1, startPosition + dataPosition);
        }
    }

    public final void notifyItemChanged(int dataPosition, Object payload) {
        for (DataChangeListener mDataChangeListener : dataChangeListeners) {
            mDataChangeListener.onDataItemRangeChanged(this, dataPosition, 1, dataPosition + startPosition, payload);
        }
    }







    public void clear() {
        if (this.dataList.isEmpty()) {
            return;
        }
        int size = size();
        this.dataList.clear();
        notifyItemRemove(0, size);
        //notifyDataSetChanged();
    }



    public void addDataChangeListener(DataChangeListener dataChangeListener) {
        if(dataChangeListener==null){
            return;
        }
        if(!dataChangeListeners.contains(dataChangeListener)){
            dataChangeListeners.add(dataChangeListener);
        }
    }

    public void removeDataChangeListener(DataChangeListener dataChangeListener){
        dataChangeListeners.remove(dataChangeListener);
    }

    public void removeDataStateChangeListener(DataStateChangeListener dataStateChangeListener) {
        dataStateChangeListeners.remove(dataStateChangeListener);
    }

    public void addDataStateChangeListener(DataStateChangeListener dataStateChangeListener) {
        if(dataStateChangeListener==null){
            return;
        }
        if(!dataStateChangeListeners.contains(dataStateChangeListener)){
            dataStateChangeListeners.add(dataStateChangeListener);
        }
    }

    public boolean isStickyItem(int dataPosition) {
        return false;
    }


    public List<T> getDataList() {
        return new ArrayList<>(dataList);
    }

    /**
     * @return 返回源数据  操作后必须调用相关更新方法
     */
    public final List<T> _getDataList() {
        return dataList;
    }


    public @IntRange(from = MIN_NORMAL_VIEW_TYPE, to = MAX_NORMAL_VIEW_TYPE)
    int getItemViewType(int dataPosition) {
        return 0;
    }

    //需要在界面上显示的数量
    public int getSize() {
        return size();
    }




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
        if (isStickyItem(dataPosition)) {//吸顶item占满全列
            return 1;
        }
        return getSpanCount(dataPosition);
    }


    /**
     * 特别注意：
     * 约定
     *
     * @param dataPosition
     * @return 约定只返回1或者其他如 返回1或3 1或4 1或5
     * <p>
     * --- ---  2
     * -------  1
     * ---      2
     * -------  1
     * <p>
     * 如返回 3|2|1
     * ---  ---  2
     * -- -- --  3
     * --- --    2|3
     * -- -- --  3
     * --------  1
     * 此混合模式很难找到当前item属于该行的第几列  如需要画分割线 则尽量避免此种组合
     * <p>
     * 多种列结构 建议使用多个module组合非方式
     */
    @Deprecated
    public @IntRange(from = 1)
    int getSpanCount(int dataPosition) {
        return getSpanCount();
    }


    public int getSpanCount() {
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

    final BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        switch (viewType) {
            case EMPTY_VIEW_TYPE:
                return onCreateEmptyViewHolder(parent);
            case LOADING_VIEW_TYPE:
                return onCreateLoadingHolder(parent);
            case FAIL_VIEW_TYPE:
                return onCreateFailHolder(parent);
        }

        BaseViewHolder<T> viewHolder = onCreateViewHolder(parent, viewType);
        return viewHolder;
    }


    public abstract BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType);

    protected NoneTViewHolder onCreateEmptyViewHolder(ViewGroup parent) {
        return new SpaceViewHolder(parent);
    }

    protected BaseViewHolder onCreateLoadingHolder(ViewGroup parent) {
        return new SpaceViewHolder(parent);
    }

    protected BaseViewHolder onCreateFailHolder(ViewGroup parent) {
        return new SpaceViewHolder(parent);
    }





    final int getAdapterItemViewTypeByVmItemType(int itemType) {
        return typeViewFlag + itemType;
    }


    int handlerStateAndEmptyViewType(int dataPosition) {
        if (getItem(dataPosition) == null) {//过滤空数据类型
            return ViewType.SPACE_VIEW_TYPE;
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

    public void setOnModuleItemLongClickListener(OnModuleItemLongClickListener<T> itemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener;
    }

    public OnModuleItemClickListener<T> getItemClickListener() {
        return mItemClickListener;
    }

    public OnModuleItemLongClickListener<T> getItemLongClickListener() {
        return mItemLongClickListener;
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
