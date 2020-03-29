package com.qianjujun.fragment;

import android.graphics.Color;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.decoration.GroupViewModuleItemDecoration;
import com.hello7890.adapter.decoration.StickyItemDecoration;
import com.hello7890.adapter.decoration.ViewModuleItemDecoration;
import com.qianjujun.TestData;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.vm.Group;
import com.qianjujun.vm.GroupVm;
import com.qianjujun.vm2.TestStateVm;
import com.qianjujun.vm2.TitleWrapVm;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

import static com.qianjujun.router.RouterPath.PATH_TEST_MORE_VM;


@Route(path = PATH_TEST_MORE_VM)
public class TestMoreVmFragment extends BetterModuleFragment {
    private TestStateVm testStateVm = new TestStateVm("第一个");
    private TestStateVm testStateVm1 = new TestStateVm("第二个");
    private TestStateVm testStateVm2 = new TestStateVm("第三个");
    private GroupVm groupVm = new GroupVm();

    @Override
    protected void initModule(RecyclerView recyclerView, View contentView) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(testStateVm, TitleWrapVm.wrap(testStateVm1,"第二个"), groupVm, testStateVm2);
        ScaleInAnimationAdapter adapter1 = new ScaleInAnimationAdapter(adapter);
        adapter1.setFirstOnly(false);
        AlphaInAnimationAdapter aa = new AlphaInAnimationAdapter(adapter);
        aa.setFirstOnly(false);
        recyclerView.setAdapter(adapter1);
        //recyclerView.setItemAnimator(new ScaleInAnimator());



        testStateVm.setList(TestData.createTestStringList(5));
        testStateVm1.setList(TestData.createTestStringList(7));
        testStateVm2.setList(TestData.createTestStringList(9));
        List<Group> list = Group.createTestData();
        groupVm.setExpendable(true);
        groupVm.setList(list);

        recyclerView.addItemDecoration(new GroupViewModuleItemDecoration(groupVm, 3)
                .setDividerColor(Color.BLUE));

        recyclerView.addItemDecoration(new ViewModuleItemDecoration(testStateVm1,3)
                .setDividerColor(Color.BLUE)
        .setNoneBottomDivider(true));

        recyclerView.addItemDecoration(new StickyItemDecoration(adapter.getAdapterHelper()));

    }
}
