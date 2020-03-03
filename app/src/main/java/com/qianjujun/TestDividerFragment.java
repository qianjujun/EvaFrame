package com.qianjujun;

import android.graphics.Color;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.qianjujun.frame.adapter.RecyclerViewAdapter;
import com.qianjujun.frame.adapter.ViewModuleItemDecoration;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.vm.SimpleTextVm;

import static com.qianjujun.RouterPath.PATH_TEST_DIVIDER;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/3 13:54
 * @describe
 */
@Route(path = PATH_TEST_DIVIDER)
public class TestDividerFragment extends BetterModuleFragment {
    private SimpleTextVm simpleTextVm = new SimpleTextVm(Color.parseColor("#336699"), Color.BLACK).setColumnNum(2);
    private SimpleTextVm grayTextVm = new SimpleTextVm(Color.parseColor("#f2f2f2"), Color.BLACK).setColumnNum(3);

    @Override
    protected void initModule(RecyclerView recyclerView, View contentView) {
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,6));
        recyclerView.setAdapter(new RecyclerViewAdapter(grayTextVm));

//        recyclerView.addItemDecoration(new ViewModuleItemDecoration(simpleTextVm, 20)
//                .setDividerColor(Color.YELLOW)
//                .setNoneBottomDivider(false));

        recyclerView.addItemDecoration(new ViewModuleItemDecoration(grayTextVm, 3)
                .setColumnNum(3)
                .setDividerColor(Color.parseColor("#220022")));

        grayTextVm.setList(TestData.createTestStringList(16));
        simpleTextVm.setList(TestData.createTestStringList(25));

    }
}
