package com.hello7890.adapter.vm;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.R;
import com.hello7890.adapter.data.GroupDataHelper;
import com.hello7890.adapter.data.Group2Data;
import com.hello7890.adapter.databinding.SpaceVmBinding;
import com.hello7890.adapter.vh.BaseDbViewHolder;
import com.hello7890.adapter.vh.SpaceTViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/28 14:55
 * @describe 暂未实现
 */
public abstract class Group2ViewModule<C1, C2, G extends Group2Data<C1, C2>> extends ViewModule<G> {
    public static final String TAG = "TwoGroupViewModule";
    public static final int DATA_TYPE_GROUP_TOP = 1;
    public static final int DATA_TYPE_CHILD1 = 2;
    public static final int DATA_TYPE_GROUP_BOTTOM1 = 3;
    public static final int DATA_TYPE_CHILD2 = 4;
    public static final int DATA_TYPE_GROUP_BOTTOM2 = 5;
    public static final int DATA_TYPE_UNKNOWN = 6;
    private DataInfo mDataInfo;


    private List<G> groupList = new ArrayList<>();

    @Override
    public BaseViewHolder<G> onCreateViewHolder(ViewGroup parent, int viewType) {
        int type = viewType % 5;
        switch (type) {
            case 0:
                return onCreateGroupTopViewHolder(parent, viewType / 5);
            case 1:
                return onCreateChild1ViewHolder(parent, (viewType - 1) / 5);
            case 2:
                return onCreateChild1BottomViewHolder(parent,(viewType - 2) / 5);
            case 3:
                return onCreateChild2ViewHolder(parent, (viewType - 3) / 5);
            case 4:
                return onCreateChild2BottomViewHolder(parent,(viewType - 4) / 5);
            default:
                return new SpaceTViewHolder<>(parent);
        }
    }

    protected abstract GroupViewHolder<? extends ViewDataBinding> onCreateGroupTopViewHolder(ViewGroup parent, int viewType);
    protected abstract Child1ViewHolder<? extends ViewDataBinding> onCreateChild1ViewHolder(ViewGroup parent, int viewType);
    protected abstract Child2ViewHolder<? extends ViewDataBinding> onCreateChild2ViewHolder(ViewGroup parent, int viewType);

    protected GroupViewHolder<? extends ViewDataBinding> onCreateChild1BottomViewHolder(ViewGroup parent, int viewType){
        return new EmptyGroupViewHolder(parent);
    }

    protected GroupViewHolder<? extends ViewDataBinding> onCreateChild2BottomViewHolder(ViewGroup parent, int viewType){
        return new EmptyGroupViewHolder(parent);
    }

    private class EmptyGroupViewHolder extends GroupViewHolder<SpaceVmBinding>{

        public EmptyGroupViewHolder(ViewGroup container) {
            super(R.layout.space_vm, container);
        }

        @Override
        protected void onBindData(SpaceVmBinding dataBing, G group, int groupIndex, int dataPosition, int adapterPosition) {

        }
    }


    public abstract class GroupViewHolder<DB extends ViewDataBinding> extends BaseDbViewHolder<G,DB>{

        public GroupViewHolder(int layoutId, ViewGroup container) {
            super(layoutId, container);
        }

        @Override
        public void onBindData(G g, int dataPosition, int adapterPosition) {
            DataInfo dataInfo = getDataType(dataPosition);
            onBindData(mDataBinding,g,dataInfo.groupPosition,dataPosition,adapterPosition);
        }

        @Override
        public void onBindData(G g, int dataPosition, int adapterPosition, @NonNull List<Object> payloads) {
            DataInfo dataInfo = getDataType(dataPosition);
            onBindData(mDataBinding,g,dataInfo.groupPosition,dataPosition,adapterPosition,payloads);
        }

        protected abstract void onBindData(DB dataBing, G group, int groupIndex, int dataPosition, int adapterPosition);

        protected void onBindData(DB dataBing, G group, int groupIndex, int dataPosition, int adapterPosition, @NonNull List<Object> payloads) {

        }
    }





    public abstract class Child1ViewHolder<DB extends ViewDataBinding> extends BaseDbViewHolder<G,DB>{

        public Child1ViewHolder(int layoutId, ViewGroup container) {
            super(layoutId, container);
        }

        @Override
        public void onBindData(G g, int dataPosition, int adapterPosition) {
            DataInfo dataInfo = getDataType(dataPosition);
            C1 child = g.getChild1(dataInfo.childPosition);
            onBindData(mDataBinding,g,child,dataInfo.groupPosition,dataInfo.childPosition,dataPosition,adapterPosition);
        }

        @Override
        public void onBindData(G g, int dataPosition, int adapterPosition, @NonNull List<Object> payloads) {
            DataInfo dataInfo = getDataType(dataPosition);
            C1 child = g.getChild1(dataInfo.childPosition);
            onBindData(mDataBinding,g,child,dataInfo.groupPosition,dataInfo.childPosition,dataPosition,adapterPosition,payloads);
        }

        protected abstract void onBindData(DB dataBing, G group, C1 child, int groupIndex, int childIndex, int dataPosition, int adapterPosition);

        protected void onBindData(DB dataBing, G group, C1 child, int groupIndex, int childIndex, int dataPosition, int adapterPosition, List<Object> payloads) {

        }
    }

    public abstract class Child2ViewHolder<DB extends ViewDataBinding> extends BaseDbViewHolder<G,DB>{

        public Child2ViewHolder(int layoutId, ViewGroup container) {
            super(layoutId, container);
        }

        @Override
        public void onBindData(G g, int dataPosition, int adapterPosition) {
            DataInfo dataInfo = getDataType(dataPosition);
            C2 child = g.getChild2(dataInfo.childPosition);
            onBindData(mDataBinding,g,child,dataInfo.groupPosition,dataInfo.childPosition,dataPosition,adapterPosition);
        }

        @Override
        public void onBindData(G g, int dataPosition, int adapterPosition, @NonNull List<Object> payloads) {
            DataInfo dataInfo = getDataType(dataPosition);
            C2 child = g.getChild2(dataInfo.childPosition);
            onBindData(mDataBinding,g,child,dataInfo.groupPosition,dataInfo.childPosition,dataPosition,adapterPosition,payloads);
        }

        protected abstract void onBindData(DB dataBing, G group, C2 child, int groupIndex, int childIndex, int dataPosition, int adapterPosition);

        protected void onBindData(DB dataBing, G group, C2 child, int groupIndex, int childIndex, int dataPosition, int adapterPosition, List<Object> payloads) {

        }
    }





    public G getGroup(int groupPosition){
        if(groupPosition<0||groupPosition>=groupList.size()){
            return null;
        }
        return groupList.get(groupPosition);
    }







    protected @IntRange(from = MIN_NORMAL_VIEW_TYPE, to = MAX_TWO_GROUP_VIEW_TYPE)
    int getTopGroupViewType(G group,int groupIndex) {
        return 0;
    }

    protected @IntRange(from = MIN_NORMAL_VIEW_TYPE, to = MAX_TWO_GROUP_VIEW_TYPE)
    int getChild1ViewType(G group,int groupPosition, int child1Position) {
        return 0;
    }

    protected @IntRange(from = MIN_NORMAL_VIEW_TYPE, to = MAX_TWO_GROUP_VIEW_TYPE)
    int getChild1BottomViewType(G group,int groupPosition) {
        return 0;
    }

    protected @IntRange(from = MIN_NORMAL_VIEW_TYPE, to = MAX_TWO_GROUP_VIEW_TYPE)
    int getChild2ViewType(G group,int groupPosition, int child1Position) {
        return 0;
    }

    protected @IntRange(from = MIN_NORMAL_VIEW_TYPE, to = MAX_TWO_GROUP_VIEW_TYPE)
    int getChild2BottomViewType(G group,int groupPosition) {
        return 0;
    }


    @Override
    public int getItemViewType(int dataPosition) {
        G group = getItem(dataPosition);
        if(group==null){
            return getSpaceViewType();
        }

        DataInfo dataInfo = getDataType(dataPosition);

        int dataType = dataInfo.dataType;
        int groupIndex = dataInfo.groupPosition;
        Log.d(TAG, "getItemViewType() called with: dataPosition = [" + dataPosition + "]"+dataType);
        switch (dataType) {
            case DATA_TYPE_GROUP_TOP:
                return getTopGroupViewType(group,groupIndex) * 5;
            case DATA_TYPE_CHILD1:
                return getChild1ViewType(group,groupIndex, dataInfo.childPosition) * 5 + 1;
            case DATA_TYPE_GROUP_BOTTOM1:
                return getChild1BottomViewType(group,groupIndex) * 5 + 2;
            case DATA_TYPE_CHILD2:
                return getChild2ViewType(group,groupIndex, dataInfo.childPosition) * 5 + 3;
            case DATA_TYPE_GROUP_BOTTOM2:
                return getChild2BottomViewType(group,groupIndex)+4;
            default:
                return getSpaceViewType();
        }
    }

    private int getSpaceViewType() {
        return SPACE_VIEW_TYPE;
    }

    public static class DataInfo<G extends Group2Data> {
        int dataType;
        int groupPosition;
        int childPosition;
        G data;

        public G getData() {
            return data;
        }
        public int getDataType() {
            return dataType;
        }
        public int getGroupPosition() {
            return groupPosition;
        }
        public int getChildPosition() {
            return childPosition;
        }
        public boolean isTopChild(int span){
            return childPosition/span==0;
        }

        public boolean isBottomChild(int span){
            return data==null?false:childPosition/span==(data.getChild1Size()-1)/span;
        }
    }


    private void  logDataType(int dataPosition,int firstDataIndex,int groupPosition,int child1Size,int child1Start,int child1End){
        Log.d(TAG, "logDataType() called with: dataPosition = [" + dataPosition + "], firstDataIndex = [" + firstDataIndex + "], groupPosition = [" + groupPosition + "], child1Size = [" + child1Size + "], child1Start = [" + child1Start + "], child1End = [" + child1End + "]");
    }

    public DataInfo<G> getDataType(int dataPosition) {
        G data = getItem(dataPosition);
        int firstDataIndex = dataList.indexOf(data);
        int groupPosition = groupList.indexOf(data);
        int child1Size = data.getChild1Size();
        int child2Size = data.getChild2Size();
        if (mDataInfo == null) {
            mDataInfo = new DataInfo();
        }
        mDataInfo.data = data;
        if (firstDataIndex == dataPosition) {
            mDataInfo.dataType = DATA_TYPE_GROUP_TOP;
            mDataInfo.groupPosition = groupPosition;
            mDataInfo.childPosition = -1;
            return mDataInfo;
        }
        int child1Start = firstDataIndex + 1;
        int child1End = child1Start + child1Size;


        logDataType(dataPosition,firstDataIndex,groupPosition,child1Size,child1Start,child1End);

        if (dataPosition >= child1Start && dataPosition < child1End) {
            mDataInfo.dataType = DATA_TYPE_CHILD1;
            mDataInfo.groupPosition = groupPosition;
            mDataInfo.childPosition = dataPosition - child1Start;
            return mDataInfo;
        }
        if (dataPosition == child1End) {
            mDataInfo.dataType = DATA_TYPE_GROUP_BOTTOM1;
            mDataInfo.groupPosition = groupPosition;
            mDataInfo.childPosition = -1;
            return mDataInfo;
        }

        int child2Start = child1End + 1;
        int child2End = child2Start + child2Size;
        if (dataPosition >= child2Start && dataPosition < child2End) {
            mDataInfo.dataType = DATA_TYPE_CHILD2;
            mDataInfo.groupPosition = groupPosition;
            mDataInfo.childPosition = dataPosition - child2Start;
            return mDataInfo;
        }
        if (dataPosition == child2End) {
            mDataInfo.dataType = DATA_TYPE_GROUP_BOTTOM2;
            mDataInfo.groupPosition = groupPosition;
            mDataInfo.childPosition = -1;
            return mDataInfo;
        }
        mDataInfo.dataType = DATA_TYPE_UNKNOWN;
        mDataInfo.groupPosition = groupPosition;
        mDataInfo.childPosition = -1;
        return mDataInfo;
    }


    private int getRealSize(G group) {
        int size = 1;
        size += group.getChild1Size();
        size += 1;
        size += group.getChild2Size();
        size += 1;
        return size;
    }

    private int convertLocation(int location) {
        int insertPosition = location - 1;
        if (insertPosition < 0) {
            return 0;
        }
        if (insertPosition >= groupList.size()) {
            return size();
        }
        int index = 0;
        G group;
        for (int i = 0; i < insertPosition; i++) {
            group = groupList.get(i);
            index += getRealSize(group);
        }
        return index;
    }


    @Override
    public void setList(List<? extends G> list) {
        groupList.clear();
        if (list == null || list.isEmpty()) {
            clear();
            return;
        }
        groupList.addAll(list);
        List<G> result = GroupDataHelper.convert2(list);
        int oldSize = getSize();
        if (!dataList.isEmpty()) {
            dataList.clear();
        }
        dataList.addAll(result);
        updateDate(oldSize, getSize());
    }

    @Override
    public void addAll(int location, List<? extends G> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        groupList.addAll(list);
        List<G> result = GroupDataHelper.convert2(list);
        location = convertLocation(location);
        if (location < 0) {
            location = 0;
        }
        if (location > dataList.size()) {
            location = dataList.size();
        }
        int oldSize = getSize();
        dataList.addAll(location, result);
        updateDate(oldSize, getSize());
    }


    @Override
    public void add(G data) {
        List<G> list = new ArrayList<>();
        list.add(data);
        addAll(groupList.size(), list);
    }

    @Override
    public void add(int location, G data) {
        List<G> list = new ArrayList<>();
        list.add(data);
        addAll(location, list);
    }

    @Override
    public void setData(G data) {
        List<G> list = new ArrayList<>();
        list.add(data);
        setList(list);
    }


    @Override
    public void set(int location, G data) {
        //可能涉及子项数量变化 难以实现局部刷新
        //throw new RuntimeException("暂未实现!不要调用此方法");
        if (location < 0 || location >= groupList.size()) {
            return;
        }
        groupList.set(location, data);
        super.setList(GroupDataHelper.convert2(groupList));
    }


    @Override
    public void removeAll(List<? extends G> list) {
        groupList.removeAll(list);
        super.setList(GroupDataHelper.convert2(groupList));
    }


    // TODO: 2020/3/2  关于remove  可以精准计算出位置信息，提供删除动画 局部更新
    @Override
    public void remove(G data) {
        groupList.remove(data);
        super.setList(GroupDataHelper.convert2(groupList));
    }

    @Override
    public void remove(int location) {
        if (location < 0 || location >= groupList.size()) {
            return;
        }
        groupList.remove(location);
        super.setList(GroupDataHelper.convert2(groupList));
    }




    @Override
    int _getSpanCount(int dataPosition) {
        DataInfo<G> dataInfo = getDataType(dataPosition);
        switch (dataInfo.getDataType()){
            case DATA_TYPE_CHILD1:
                return getChild1SpanCount(dataInfo.groupPosition,dataInfo.data);
            case DATA_TYPE_CHILD2:
                return getChild2SpanCount(dataInfo.groupPosition,dataInfo.data);
            default:
                return 1;
        }
    }


    @Override
    public final int getSpanCount() {
        return super.getSpanCount();
    }

    public int getChild1SpanCount(int groupPosition,G group){
        return 1;
    }

    public int getChild2SpanCount(int groupPosition,G group){
        return 1;
    }

}
