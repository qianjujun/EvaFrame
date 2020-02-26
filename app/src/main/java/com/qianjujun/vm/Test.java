package com.qianjujun.vm;

import com.qianjujun.frame.adapter.ChildData;
import com.qianjujun.frame.adapter.GroupData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/22 14:28
 * @describe
 */
public class Test {


    public static int index = 1;
    public static int GROUP_INDEX = 1;
    public static class Child implements ChildData{

        private boolean isGroup;

        public void setGroup(boolean group) {
            isGroup = group;
        }

        @Override
        public boolean isGroup() {
            return isGroup;
        }


        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Child(String text) {
            this.text = text;
        }



    }


    public static class Group implements GroupData<Child> {

        private List<Child> list = new ArrayList<>();

        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Group(String text) {
            this.text = text;
        }

        @Override
        public Child convertToChild() {
            Child child = new Child(text);
            child.setGroup(true);
            return child;
        }

        @Override
        public List<Child> getData() {
            if(list.isEmpty()){
                int max = new Random().nextInt(20)+3;
                for(int i = 0;i<max;i++){
                    list.add(new Child("组"+text+"  "+i));
                }
            }
            return list;
        }
    }

    public static List<Group> createGroup(){
        List<Group> list = new ArrayList<>();
        for(int i = 0;i<10;i++){
            list.add(new Group(String.valueOf(i)));
        }
        return list;
    }




}
