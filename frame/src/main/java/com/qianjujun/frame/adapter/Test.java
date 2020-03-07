package com.qianjujun.frame.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/22 14:28
 * @describe
 */
public class Test {


    public static <G extends GroupData<C>, C extends ChildData> List<C> convertGroupData(List<G> data, List<C> childGroupList) {

        List<C> list = new ArrayList<>();
//        if (data == null) {
//            return list;
//        }
//        if (childGroupList == null) {
//            childGroupList = new ArrayList<>();
//        }
//        childGroupList.clear();
//        for (G group : data) {
//            C child = group.convertToChild();
//            list.add(child);
//            childGroupList.add(child);
//            list.addAll(group.getChildList());
//        }
        return list;
    }

    public static <G extends GroupData<C>, C extends ChildData> List<C> convertGroupData(G data) {
        List<C> list = new ArrayList<>();
//        list.add(data.convertToChild());
//        list.addAll(data.getChildList());
        return list;
    }
}
