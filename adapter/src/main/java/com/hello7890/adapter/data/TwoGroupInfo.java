package com.hello7890.adapter.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/28 17:19
 * @describe
 */
public class TwoGroupInfo <C1,C2,G extends TwoGroupData<C1,C2>>{
    private static final String TAG = "TwoGroupInfo";
    public static final int DATA_TYPE_GROUP_TOP = 1;
    public static final int DATA_TYPE_CHILD1 = 2;
    public static final int DATA_TYPE_GROUP_BOTTOM1 = 3;
    public static final int DATA_TYPE_CHILD2 = 4;
    public static final int DATA_TYPE_GROUP_BOTTOM2 = 5;
    public static final int DATA_TYPE_UNKNOWN = 6;
    private G group;
    int dataType = -1;
    int groupPosition = -1;
    int childPosition = -1;


    public G getGroup() {
        return group;
    }

    public void setGroup(G group) {
        this.group = group;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getGroupPosition() {
        return groupPosition;
    }

    public void setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
    }

    public int getChildPosition() {
        return childPosition;
    }

    public void setChildPosition(int childPosition) {
        this.childPosition = childPosition;
    }

    public TwoGroupInfo(G group) {
        this.group = group;
    }


    public static <C1,C2,G extends TwoGroupData<C1,C2>> List<TwoGroupInfo<C1,C2,G>> convert2(List<? extends G> list){
        List<G> result = new ArrayList<>();
        List<C1> childList;
        List<C2> childList2;
        Log.d(TAG, "convert: start");
        for(G groupData:list){
            result.add(groupData);//头部数据
            childList = groupData.getChild1List();
            if(childList!=null){
                for(int i = 0;i<childList.size();i++){
                    result.add(groupData);
                }
            }
            result.add(groupData);//第一个Child尾部数据

            childList2 = groupData.getChild2List();
            if(childList2!=null){
                for(int i = 0;i<childList2.size();i++){
                    result.add(groupData);
                }
            }
            result.add(groupData);//第二个Child尾部数据
        }
        Log.d(TAG, "convert: end");
        return null;
    }
}
