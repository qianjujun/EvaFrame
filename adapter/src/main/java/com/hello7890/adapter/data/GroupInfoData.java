package com.hello7890.adapter.data;

import android.util.Log;

import com.hello7890.adapter.data.GroupData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/2 14:26
 * @describe
 */
public class GroupInfoData {
    public static final String TAG = "GroupInfoData";
    public static <C,G extends GroupData<C>> List<G> convert(List<? extends G> list){
        List<G> result = new ArrayList<>();
        List<C> childList;
        Log.d(TAG, "convert: start");
        for(G groupData:list){
            result.add(groupData);//头部数据
            childList = groupData.getChildList();
            if(childList!=null){
                for(int i = 0;i<childList.size();i++){
                    result.add(groupData);
                }
            }
            result.add(groupData);//尾部数据
            Log.d(TAG, "convert: end1");
        }
        Log.d(TAG, "convert: end");
        return result;
    }





}
