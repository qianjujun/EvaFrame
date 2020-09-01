package com.qianjujun.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.decoration.StickyItemDecoration;
import com.hello7890.adapter.listener.OnModuleItemClickListener;
import com.hello7890.adapter.listener.OnModuleItemLongClickListener;
import com.hello7890.adapter.vm.StateWrapViewModule;
import com.qianjujun.R;
import com.qianjujun.TestData;
import com.qianjujun.databinding.FragmentTestWrapBinding;
import com.qianjujun.frame.base.BetterCustomModuleFragment;
import com.qianjujun.frame.exception.AppException;
import com.qianjujun.frame.utils.ToastUtils;
import com.qianjujun.rx.OnStateVmCallBack;
import com.qianjujun.vm.HeadVm;
import com.qianjujun.vm2.TestStateVm;
import com.qianjujun.vm2.TitleWrapVm;

import java.util.List;

import static com.qianjujun.router.RouterPath.PATH_TEST_WRAP_VM;

@Route(path = PATH_TEST_WRAP_VM)
public class TestWrapVmFragment extends BetterCustomModuleFragment<FragmentTestWrapBinding> {
    private static int insertIndex = 0;
    private static int index = 0;
    private TestStateVm stateVm1 = new TestStateVm("组1");
    private TestStateVm stateVm2 = new TestStateVm("组2");
    private TestStateVm stateVm3 = new TestStateVm("组3");


    @Override
    protected int getIncludeRecyclerViewLayoutId() {
        return R.layout.fragment_test_wrap;
    }

    @Override
    protected void initModule(RecyclerView recyclerView, FragmentTestWrapBinding dataBinding) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(
                new HeadVm("我是头"),
                TitleWrapVm.wrap(new StateWrapViewModule().setReloadRunnable(new Runnable() {
                    @Override
                    public void run() {
                        request1();
                    }
                }).wrapWm(stateVm1), "测试组头1"),
                TitleWrapVm.wrap(new StateWrapViewModule().setReloadRunnable(new Runnable() {
                    @Override
                    public void run() {
                        request2();
                    }
                }).wrapWm(stateVm2), "测试组头2"),
                TitleWrapVm.wrap(new StateWrapViewModule().setReloadRunnable(new Runnable() {
                    @Override
                    public void run() {
                        request3();
                    }
                }).wrapWm(stateVm3), "测试组头3")
                );

        recyclerView.setAdapter(adapter);




        recyclerView.addItemDecoration(new StickyItemDecoration(adapter.getAdapterHelper()));

        request1();
        request2();
        request3();
        dataBinding.setClick(this);


        stateVm2.setOnModuleItemLongClickListener(new OnModuleItemLongClickListener<String>() {
            @Override
            public boolean onModuleItemLongClick(String s, int dataPosition, int adapterPosition) {
                ToastUtils.show("删除一条数据");
                stateVm2.remove(dataPosition);
                return true;
            }
        });


        stateVm2.setOnModuleItemClickListener(new OnModuleItemClickListener<String>() {
            @Override
            public void onModuleItemClick(String s, int dataPosition, int adapterPosition) {
                ToastUtils.show("插入一条");
                stateVm2.add(dataPosition+1,"插入一条数据"+(insertIndex++));
            }
        });
    }



    @SuppressLint("CheckResult")
    private void request1() {
        TestData.createTestStringListRequest(index++,10)
                .subscribeWith(new OnStateVmCallBack<List<String>>(stateVm1) {
                    @Override
                    public void onBegin() {
                        stateVm1.notifyForceLoading();
                    }
                    @Override
                    public void onSuccess(List<String> strings) throws AppException {
                        super.onSuccess(strings);
                        stateVm1.setList(strings);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void request2() {
        TestData.createTestStringListRequest(index++)
                .subscribeWith(new OnStateVmCallBack<List<String>>(stateVm2) {

                    @Override
                    public void onBegin() {
                        stateVm2.notifyForceLoading();
                    }

                    @Override
                    public void onSuccess(List<String> strings) throws AppException {
                        super.onSuccess(strings);
                        stateVm2.setList(strings);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void request3() {
        TestData.createTestStringListRequest(index++)
                .subscribeWith(new OnStateVmCallBack<List<String>>(stateVm3) {
                    @Override
                    public void onBegin() {
                        stateVm3.notifyForceLoading();
                    }
                    @Override
                    public void onSuccess(List<String> groupList) throws AppException {
                        super.onSuccess(groupList);
                        stateVm3.setList(groupList);
                    }
                });
    }

    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()) {
            case R.id.btn_loading1:
                request1();
                break;
            case R.id.btn_loading2:
                request2();
                break;
            case R.id.btn_loading3:
                request3();
                break;
        }
    }
}
