package com.qianjujun.fragment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.qianjujun.TestData;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.vm2.TestTwoGroupVm;

import static com.qianjujun.router.RouterPath.PATH_TEST_FRIEND;

@Route(path = PATH_TEST_FRIEND)
public class TestFriendFragment extends BetterModuleFragment {
    @Override
    protected void initModule(RecyclerView recyclerView, View contentView) {
        TestTwoGroupVm groupVm = new TestTwoGroupVm();
        recyclerView.setAdapter(new RecyclerViewAdapter(groupVm));
        groupVm.setList(TestData.createFriendData());
    }


    @Override
    protected RecyclerView.LayoutManager createLayoutManger() {
        return new GridLayoutManager(mActivity,3);
    }
}
