package com.qianjujun;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qianjujun.frame.adapter.RecyclerViewAdapter;
import com.qianjujun.frame.adapter.StickyItemDecoration;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.frame.utils.ToastUtils;
import com.qianjujun.vm.HeadVm;
import com.qianjujun.vm.Test;
import com.qianjujun.vm.TestVm1;
import com.qianjujun.vm.TestVm2;
import com.qianjujun.vm.TestVm3;
import com.qianjujun.vm.TestVm4;
import com.qianjujun.vm.TestVm5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.qianjujun.RouterPath.PATH_TEST_MODULE;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 16:18
 * @describe
 */

@Route(path = PATH_TEST_MODULE)
public class TestRecFragment extends BetterModuleFragment {

    private static int index = 0;

    //多列  悬停item自动填充一列
    TestVm1 vm1 = new TestVm1();
    TestVm2 vm2 = new TestVm2();
    TestVm3 vm3 = new TestVm3();
    TestVm4 vm4 = new TestVm4();

    //分组
    TestVm5 vm5 = new TestVm5();
    @Override
    protected void initModule(RecyclerView recyclerView,View contentView) {
        index = 0;

        vm4.setData("我是头");
        vm1.setList(createTestData());
        vm2.setList(createStringTestData());

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(new HeadVm(),vm1,vm2,vm3,vm4,vm5);



        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,3));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new StickyItemDecoration(adapter.getAdapterHelper()));
        vm1.setOnModuleItemClickListener((integer, dataPosition, layoutPosition) -> ToastUtils.showWarning("AAAAA"+integer));



        vm5.setList(Test.createGroup());


        contentView.findViewById(R.id.btn_add).setOnClickListener(this);
        contentView.findViewById(R.id.btn_remove).setOnClickListener(this);
        contentView.findViewById(R.id.btn_setting).setOnClickListener(this);
        contentView.findViewById(R.id.btn_change).setOnClickListener(this);
        contentView.findViewById(R.id.btn_clear).setOnClickListener(this);


    }


    @Override
    protected int getLayoutId() {
        return R.layout.test_rec;
    }

    List<Integer> createTestData(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0;i<20;i++){
            index++;
            list.add(index);
        }
        return list;
    }

    List<String> createStringTestData(){
        int num = new Random().nextInt(15)+2;
        List<String> list = new ArrayList<>();
        for (int i = 0;i<num;i++){
            index++;
            list.add(index+"");
        }
        return list;
    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                vm2.addAll(createStringTestData());
                break;
            case R.id.btn_change:
                vm2.set(1,new Random().nextInt()+"");
                break;
            case R.id.btn_remove:
                vm2.remove(1);
                break;
            case R.id.btn_clear:
                vm2.clear();
                break;
            case R.id.btn_setting:
                vm2.setList(createStringTestData());
                break;
        }
    }
}
