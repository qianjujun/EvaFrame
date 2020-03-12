package com.hello7890.adapter.vm;

import com.hello7890.adapter.data.GroupData;
import com.hello7890.adapter.data.OpData;

import java.util.ArrayList;
import java.util.List;

/**
 * 待优化  将每个item的信息缓存
 * @param <C>
 * @param <G>
 */
public class GroupVmHelper<C, G extends GroupData<C>> implements OpData<G> {
    private static final String TAG = "GroupVmHelper";

    private List<GroupDataWrap<C,G>> wrapList = new ArrayList<>();
    private final List<G> dataList;
    private final ViewModule<G> viewModule;
    private List<G> groupList = new ArrayList<>();

    public GroupVmHelper(List<G> dataList, ViewModule<G> viewModule) {
        this.dataList = dataList;
        this.viewModule = viewModule;
    }


    @Override
    public void setList(List<? extends G> list) {


        groupList.clear();
        if (list == null || list.isEmpty()) {
            return;
        }
        groupList.addAll(list);
        List<G> result = convert(list,new ArrayList<>());
        if (dataList.isEmpty()) {
            dataList.addAll(result);
            viewModule.notifyItemInserted(0, result.size());
        } else {
            dataList.clear();
            dataList.addAll(list);
            viewModule.notifyDataSetChanged();
        }
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




    public static class GroupDataWrap<C, G extends GroupData<C>> {
        private boolean isGroup;
        private G group;
        private C child;
        private int groupIndex;
        private int childIndex;

        public GroupDataWrap(G group, int groupIndex) {
            this.group = group;
            this.groupIndex = groupIndex;
        }

        public GroupDataWrap(G group, C child, int groupIndex, int childIndex) {
            this.group = group;
            this.child = child;
            this.groupIndex = groupIndex;
            this.childIndex = childIndex;
        }
    }



    private  <C,G extends GroupData<C>> List<G> convert(List<? extends G> list,List<GroupDataWrap<C,G>> temp){
        List<G> result = new ArrayList<>();
        List<C> childList;
        for(G groupData:list){
            result.add(groupData);//头部数据
            temp.add(new GroupDataWrap<>(groupData,groupList.size()));
            childList = groupData.getChildList();
            if(childList!=null){
                for(int i = 0;i<childList.size();i++){
                    result.add(groupData);
                    temp.add(new GroupDataWrap<>(groupData,groupData.getChild(i),groupList.size(),i));
                }
            }
            result.add(groupData);//尾部数据
            temp.add(new GroupDataWrap<>(groupData,groupList.size()));
        }
        return result;
    }
}
