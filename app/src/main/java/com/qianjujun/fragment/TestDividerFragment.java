package com.qianjujun.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.qianjujun.TestData;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.vm.Group;
import com.qianjujun.vm.GroupVm;
import com.qianjujun.vm.SimpleTextVm;

import static com.qianjujun.router.RouterPath.PATH_TEST_DIVIDER;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/3 13:54
 * @describe
 */
@Route(path = PATH_TEST_DIVIDER)
public class TestDividerFragment extends BetterModuleFragment {

    private SimpleTextVm textVm1 = new SimpleTextVm().setColumnNum(1);
    private SimpleTextVm textVm2 = new SimpleTextVm().setColumnNum(2);
    private SimpleTextVm textVm4 = new SimpleTextVm().setColumnNum(4);

    private GroupVm groupVm = new GroupVm();

    private Paint paint,paintGray;

    @Override
    protected void initModule(RecyclerView recyclerView, View contentView) {
        paint = new Paint();
        paint.setColor(Color.WHITE);

        paintGray = new Paint();
        paintGray.setColor(Color.parseColor("#f2f2f2"));

        recyclerView.setBackgroundColor(Color.parseColor("#336699"));



        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,4));

        //设置adapter 动画
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(textVm2, textVm4,textVm1,groupVm);
//        ScaleInAnimationAdapter adapter1 = new ScaleInAnimationAdapter(adapter);
//        adapter1.setFirstOnly(false);
        recyclerView.setAdapter(adapter);

        //设置缓存池
        RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        pool.setMaxRecycledViews(1000,21);
        recyclerView.setRecycledViewPool(pool);



        recyclerView.addItemDecoration(new RoundBackgroundDecoration(textVm2,30,50,2));


        recyclerView.addItemDecoration(new RoundBackgroundDecoration(textVm4,30,50));


        recyclerView.addItemDecoration(new RoundBackgroundDecoration(textVm1,30,50,2));




        textVm2.setList(TestData.createTestStringList(7));
        textVm4.setList(TestData.createTestStringList(15));
        textVm1.setList(TestData.createTestStringList(25));
        groupVm.setExpendable(true);
        groupVm.setList(Group.createTestData());





    }





}
