package com.qianjujun.vm2;

import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

import com.hello7890.adapter.vm.TwoGroupViewModule;
import com.qianjujun.data.FriendData;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/28 18:22
 * @describe
 */
public class TestTwoGroupVm extends TwoGroupViewModule<String, FriendData.Comment,FriendData> {
    @Override
    protected GroupViewHolder<? extends ViewDataBinding> onCreateGroupTopViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected Child1ViewHolder<? extends ViewDataBinding> onCreateChild1ViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected Child2ViewHolder<? extends ViewDataBinding> onCreateChild2ViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected int getChild1ViewType(int groupPosition, int child1Position) {

        return super.getChild1ViewType(groupPosition, child1Position);
    }
}
