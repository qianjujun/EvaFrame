package com.hello7890.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseAdapterHelperImpl implements IAdapterHelp,ViewType,DataChangeListener{

    //缓存position对应的ViewModule  key 为position
    private Map<Integer,BaseViewModule> mPositionViewModules = new HashMap<>();


    private List<BaseViewModule> viewModuleList = new ArrayList<>();

    private final RecyclerViewAdapter adapter;



    private int size;

    private List<Integer> stickyPositionSet = new ArrayList<>();


    BaseViewModule findOneVmByName(String className){
        for(BaseViewModule viewModule:viewModuleList){
            if(TextUtils.equals(viewModule.getClass().getName(),className)){
                return viewModule;
            }
        }
        return null;
    }



    public BaseAdapterHelperImpl(RecyclerViewAdapter adapter){
        this.adapter = adapter;
    }


    public void updateViewModule(BaseViewModule... viewModules){
        if(viewModules==null){
            resetData();
            return;
        }
        viewModuleList.addAll(Arrays.asList(viewModules));
        initViewModules(viewModuleList);
    }


    protected void initViewModules(List<BaseViewModule> list){
        if(list==null||list.isEmpty()){
            return;
        }
        for(BaseViewModule viewModule:list){
            initViewModule(viewModule);
        }
        Collections.sort(stickyPositionSet);
    }

    protected void initViewModule(BaseViewModule viewModule) {
        onAddNewViewModule(viewModule);
        viewModule.setDataChangeListener(this);
        viewModule.setStartPosition(size);
        size += viewModule.getSize();
        stickyPositionSet.addAll(viewModule.getStickyLayoutPosition());
    }

    protected void resetData(){
        onRestData();
        mPositionViewModules.clear();
        stickyPositionSet.clear();
        size = 0;
        initViewModules(viewModuleList);
    }

    protected abstract void onAddNewViewModule(BaseViewModule viewModule);

    protected abstract void onRestData();







    @Override
    public BaseViewModule findViewModuleByPosition(int position) {
        if (mPositionViewModules.containsKey(position)) {
            return mPositionViewModules.get(position);
        }
        BaseViewModule result = null;
        for (BaseViewModule viewModule : viewModuleList) {
            int tempStartPosition = viewModule.getStartPosition();
            if (position >= tempStartPosition && position < tempStartPosition + viewModule.getSize()) {
                result = viewModule;
                break;
            }
        }
        if (result != null) {
            mPositionViewModules.put(position, result);
            return result;
        }


        throw new RuntimeException("After the data changes, must be called notif");
    }

    @Override
    public int getModuleViewType(int viewType) {
        return viewType % FLAG_VIEW_TYPE;
    }

    @Override
    public int getAdapterViewType(int position) {
        BaseViewModule viewModule = findViewModuleByPosition(position);
        int dataPosition = getDataPosition(viewModule,position);
        int vmItemType = viewModule.handlerStateAndEmptyViewType(dataPosition);
        if(vmItemType==SPACE_VIEW_TYPE){
            return GLOBE_NULL_DATA_VIEW_TYPE;
        }
        return viewModule.getAdapterItemViewTypeByVmItemType(vmItemType);
    }

    @Override
    public int getDataPosition(BaseViewModule viewModule, int position) {
        int dataPosition = position - viewModule.getStartPosition();
        if (dataPosition >= 0 && dataPosition < viewModule.getSize()) {
            return dataPosition;
        }
        throw new RuntimeException("After the data changes, must be called notif");
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if(layoutManager==null){
            return;
        }
        if(layoutManager instanceof GridLayoutManager){
            final GridLayoutManager glm = ((GridLayoutManager) layoutManager);
            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    BaseViewModule viewModule = findViewModuleByPosition(position);
                    viewModule.setMaxSpanSize(glm.getSpanCount());
                    return viewModule.getSpanSize(position);
                }
            });
        }

    }

    @Override
    public int findCurrentStickyPosition(int position) {
        if(stickyPositionSet.size()==0){
            return POSITION_NONE;
        }

        //遍历需要吸顶的position集合
        for(int index = 0;index<stickyPositionSet.size();index++){
            int stickyPos = stickyPositionSet.get(index);
            //如果吸顶position小于当前显示的position
            if(stickyPos<position){
                continue;
            }

            if(stickyPos==position){
                return position;
            }
            //取前一条
            if(index-1>=0&&position>stickyPositionSet.get(index-1)){
                return stickyPositionSet.get(index-1);
            }
        }

        int target = stickyPositionSet.get(stickyPositionSet.size()-1);
        if(target>position){
            return POSITION_NONE;
        }
        return target;
    }

    @Override
    public boolean isStickyItem(int position) {
        return stickyPositionSet.contains(position);
    }

    @Override
    public BaseViewHolder onCreateStickyViewHolder(ViewGroup container, int viewType) {
        return adapter.onCreateViewHolder(container,viewType);
    }

    @Override
    public void onBindStickyViewHolder(BaseViewHolder viewHolder, int position) {
        adapter.onBindViewHolder(viewHolder,position);
    }

    @Override
    public void onBindStickyViewHolder(BaseViewHolder viewHolder, int position, List<Object> payloads) {
        adapter.onBindViewHolder(viewHolder,position,payloads);
    }

    @Override
    public void changeLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        setLayoutManager(layoutManager);
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        adapter.registerAdapterDataObserver(observer);
    }


    @Override
    public void onDataItemRangeChanged(BaseViewModule viewModule, int dataPosition, int itemCount, int layoutPosition) {
        adapter.notifyItemRangeChanged(layoutPosition,itemCount);
    }

    @Override
    public void onDataItemRangeChanged(BaseViewModule viewModule, int dataPosition, int itemCount, int layoutPosition, Object payload) {
        adapter.notifyItemRangeChanged(layoutPosition,itemCount,payload);
    }

    @Override
    public void onDataSizeChangeByInserted(BaseViewModule viewModule, int positionStart, int itemCount) {
        resetData();
        int start = viewModule.getStartPosition()+positionStart;
        if(start>0){//==0时插入动画 会直接滑倒底部
            adapter.notifyItemRangeInserted(start,itemCount);
        }

        //adapter.notifyItemRangeChanged(start,size);
        adapter.notifyItemRangeChanged(start,size, ADAPTER_SIZE_UPDATE_PAYLOAD);
    }

    @Override
    public void onDataSizeChangeByRemove(BaseViewModule viewModule, int positionStart, int itemCount) {
        resetData();
        int start = viewModule.getStartPosition()+positionStart;
        adapter.notifyItemRangeRemoved(start,itemCount);
        //adapter.notifyItemRangeChanged(start,size);
        adapter.notifyItemRangeChanged(start,size, ADAPTER_SIZE_UPDATE_PAYLOAD);
    }

    @Override
    public void onDataSizeChange(BaseViewModule viewModule) {
        resetData();
        adapter.notifyDataSetChanged();
    }
}
