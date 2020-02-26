package com.qianjujun.frame.adapter.group;

import java.util.ArrayList;
import java.util.List;

public class Test {
    Gc<IGroup> gc = new Gc<>();
    void test(){
        Group group = new Group();
        gc.setData(group);


        List<Group> list = new ArrayList<>();
        gc.setList(list);
    }

    static class Group implements IGroup{

        @Override
        public boolean isGroup() {
            return false;
        }

        @Override
        public List<? extends IGroup> getChildList2() {
            return null;
        }
    }
}
