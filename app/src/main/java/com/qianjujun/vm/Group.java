package com.qianjujun.vm;

import com.qianjujun.frame.adapter.GroupData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/2 16:23
 * @describe
 */
public class Group implements GroupData<Child> {
    private String text;
    private List<Child> childList;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }

    @Override
    public List<Child> getChildList() {
        return childList;
    }

    @Override
    public Child getChild(int childPosition) {
        if(childList==null||childList.isEmpty()){
            return null;
        }
        if(childPosition<0||childPosition>=childList.size()){
            return null;
        }
        return childList.get(childPosition);
    }

    @Override
    public int getChildSize() {
        return childList==null?0:childList.size();
    }


    private static final Random random = new Random();
    public static List<Group> createTestData(){
        List<Group> groupList = new ArrayList<>();
        List<Child> childList;
        Group group;
        int maxChildSize = 0;
        for(int i = 0;i<10;i++){
            group = new Group();
            group.setText("group"+i+"组");
            maxChildSize = random.nextInt(20);
            childList = new ArrayList<>();
            for(int j = 0;j< maxChildSize;j++){
                childList.add(new Child("group:"+i+"  child:"+j));
            }
            group.setChildList(childList);
            groupList.add(group);
        }
        return groupList;
    }

}
