package com.qianjujun.frame.adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

import com.qianjujun.frame.adapter.group.IIGroup;
import com.qianjujun.frame.adapter.group.OnGroupModuleItemListener;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupViewModule2<T extends IIGroup<T>> extends ViewModule<T> {
    private static final String TAG = "GroupViewModule2";

    public void setOnGroupModuleItemListener(OnGroupModuleItemListener<T> onGroupModuleItemListener) {

        setOnModuleItemClickListener((t, dataPosition, layoutPosition) -> {
            if (t.isGroup()) {
                onGroupModuleItemListener.onGroupItemClick(t, rawList.indexOf(t), dataPosition);
            } else {
                GroupInfo groupInfo = findGroupInfo(dataPosition);
                T group = null;
                int groupIndex = -1, childIndex = -1;
                if (groupInfo != null) {
                    group = groupInfo.group;
                    groupIndex = groupInfo.groupIndex;
                }
                onGroupModuleItemListener.onChildItemClick(t, group, groupIndex, childIndex, dataPosition);
            }
        });


    }

    @Override
    public BaseViewHolder<T, ?> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return onCreateGroupViewHolder(parent, viewType);
            case 2:
                return onCreateChildViewHolder(parent, viewType);
        }
        return new SpaceTViewHolder<>(parent);
    }


    private class GroupInfo {
        T group;
        int groupIndex;
    }

    private GroupInfo findGroupInfo(int dataPosition) {
        GroupInfo groupInfo = new GroupInfo();
        T group = getItem(dataPosition);
        if (group.isGroup()) {
            int index = rawList.indexOf(group);
            if (index == -1) {
                return null;
            }
            groupInfo.group = group;
            groupInfo.groupIndex = index;
            return groupInfo;
        }

        for (int index = dataPosition - 1; index >= 0; index--) {
            group = getItem(index);
            if (group.isGroup()) {
                return findGroupInfo(index);
            }
        }
        return null;
    }


    protected abstract ChildViewHolder<?> onCreateChildViewHolder(ViewGroup parent, int viewType);

    protected abstract GroupViewHolder<?> onCreateGroupViewHolder(ViewGroup parent, int viewType);

    public abstract class ChildViewHolder<CDB extends ViewDataBinding> extends BaseViewHolder<T, CDB> {

        public ChildViewHolder(int layoutId, ViewGroup container) {
            super(layoutId, container);
        }


        @Override
        public void onBindData(T t, int dataPosition, int layoutPosition) {
            GroupInfo groupInfo = findGroupInfo(dataPosition);
            T group = null;
            int groupIndex = -1, childIndex = -1;
            int childSize = 0;
            if (groupInfo != null) {
                group = groupInfo.group;
                groupIndex = groupInfo.groupIndex;
                List<T> childList = group.getChildList();
                if (childList != null) {
                    childIndex = childList.indexOf(t);
                    childSize = childList.size();
                }
            }
            onBindData(t, group, groupIndex, childIndex, childSize, dataPosition, layoutPosition);
        }

        public abstract void onBindData(T child, T group, int groupIndex, int childIndex, int childSize, int dataPosition, int layoutPosition);
    }

    public abstract class GroupViewHolder<CDB extends ViewDataBinding> extends BaseViewHolder<T, CDB> {

        public GroupViewHolder(int layoutId, ViewGroup container) {
            super(layoutId, container);
        }

        @Override
        public void onBindData(T t, int dataPosition, int layoutPosition) {
            int groupIndex = rawList.indexOf(t);
            onBindData(t, groupIndex, dataPosition, layoutPosition);
        }

        public abstract void onBindData(T group, int groupIndex, int dataPosition, int layoutPosition);
    }


    protected int getGroupItemViewType() {
        return 1;
    }

    protected int getChildItemViewType() {
        return 2;
    }


    @Override
    public int getItemViewType(int dataPosition) {
        T group = getItem(dataPosition);
        if (group == null) {
            return ViewType.SPACE_VIEW_TYPE;
        }
        if (group.isGroup()) {
            return getGroupItemViewType();
        } else {
            return getChildItemViewType();
        }
    }


    private List<T> convertList(List<? extends T> list) {
        List<T> results = new ArrayList<>();
        for (T group : list) {
            if (group.isGroup()) {
                results.add(group);
                results.addAll(group.getChildList());
            } else {
                Log.e(TAG, "convertList: "+group, new RuntimeException("子类型不能加入"));
            }
        }
        return results;
    }

    private List<T> convertList(T group) {
        List<T> results = new ArrayList<>();
        if (group.isGroup()) {
            results.add(group);
            results.addAll(group.getChildList());
        } else {
            Log.e(TAG, "convertList: ", new RuntimeException("子类型不能加入"));
        }
        return results;
    }


    private int getResultLocation(int location) {
        if (location >= rawList.size()) {
            return size();
        }
        if (location == 0) {
            return 0;
        }
        int resultLocation = 0;
        for (int index = 0; index < location; index++) {
            T group = rawList.get(index);
            resultLocation += 1;
            resultLocation += group.getChildSize();
        }
        return resultLocation;
    }


    private List<T> rawList = new ArrayList<>();


    public int addChildData(T group, T data) {
        if(group==null){
            Log.e(TAG, "addChildData: 没有找到对应的父类型");
            return -1;
        }
        int index = rawList.indexOf(group);
        if (index == -1) {
            return -1;
        }
        int dataPosition = dataList.indexOf(group);
        if (dataPosition == -1) {
            return -1;
        }
        group.addChild(data);
        int size = group.getChildSize();
        int childPosition = dataPosition + 1;
        super.add(childPosition, data);
        return childPosition;
    }

    public int addChildData(int groupIndex, T data) {
        if (groupIndex < 0 || groupIndex >= rawList.size()) {
            Log.e(TAG, "addChildData: 没有找到对应的父类型"+groupIndex);
            return -1;
        }
        T group = rawList.get(groupIndex);
        return addChildData(group, data);
    }


    @Override
    public void setList(List<? extends T> list) {
        if(dataList.isEmpty()){
            rawList.clear();
            addAll(list);
            return;
        }
        super.setList(convertList(list));
        rawList.clear();
        rawList.addAll(list);
    }

    @Override
    public void addAll(List<? extends T> list) {
        addAll(size(), list);
    }

    @Override
    public void addAll(int location, List<? extends T> list) {
        List<? extends T> results = convertList(list);
        int resultLocation = getResultLocation(location);
        super.addAll(resultLocation, results);
        rawList.addAll(list);
    }

    @Override
    public void add(T data) {
        add(size(), data);
    }

    @Override
    public void add(int location, T data) {
        int resultLocation = getResultLocation(location);
        super.addAll(resultLocation, convertList(data));
        rawList.add(data);
    }

    @Override
    public void remove(T data) {
        List<T> result = convertList(data);
        super.removeAll(result);
        rawList.remove(data);
    }

    @Override
    public void remove(int location) {
        T data = getItem(location);
        remove(data);
    }

    @Override
    public void removeAll(List<? extends T> list) {
        super.removeAll(convertList(list));
        rawList.removeAll(list);
    }

    @Override
    public void setData(T data) {
        List<T> list = new ArrayList<>();
        list.add(data);
        setList(list);
    }

    @Override
    public void set(int location, T data) {
        //super.set(location, data);
        // TODO: 2020/2/24 待实现
    }
}
