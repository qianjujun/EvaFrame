package com.hello7890.adapter.vm;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.data.GroupData;
import com.hello7890.adapter.data.GroupInfoData;
import com.hello7890.adapter.listener.OnChildItemClickListener;
import com.hello7890.adapter.listener.OnGroupItemClickListener;
import com.hello7890.adapter.R;
import com.hello7890.adapter.vh.BaseDbViewHolder;
import com.hello7890.adapter.databinding.SpaceVmBinding;
import com.hello7890.adapter.vh.SpaceTViewHolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/2 14:40
 * @describe
 */
public abstract class GroupViewModule<C, G extends GroupData<C>> extends ViewModule<G> {
    private static final String TAG = "GroupViewModule3";
    public static final String CHANGE_EXPEND = "GroupViewModule3_change_expend";

    private List<G> groupList = new ArrayList<>();
    private SparseArray<Boolean> expendGroup = new SparseArray<>();

    private OnGroupItemClickListener<C,G> onGroupItemClickListener;
    private OnChildItemClickListener<C,G> onChildItemClickListener;
    private boolean expendable;

    public void setExpendable(boolean expendable) {
        this.expendable = expendable;
    }

    public void setOnGroupItemClickListener(OnGroupItemClickListener<C, G> onGroupItemClickListener) {
        this.onGroupItemClickListener = onGroupItemClickListener;
    }

    public void setOnChildItemClickListener(OnChildItemClickListener<C, G> onChildItemClickListener) {
        this.onChildItemClickListener = onChildItemClickListener;
    }


    public GroupViewModule(){
        setOnModuleItemClickListener((group, dataPosition, layoutPosition) -> {
            int firstIndex = dataList.indexOf(group);
            int lastIndex = dataList.lastIndexOf(group);
            int childIndex = dataPosition - firstIndex - 1;
            int groupIndex = groupList.indexOf(group);
            int childSize = group.getChildSize();

            if(dataPosition==firstIndex){
                if(expendable&&childSize>0){

                    if(expendGroup.get(groupIndex,true)){
                        expendGroup.put(groupIndex,false);
                        Iterator<G> iterator = dataList.iterator();
                        G data;
                        int deleteNum = -1;
                        while (iterator.hasNext()){
                            data = iterator.next();
                            if(Objects.equals(data,group)){//第一次过掉
                                if(deleteNum==-1){
                                    deleteNum = 0;
                                }
                                if(deleteNum>=childSize){
                                    break;
                                }
                                iterator.remove();
                                deleteNum++;
                            }
                        }
                        notifyItemRemove(dataPosition+1,childSize);

                    }else {
                        expendGroup.put(groupIndex,true);
                        List<G> childList = new ArrayList<>();
                        for(int i = 0;i<childSize;i++){
                            childList.add(group);
                        }
                        dataList.addAll(dataPosition+1,childList);
                        notifyItemInserted(dataPosition+1,group.getChildList().size());
                    }

                    notifyItemChanged(dataPosition,CHANGE_EXPEND);
                }
                if(onGroupItemClickListener!=null){
                    onGroupItemClickListener.onGroupItemClick(group,groupIndex,dataPosition,true);
                }

            }else if(dataPosition==lastIndex){
                if(onGroupItemClickListener!=null){
                    onGroupItemClickListener.onGroupItemClick(group,groupIndex,dataPosition,false);
                }
            }else if(childIndex<group.getChildSize()){
                if(onChildItemClickListener!=null){
                    onChildItemClickListener.onChildItemClick(group.getChild(childIndex),group,groupIndex,childIndex,dataPosition);
                }
            }
        });
    }








    @Override
    public BaseViewHolder<G> onCreateViewHolder(ViewGroup parent, int viewType) {
        int type = viewType % 3;
        switch (type) {
            case 0:
                return onCreateChildViewHolder(parent, viewType / 3);
            case 1:
                return onCreateGroupBottomViewHolder(parent, (viewType - 1) / 3);
            case 2:
                return onCreateGroupTopViewHolder(parent, (viewType - 2) / 3);
            default:
                return new SpaceTViewHolder<>(parent);
        }
    }


    protected abstract ChildHolder<? extends ViewDataBinding> onCreateChildViewHolder(ViewGroup parent, int viewType);

    protected abstract GroupHolder<? extends ViewDataBinding> onCreateGroupTopViewHolder(ViewGroup parent, int viewType);

    protected GroupHolder<? extends ViewDataBinding> onCreateGroupBottomViewHolder(ViewGroup parent, int viewType) {
        return new GroupHolder<SpaceVmBinding>(R.layout.space_vm, parent) {
            @Override
            protected void onBindData(SpaceVmBinding dataBing, G group, int groupIndex, int dataPosition,boolean expend) {
                // do nothing
                Log.d(TAG, "onBindGroupData() called with: group = [" + group + "], groupIndex = [" + groupIndex + "], dataPosition = [" + dataPosition + "]");
            }
        };
    }


    public abstract class ChildHolder<DB extends ViewDataBinding> extends BaseDbViewHolder<G, DB> {
        public ChildHolder(int layoutId, ViewGroup container) {
            super(layoutId, container);
        }

        @Override
        public final void onBindData(G g, int dataPosition, int layoutPosition) {
            int firstIndex = dataList.indexOf(g);
            int childIndex = dataPosition - firstIndex - 1;
            int groupIndex = groupList.indexOf(g);
            onBindData(mDataBinding, g, g.getChild(childIndex), groupIndex, childIndex, dataPosition);
        }

        @Override
        public final void onBindData(G g, int dataPosition, int layoutPosition, List<Object> payloads) {
            int firstIndex = dataList.indexOf(g);
            int childIndex = dataPosition - firstIndex - 1;
            int groupIndex = groupList.indexOf(g);
            onBindData(mDataBinding, g, g.getChild(childIndex), groupIndex, childIndex, dataPosition,payloads);
        }

        protected abstract void onBindData(DB dataBing, G group, C child, int groupIndex, int childIndex, int dataPosition);
        protected  void onBindData(DB dataBing, G group, C child, int groupIndex, int childIndex, int dataPosition,List<Object> payloads){

        }
    }

    public void getChildLocationInfo(int dataPosition,int[] location){
        if(location==null||location.length<2){
            throw new RuntimeException("数组长度为2");
        }
        G groupInfo = getItem(dataPosition);
        if(groupInfo==null){
            throw new RuntimeException("dataPosition错误");
        }
        int firstIndex = dataList.indexOf(groupInfo);
        int childIndex = dataPosition - firstIndex - 1;
        location[0] = childIndex;
        location[1] = groupInfo.getChildSize();
    }



    public abstract class GroupHolder<DB extends ViewDataBinding> extends BaseDbViewHolder<G, DB> {
        public GroupHolder(int layoutId, ViewGroup container) {
            super(layoutId, container);
        }

        @Override
        public final void onBindData(G g, int dataPosition, int layoutPosition) {
            int groupIndex = groupList.indexOf(g);
            onBindData(mDataBinding, g, groupIndex, dataPosition,expendGroup.get(groupIndex,true));
        }

        @Override
        public final void onBindData(G g, int dataPosition, int layoutPosition, @NonNull List<Object> payloads) {
            int groupIndex = groupList.indexOf(g);
            boolean expend = expendGroup.get(groupIndex,true);
            if(payloads.contains(CHANGE_EXPEND)){
                onGroupExpendChange(mDataBinding,g,groupIndex,expend);
                //payloads.remove(CHANGE_EXPEND);
            }else {
                onBindData(mDataBinding, g, groupIndex, dataPosition,expend,payloads);
            }
        }


        protected abstract void onBindData(DB dataBing, G group, int groupIndex, int dataPosition, boolean expend);
        protected void onBindData(DB dataBing, G group, int groupIndex, int dataPosition, boolean expend,@NonNull List<Object> payloads){

        }

        protected void onGroupExpendChange(DB dataBing, G group, int groupIndex,boolean expend){

        }

        public final void changeExpendImageAnim(ImageView imageView, boolean expend){
            if(expend){
                ObjectAnimator.ofFloat(imageView,"rotation",-180,0)
                        .setDuration(200)
                        .start();
            }else {
                ObjectAnimator.ofFloat(imageView,"rotation",0,-180)
                        .setDuration(200)
                        .start();
            }
        }

        public final void changeExpendImage(ImageView imageView, boolean expend){
            if(expend){
                imageView.setRotation(0);
            }else {
                imageView.setRotation(-180);
            }
        }

    }


    /**
     * 插入 修改等操作
     *
     * @param location group的location
     * @return 数据层的location
     */
    private int convertLocation(int location) {
        int insertPosition = location - 1;
        if (insertPosition < 0) {
            return 0;
        }
        int index = 0;
        G group;
        if (insertPosition >= groupList.size()) {
            return size();
        }
        for (int i = 0; i < insertPosition; i++) {
            group = groupList.get(i);
            index += 2;//头部+尾部
            index += group.getChildSize();
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
        List<G> result = GroupInfoData.convert(list);
        if (dataList.isEmpty()) {
            dataList.addAll(result);
            notifyItemInserted(0, result.size());
        } else {
            dataList.clear();
            dataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void addAll(int location, List<? extends G> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        groupList.addAll(list);
        List<G> result = GroupInfoData.convert(list);
        location = convertLocation(location);
        if (location < 0) {
            location = 0;
        }
        if (location > dataList.size()) {
            location = dataList.size();
        }
        dataList.addAll(location, result);
        notifyItemInserted(location, list.size());
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
        setList(GroupInfoData.convert(groupList));
    }


    @Override
    public void removeAll(List<? extends G> list) {
        //super.removeAll(list);
        groupList.removeAll(list);
        setList(GroupInfoData.convert(groupList));
    }


    // TODO: 2020/3/2  关于remove  可以精准计算出位置信息，提供删除动画 局部更新
    @Override
    public void remove(G data) {
        groupList.remove(data);
        setList(GroupInfoData.convert(groupList));
    }

    @Override
    public void remove(int location) {
        if (location < 0 || location >= groupList.size()) {
            return;
        }
        groupList.remove(location);
        setList(GroupInfoData.convert(groupList));
    }


    @Override
    public int getItemViewType(int dataPosition) {
        G data = getItem(dataPosition);
        G preData = getItem(dataPosition - 1);
        G nextData = getItem(dataPosition + 1);
        int firstDataIndex = dataList.indexOf(data);
        int groupIndex = groupList.indexOf(data);

        if (Objects.equals(data, preData) && Objects.equals(data, nextData)) {//子项
            int childIndex = dataPosition - firstDataIndex - 1;
            return getChildItemViewType(groupIndex, childIndex) * 3;
        }

        if (preData == null || !Objects.equals(data, preData)) {//头
            return getTopGroupViewType(groupIndex) * 3 + 2;
        }

        if (nextData == null || !Objects.equals(data, nextData)) {//尾
            return getBottomViewType(groupIndex) * 3 + 1;
        }
        return getUnKnownViewType();
    }

    private int getUnKnownViewType(){
        return EMPTY_VIEW_TYPE;
    }


    protected @IntRange(from = MIN_NORMAL_VIEW_TYPE, to = MAX_GROUP_VIEW_TYPE)
    int getChildItemViewType(int groupPosition, int childPosition) {
        return 0;
    }

    protected @IntRange(from = MIN_NORMAL_VIEW_TYPE, to = MAX_GROUP_VIEW_TYPE)
    int getTopGroupViewType(int groupPosition) {
        return 0;
    }

    protected @IntRange(from = MIN_NORMAL_VIEW_TYPE, to = MAX_GROUP_VIEW_TYPE)
    int getBottomViewType(int groupPosition) {
        return 0;
    }


    public static final int DATA_TYPE_GROUP_TOP = 1;
    public static final int DATA_TYPE_GROUP_BOTTOM = 2;
    public static final int DATA_TYPE_CHILD = 3;
    public static final int DATA_TYPE_UNKNOWN = 4;

    public int getDataType(int dataPosition){
        G data = getItem(dataPosition);
        G preData = getItem(dataPosition - 1);
        G nextData = getItem(dataPosition + 1);
        if (Objects.equals(data, preData) && Objects.equals(data, nextData)) {//子项
            return DATA_TYPE_CHILD;
        }
        if (preData == null || !Objects.equals(data, preData)) {//头
            return DATA_TYPE_GROUP_TOP;
        }

        if (nextData == null || !Objects.equals(data, nextData)) {//尾
            return DATA_TYPE_GROUP_BOTTOM;
        }

        return DATA_TYPE_UNKNOWN;
    }


}
