package com.hello7890.adapter.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/2 14:26
 * @describe
 */
public class GroupDataHelper {
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
        }
        Log.d(TAG, "convert: end");
        return result;
    }




    public static <C1,C2,G extends Group2Data<C1,C2>> List<G> convert2(List<? extends G> list){
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
        return result;
    }







}
