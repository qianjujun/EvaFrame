package com.qianjujun.frame.adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/22 14:25
 * @describe
 */
public abstract class GroupViewModule<G extends GroupData<C>,C extends ChildData,GDB extends ViewDataBinding> extends BaseViewModule<C> implements OpData<G>{

    private Map<Integer,Boolean> map = new HashMap<>();

    private static class GroupInfo<G,C>{
        private G group;
        private int groupIndex;

        public G getGroup() {
            return group;
        }

        public void setGroup(G group) {
            this.group = group;
        }

        public int getGroupIndex() {
            return groupIndex;
        }

        public void setGroupIndex(int groupIndex) {
            this.groupIndex = groupIndex;
        }
    }

    private GroupInfo<G,C> findGroupInfo(C child){
        if(child==null){
            return null;
        }
        if(!child.isGroup()){
            return null;
        }
        int index = convertGroupList.indexOf(child);
        if(index==-1){
            return null;
        }
        GroupInfo<G,C> gcGroupInfo = new GroupInfo<>();
        G group = groupList.get(index);
        gcGroupInfo.setGroup(group);
        gcGroupInfo.setGroupIndex(index);
        return gcGroupInfo;
    }

    private GroupInfo<G,C> findGroupInfo(int dataPosition){
        Log.d("qianjujun", "开始了-------------- = [" + dataPosition + "]");
        C item = null;
        for(int index = dataPosition-1;index>=0;index--){
            item = getItem(index);
            if(item==null){
                continue;
            }
            if(item.isGroup()){
                return findGroupInfo(item);
            }
        }
        return null;
    }




    public GroupViewModule() {
        setOnModuleItemClickListener((c, dataPosition, layoutPosition) -> {
            C child = getItem(dataPosition);
            if(child==null){
                return;
            }
            if(child.isGroup()){
                GroupInfo<G,C> groupInfo = findGroupInfo(child);
                if(groupInfo==null){
                    return;
                }
                G group = groupInfo.getGroup();
                int index = groupInfo.getGroupIndex();
                if(onGroupItemClickListener!=null){
                    onGroupItemClickListener.onModuleItemClick(group,dataPosition,layoutPosition);
                }

                if(!map.containsKey(index)){
                    map.put(index,false);
                    dataList.removeAll(group.getChildList());
                    notifyItemRemove(dataPosition+1,group.getChildList().size());
                    return;
                }

                if(map.get(index)){
                    map.put(index,false);
                    dataList.removeAll(group.getChildList());
                    notifyItemRemove(dataPosition+1,group.getChildList().size());
                }else {
                    map.put(index,true);
                    dataList.addAll(dataPosition+1,group.getChildList());
                    notifyItemInserted(dataPosition+1,group.getChildList().size());
                }
            }else {
                if(onChildItemClickListener!=null){
                    onChildItemClickListener.onModuleItemClick(child,dataPosition,layoutPosition);
                }
            }



        });
    }


    private OnModuleItemClickListener<G> onGroupItemClickListener;
    private OnModuleItemClickListener<C> onChildItemClickListener;


    public void setOnGroupItemClickListener(OnModuleItemClickListener<G> onGroupItemClickListener) {
        this.onGroupItemClickListener = onGroupItemClickListener;
    }

    public void setOnChildItemClickListener(OnModuleItemClickListener<C> onChildItemClickListener) {
        this.onChildItemClickListener = onChildItemClickListener;
    }

    @Override
    public BaseViewHolder<C, ?> onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){
            return onCreateGroupViewHolder(parent);
        }else {
            return onCreateChildViewHolder(parent);
        }
    }


    protected abstract @LayoutRes int getGroupLayoutId();

    protected abstract void onBindGroupViewHolder(GDB dataBing,G group,int groupPosition,int dataPosition,int layoutPosition);


    protected BaseViewHolder<C, ?> onCreateGroupViewHolder(ViewGroup parent){
        return new BaseViewHolder<C, GDB>(getGroupLayoutId(),parent) {
            @Override
            public void onBindData(C c, int dataPosition, int layoutPosition) {
                GroupInfo<G,C> groupInfo = findGroupInfo(c);
                if(groupInfo==null){
                    return;
                }
                G group = groupInfo.getGroup();
                int index = groupInfo.getGroupIndex();
                onBindGroupViewHolder(mDataBinding,group,index,dataPosition,layoutPosition);
            }
        };
    }


    protected abstract BaseViewHolder<C, ?> onCreateChildViewHolder(ViewGroup parent);


    public abstract class ChildViewHolder<CDB extends ViewDataBinding> extends BaseViewHolder<C, CDB>{

        public ChildViewHolder(int layoutId, ViewGroup container) {
            super(layoutId, container);
        }

        @Override
        public void onBindData(C c, int dataPosition, int layoutPosition) {
            GroupInfo<G,C> gcGroupInfo = findGroupInfo(dataPosition);
            int groupIndex=-1,childIndex = -1,childSize=-1;
            if(gcGroupInfo!=null){
                groupIndex = gcGroupInfo.groupIndex;
                G group = gcGroupInfo.group;
                List<C> childList = group.getChildList();
                if(childList!=null){
                    childIndex = childList.indexOf(c);
                    childSize = childList.size();
                }
            }
            onBindChildData(c,dataPosition,layoutPosition,groupIndex,childIndex,childSize);
        }

        public abstract void onBindChildData(C c, int dataPosition, int layoutPosition,int groupIndex,int childIndex,int childSize);
    }




    @Override
    public int getItemViewType(int dataPosition) {

        C childData = getItem(dataPosition);
        if(childData==null){
            return 0;
        }
        if(childData.isGroup()){
            return 1;
        }
        return 2;

    }

    protected int getChildType(int groupPostion,int childPosition,int dataPosition){
        return 0;
    }

    protected int getGroupType(int groupPostion,int dataPosition){
        return 0;
    }



    private List<G> groupList = new ArrayList<>();
    private List<C> convertGroupList = new ArrayList<>();

    @Override
    public void setList(List<? extends G> list) {
//        if (dataList.isEmpty()) {
//            addAll(list);
//            return;
//        }
        groupList.clear();
        groupList.addAll(list);
        List<C> data = Test.convertGroupData(list,convertGroupList);
        this.dataList.clear();
        if (list != null) {
            dataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public void setData(G data) {

    }

    @Override
    public void set(int location, G data) {

    }

    @Override
    public void add(G data) {

    }

    @Override
    public void add(int location, G data) {

    }

    @Override
    public void addAll(List<? extends G> list) {

    }

    @Override
    public void addAll(int location, List<? extends G> list) {

    }

    @Override
    public void remove(int location) {

    }

    @Override
    public void remove(G data) {

    }

    @Override
    public void removeAll(List<? extends G> data) {

    }

    @Override
    public void clear() {

    }








    @Override
    public int getSize() {
        if(size()==0){
            return 0;
        }
        return size() + 1;
    }

    private int _getSpanCount(int position) {
        int dataPosition = position - getStartPosition();
        if (dataPosition == getSize() - 1) {//最后一列占满全列
            return 1;
        }
        if (isStickyItem(dataPosition)) {//吸顶item占满全列
            return 1;
        }

        if(getItem(dataPosition).isGroup()){
            return 1;
        }

        return getSpanCount(dataPosition);
    }

    protected  @IntRange(from = 1)
    int getSpanCount(int dataPosition){
        return 1;
    }

    @Override
    protected boolean isGridLayout() {
        return false;
    }

    @Override
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



}
