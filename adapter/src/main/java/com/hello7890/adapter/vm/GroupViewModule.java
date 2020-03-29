package com.hello7890.adapter.vm;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.RecyclerViewHelper;
import com.hello7890.adapter.data.GroupData;
import com.hello7890.adapter.data.GroupDataHelper;
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
    private RecyclerView mRecyclerView;
    private RecyclerViewHelper recyclerViewHelper;


    private static final String TAG = "GroupViewModule3";
    public static final String CHANGE_EXPEND = "GroupViewModule3_change_expend";

    private List<G> groupList = new ArrayList<>();
    private SparseArray<Boolean> expendGroup = new SparseArray<>();

    private OnGroupItemClickListener<C, G> onGroupItemClickListener;
    private OnChildItemClickListener<C, G> onChildItemClickListener;
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


    public GroupViewModule() {
        recyclerViewHelper = new RecyclerViewHelper();
        setOnModuleItemClickListener((group, dataPosition, layoutPosition) -> {
            int firstIndex = dataList.indexOf(group);
            int lastIndex = dataList.lastIndexOf(group);
            int childIndex = dataPosition - firstIndex - 1;
            int groupIndex = groupList.indexOf(group);
            int childSize = group.getChildSize();

            if (dataPosition == firstIndex) {
                if (expendable && childSize > 0) {
                    if (expendGroup.get(groupIndex, true)) {
                        opGroup2(groupIndex, dataPosition, childSize, false);
                    } else {
                        opGroup2(groupIndex, dataPosition, childSize, true);
                    }

                }
                if (onGroupItemClickListener != null) {
                    onGroupItemClickListener.onGroupItemClick(group, groupIndex, dataPosition, true);
                }

            } else if (dataPosition == lastIndex) {
                if (onGroupItemClickListener != null) {
                    onGroupItemClickListener.onGroupItemClick(group, groupIndex, dataPosition, false);
                }
            } else if (childIndex < group.getChildSize()) {
                if (onChildItemClickListener != null) {
                    onChildItemClickListener.onChildItemClick(group.getChild(childIndex), group, groupIndex, childIndex, dataPosition);
                }
            }
        });
    }

    public void expandGroup(int groupPosition) {
        opGroup(groupPosition, true);
    }

    public void collapseGroup(int groupPosition) {
        opGroup(groupPosition, false);
    }

    private void opGroup(int groupPosition, boolean expend) {
        if (groupPosition < 0 || groupPosition >= groupList.size()) {
            return;
        }
        G group = groupList.get(groupPosition);
        if (group == null) {
            return;
        }
        int dataPosition = dataList.indexOf(group);
        int childSize = group.getChildSize();
        opGroup2(groupPosition, dataPosition, childSize, expend);
    }


    /**
     * 对收缩或扩展的数据进行空占位处理
     * 对datasize不产生影响
     * @param groupPosition
     * @param dataPosition
     * @param childSize
     * @param expend
     */
    private void opGroup2(int groupPosition, int dataPosition, int childSize, boolean expend) {
        if(mRecyclerView!=null){
            int adapterPosition = dataPosition+getStartPosition();
            int firstCompletePosition = recyclerViewHelper.findFirstCompletePosition(mRecyclerView);
            if(firstCompletePosition>adapterPosition){//处理因吸顶导致实际position已经划出界面外的情况
                mRecyclerView.scrollToPosition(adapterPosition);
            }
        }

        expendGroup.put(groupPosition, expend);
        notifyItemChanged(dataPosition + 1, childSize);
        notifyItemChanged(dataPosition, CHANGE_EXPEND);
    }


    /**
     * @deprecated
     * 真实操作了数据 ，可能会重复操作
     * @param groupPosition
     * @param dataPosition
     * @param childSize
     * @param expend
     */
    private void opGroup(int groupPosition, int dataPosition, int childSize, boolean expend) {
        G group = groupList.get(groupPosition);
        if (expend) {
            List<G> childList = new ArrayList<>();
            for (int i = 0; i < childSize; i++) {
                childList.add(group);
            }
            dataList.addAll(dataPosition + 1, childList);
            notifyItemInserted(dataPosition + 1, group.getChildList().size());
        } else {
            Iterator<G> iterator = dataList.iterator();
            G data;
            int deleteNum = -1;
            while (iterator.hasNext()) {
                data = iterator.next();
                if (Objects.equals(data, group)) {//第一次过掉
                    if (deleteNum == -1) {
                        deleteNum = 0;
                    }
                    if (deleteNum >= childSize) {
                        break;
                    }
                    iterator.remove();
                    deleteNum++;
                }
            }
            notifyItemRemove(dataPosition + 1, childSize);
        }
    }


    public void removeGroup(int groupPosition){
        remove(groupPosition);
    }

    public void removeGroup(G group){
        remove(group);
    }

    public G getGroup(int groupPosition){
        if(groupPosition<0||groupPosition>=groupList.size()){
            return null;
        }
        return groupList.get(groupPosition);
    }

    public void removeChild(int groupPosition,int childPosition){
        G group = getGroup(groupPosition);
        if(group==null){
            Log.e(TAG, "removeChild: ",new RuntimeException("the group is null"));
            return;
        }
        List<C> childList = group.getChildList();
        if(childList!=null&&childPosition>=0&&childPosition<childList.size()){
            childList.remove(childPosition);
        }
        set(groupPosition,group);
    }

    public void addChild(int groupPosition,int childPosition,C child){
        G group = getGroup(groupPosition);
        if(group==null){
            Log.e(TAG, "removeChild: ",new RuntimeException("the group is null"));
            return;
        }
        List<C> childList = group.getChildList();
        if (childPosition < 0) {
            childPosition = 0;
        }
        if (childPosition > childList.size()) {
            childPosition = childList.size();
        }
        childList.add(childPosition,child);
        set(groupPosition,group);
    }

    public void addAllChild(int groupPosition,List<C> childs){
        if(childs==null||childs.isEmpty()){
            return;
        }
        G group = getGroup(groupPosition);
        if(group==null){
            Log.e(TAG, "removeChild: ",new RuntimeException("the group is null"));
            return;
        }
        List<C> childList = group.getChildList();

        childList.addAll(childs);
        set(groupPosition,group);
    }






    @Override
    public BaseViewHolder<G> onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mRecyclerView==null&&parent instanceof RecyclerView){
            mRecyclerView = (RecyclerView) parent;
        }
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
            protected void onBindData(SpaceVmBinding dataBing, G group, int groupIndex, int dataPosition,int adapterPosition,boolean expend) {
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
        public final void onBindData(G g, int dataPosition, int adapterPosition) {
            int firstIndex = dataList.indexOf(g);
            int childIndex = dataPosition - firstIndex - 1;
            int groupIndex = groupList.indexOf(g);
            onBindData(mDataBinding, g, g.getChild(childIndex), groupIndex, childIndex, dataPosition,adapterPosition);
        }

        @Override
        public final void onBindData(G g, int dataPosition, int adapterPosition, List<Object> payloads) {
            int firstIndex = dataList.indexOf(g);
            int childIndex = dataPosition - firstIndex - 1;
            int groupIndex = groupList.indexOf(g);
            onBindData(mDataBinding, g, g.getChild(childIndex), groupIndex, childIndex, dataPosition,adapterPosition, payloads);
        }

        protected abstract void onBindData(DB dataBing, G group, C child, int groupIndex, int childIndex, int dataPosition, int adapterPosition);

        protected void onBindData(DB dataBing, G group, C child, int groupIndex, int childIndex, int dataPosition, int adapterPosition, List<Object> payloads) {

        }
    }

    public void getChildLocationInfo(int dataPosition, int[] location) {
        if (location == null || location.length < 2) {
            throw new RuntimeException("数组长度小于2");
        }
        G groupInfo = getItem(dataPosition);
        if (groupInfo == null) {
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
        public final void onBindData(G g, int dataPosition, int adapterPosition) {
            int groupIndex = groupList.indexOf(g);
            onBindData(mDataBinding, g, groupIndex, dataPosition, adapterPosition,expendGroup.get(groupIndex, true));
        }

        @Override
        public final void onBindData(G g, int dataPosition, int adapterPosition, @NonNull List<Object> payloads) {
            int groupIndex = groupList.indexOf(g);
            boolean expend = expendGroup.get(groupIndex, true);
            if (payloads.contains(CHANGE_EXPEND)) {
                onGroupExpendChange(mDataBinding, g, groupIndex,adapterPosition, expend);
            } else {
                onBindData(mDataBinding, g, groupIndex, dataPosition,adapterPosition, expend, payloads);
            }
        }


        protected abstract void onBindData(DB dataBing, G group, int groupIndex, int dataPosition,int adapterPosition, boolean expend);

        protected void onBindData(DB dataBing, G group, int groupIndex, int dataPosition, int adapterPosition,boolean expend, @NonNull List<Object> payloads) {

        }

        protected void onGroupExpendChange(DB dataBing, G group, int groupIndex, int adapterPosition, boolean expend) {

        }

        public final void changeExpendImageAnim(ImageView imageView, boolean expend) {
            if (expend) {
                ObjectAnimator.ofFloat(imageView, "rotation", -180, 0)
                        .setDuration(200)
                        .start();
            } else {
                ObjectAnimator.ofFloat(imageView, "rotation", 0, -180)
                        .setDuration(200)
                        .start();
            }
        }

        public final void changeExpendImage(ImageView imageView, boolean expend) {
            if (expend) {
                imageView.setRotation(0);
            } else {
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
        List<G> result = GroupDataHelper.convert(list);
        int oldSize = getSize();
        if (!dataList.isEmpty()) {
            dataList.clear();
        }
        dataList.addAll(result);
        updateDate(oldSize,getSize());
    }

    @Override
    public void addAll(int location, List<? extends G> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        groupList.addAll(list);
        List<G> result = GroupDataHelper.convert(list);
        location = convertLocation(location);
        if (location < 0) {
            location = 0;
        }
        if (location > dataList.size()) {
            location = dataList.size();
        }
        int oldSize = getSize();
        dataList.addAll(location, result);
        updateDate(oldSize,getSize());
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
        super.setList(GroupDataHelper.convert(groupList));
    }


    @Override
    public void removeAll(List<? extends G> list) {
        groupList.removeAll(list);
        super.setList(GroupDataHelper.convert(groupList));
    }


    // TODO: 2020/3/2  关于remove  可以精准计算出位置信息，提供删除动画 局部更新
    @Override
    public void remove(G data) {
        groupList.remove(data);
        super.setList(GroupDataHelper.convert(groupList));
    }

    @Override
    public void remove(int location) {
        if (location < 0 || location >= groupList.size()) {
            return;
        }
        groupList.remove(location);
        super.setList(GroupDataHelper.convert(groupList));
    }


    @Override
    public int getItemViewType(int dataPosition) {
        G data = getItem(dataPosition);
        int firstDataIndex = dataList.indexOf(data);
        int groupIndex = groupList.indexOf(data);
        int dataType = getDataType(dataPosition);
        switch (dataType){
            case DATA_TYPE_GROUP_TOP:
                return getTopGroupViewType(groupIndex) * 3 + 2;
            case DATA_TYPE_GROUP_BOTTOM:
                return getBottomViewType(groupIndex) * 3 + 1;
            case DATA_TYPE_CHILD:
                int childIndex = dataPosition - firstDataIndex - 1;
                return getChildItemViewType(groupIndex, childIndex) * 3;
            case DATA_TYPE_CHILD_SPACE:
            default:
                return getSpaceViewType();
        }



    }

    private int getSpaceViewType() {
        return SPACE_VIEW_TYPE;
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
    public static final int DATA_TYPE_CHILD_SPACE = 4;
    public static final int DATA_TYPE_UNKNOWN = 5;

    private int getDataType(int dataPosition,int groupIndex){
        G data = getItem(dataPosition);
        G preData = getItem(dataPosition - 1);
        G nextData = getItem(dataPosition + 1);
        if (Objects.equals(data, preData) && Objects.equals(data, nextData)) {//子项
            if (expendable && !expendGroup.get(groupIndex, true)) {
                return DATA_TYPE_CHILD_SPACE;
            }
            return DATA_TYPE_CHILD;
        }
        if (!Objects.equals(data, preData)) {//头
            return DATA_TYPE_GROUP_TOP;
        }

        if (!Objects.equals(data, nextData)) {//尾
            return DATA_TYPE_GROUP_BOTTOM;
        }

        return DATA_TYPE_UNKNOWN;
    }



    public int getDataType(int dataPosition) {
        G data = getItem(dataPosition);
        int groupIndex = groupList.indexOf(data);
        return getDataType(dataPosition,groupIndex);
    }

    public boolean isChildItem(int dataPosition){
        int dataType = getDataType(dataPosition);
        return dataType==DATA_TYPE_CHILD||dataType==DATA_TYPE_CHILD_SPACE;
    }


    @Override
    int _getSpanCount(int dataPosition) {
        if(isChildItem(dataPosition)){
            return getSpanCount();
        }
        return 1;
    }


    @Override
    public final int getSpanCount() {
        return getChildSpanCount();
    }

    public int getChildSpanCount(){
        return 1;
    }



}
