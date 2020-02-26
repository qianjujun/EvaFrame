package com.qianjujun.frame.adapter.group;

import android.util.Log;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

import com.qianjujun.frame.adapter.BaseViewHolder;
import com.qianjujun.frame.adapter.OnModuleItemClickListener;
import com.qianjujun.frame.adapter.OnModuleItemLongClickListener;
import com.qianjujun.frame.adapter.SpaceTViewHolder;
import com.qianjujun.frame.adapter.ViewModule;
import com.qianjujun.frame.adapter.ViewType;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupViewModule<C extends IGroup, G extends IGroup> extends ViewModule<IGroup> {





    public GroupViewModule(){
        setOnModuleItemClickListener(null);
    }

    private static final String TAG = "GroupViewModule";

    private List<IGroup> origialList = new ArrayList<>();


    private static class GroupInfo<G> {
        G group;
        int groupIndex;
    }


    protected void onGroupItemClick(G group, int dataPosition, int layoutPosition){

    }

    protected void onChildItemClick(C child, int dataPosition, int layoutPosition){

    }


    @Override
    public final void setOnModuleItemClickListener(OnModuleItemClickListener<IGroup> itemClickListener) {
        super.setOnModuleItemClickListener((iGroup, dataPosition, layoutPosition) -> {
            if(iGroup.isGroup()){
                onGroupItemClick((G) iGroup,dataPosition,layoutPosition);
            }else {
                onChildItemClick((C) iGroup,dataPosition,layoutPosition);
            }
        });
    }

    @Override
    public final void setOnModuleItemLongClickListener(OnModuleItemLongClickListener<IGroup> itemLongClickListener) {
        super.setOnModuleItemLongClickListener(itemLongClickListener);
    }

    private GroupInfo<G> findGroupInfo(int dataPosition) {
        GroupInfo<G> gGroupInfo = new GroupInfo<>();
        IGroup group = getItem(dataPosition);
        if (group.isGroup()) {
            int index = origialList.indexOf(group);
            if(index==-1){
                return null;
            }
            gGroupInfo.group = (G) group;
            gGroupInfo.groupIndex = index;
            return gGroupInfo;
        }

        for(int index = dataPosition-1;index>=0;index--){
            group = getItem(index);
            if(group.isGroup()){
                return findGroupInfo(index);
            }
        }
        return null;

    }


    public BaseViewHolder<IGroup, ?> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return onCreateGroupViewHolder(parent, viewType);
            case 2:
                return onCreateChildViewHolder(parent, viewType);
        }
        return new SpaceTViewHolder<>(parent);
    }


    public abstract class ChildViewHolder<CDB extends ViewDataBinding> extends BaseViewHolder<IGroup, CDB> {

        public ChildViewHolder(int layoutId, ViewGroup container) {
            super(layoutId, container);
        }

        @Override
        public final void onBindData(IGroup iGroup, int dataPosition, int layoutPosition) {

            C child = null;
            if(iGroup.isGroup()){//类型错误 不会出现
                onBindData(null,null,-1,-1,0,dataPosition,layoutPosition);
                return;
            }

            child = (C) iGroup;
            G group = null;
            int groupIndex = -1;
            int childIndex = -1;
            int childSize = 0;
            GroupInfo<G> groupInfo = findGroupInfo(dataPosition);
            if(groupInfo!=null){
                group = groupInfo.group;
                groupIndex = groupInfo.groupIndex;
                List<? extends IGroup> childList = group.getChildList2();
                if(childList!=null){
                    childIndex = childList.indexOf(child);
                    childSize = childList.size();
                }
            }

            onBindData(child,group,groupIndex,childIndex,childSize,dataPosition,layoutPosition);
        }

        public abstract void onBindData(C child, G group, int groupIndex, int childIndex,int childSize, int dataPosition, int layoutPosition);
    }

    public abstract class GroupViewHolder<CDB extends ViewDataBinding> extends BaseViewHolder<IGroup, CDB> {

        public GroupViewHolder(int layoutId, ViewGroup container) {
            super(layoutId, container);
        }

        @Override
        public void onBindData(IGroup iGroup, int dataPosition, int layoutPosition) {
            GroupInfo<G> groupInfo = findGroupInfo(dataPosition);
            G group = null;
            int groupIndex = -1;
            if(groupInfo!=null){
                group = groupInfo.group;
                groupIndex = groupInfo.groupIndex;
            }
            if(group==null&&iGroup.isGroup()){
                group = (G) iGroup;
            }
            onBindData(group,groupIndex,dataPosition,layoutPosition);
        }

        public abstract void onBindData(G group, int groupIndex, int dataPosition, int layoutPosition);
    }


    protected abstract ChildViewHolder<?> onCreateChildViewHolder(ViewGroup parent, int viewType);

    protected abstract GroupViewHolder<?> onCreateGroupViewHolder(ViewGroup parent, int viewType);


    protected int getGroupItemViewType() {
        return 1;
    }

    protected int getChildItemViewType() {
        return 2;
    }


    @Override
    public int getItemViewType(int dataPosition) {
        IGroup group = getItem(dataPosition);
        if (group == null) {
            return ViewType.SPACE_VIEW_TYPE;
        }
        if (group.isGroup()) {
            return getGroupItemViewType();
        } else {
            return getChildItemViewType();
        }
    }

    public void setGroupList(List<G> groupList) {
        setList(groupList);
    }

    public void addAllGroupList(List<G> groupList) {
        addAll(groupList);
    }

    public void setGroup(G group) {
        setData(group);
    }

    public void addGroup(G group) {
        add(group);
    }


    public void addChild(G group,C child){
        int index = origialList.indexOf(group);
        if(index==-1){
            return;
        }
        int dataPosition = dataList.indexOf(group);
        if(dataPosition==-1){
            return;
        }

        List<? extends IGroup> childList = group.getChildList2();
        if(childList==null){
            childList = new ArrayList<>();
        }
        int size = childList.size();


        int childPosition = dataPosition+size;
        super.add(childPosition,child);




    }


    private List<IGroup> convertList(List<? extends IGroup> list) {
        List<IGroup> results = new ArrayList<>();
        for (IGroup group : list) {
            if (group.isGroup()) {
                results.add(group);
                results.addAll(group.getChildList2());
            } else {
                Log.e(TAG, "convertList: ", new RuntimeException("子类型不能加入"));
            }
        }
        return results;
    }

    private List<IGroup> convertData(IGroup data) {
        List<IGroup> results = new ArrayList<>();
        if (data.isGroup()) {
            results.add(data);
            List<? extends IGroup> child = data.getChildList2();
            if(child!=null){
                results.addAll(data.getChildList2());
            }
        } else {
            Log.e(TAG, "convertList: ", new RuntimeException("子类型不能加入"));
        }
        return results;
    }


    @Deprecated
    @Override
    public void setList(List<? extends IGroup> list) {
        super.setList(convertList(list));
        origialList.clear();
        origialList.addAll(list);
    }

    @Deprecated
    @Override
    public void addAll(List<? extends IGroup> list) {
        super.addAll(size(), convertList(list));
        origialList.addAll(list);
    }

    @Deprecated
    @Override
    public void setData(IGroup data) {
        super.setList(convertData(data));
        origialList.clear();
        origialList.add(data);
    }

    @Deprecated
    @Override
    public void add(IGroup data) {
        super.addAll(size(),convertData(data));
        origialList.add(data);
    }


    @Deprecated
    @Override
    public void set(int location, IGroup data) {
        Log.e(TAG, "set: ", new RuntimeException("暂未实现"));
    }

    @Deprecated
    @Override
    public void add(int location, IGroup data) {
        Log.e(TAG, "add: ", new RuntimeException("暂未实现"));
    }

    @Deprecated
    @Override
    public void addAll(int location, List<? extends IGroup> list) {
        Log.e(TAG, "addAll: ", new RuntimeException("暂未实现"));
    }

    @Deprecated
    @Override
    public void remove(int location) {
        Log.e(TAG, "remove: ", new RuntimeException("暂未实现"));
    }

    @Deprecated
    @Override
    public void remove(IGroup data) {
        Log.e(TAG, "remove: ", new RuntimeException("暂未实现"));
    }

    @Deprecated
    @Override
    public void removeAll(List<? extends IGroup> list) {
        Log.e(TAG, "removeAll: ", new RuntimeException("暂未实现"));
    }

    @Deprecated
    @Override
    public void clear() {
        super.clear();
    }
}
