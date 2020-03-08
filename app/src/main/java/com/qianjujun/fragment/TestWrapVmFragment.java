package com.qianjujun.fragment;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.data.ViewModuleState;
import com.hello7890.adapter.decoration.StickyItemDecoration;
import com.hello7890.adapter.listener.OnChildItemClickListener;
import com.hello7890.adapter.listener.OnGroupItemClickListener;
import com.hello7890.adapter.listener.OnModuleItemClickListener;
import com.hello7890.adapter.listener.OnModuleItemLongClickListener;
import com.qianjujun.R;
import com.qianjujun.TestData;
import com.qianjujun.databinding.FragmentTestWrapBinding;
import com.qianjujun.frame.base.BetterCustomModuleFragment;
import com.qianjujun.frame.exception.AppException;
import com.qianjujun.frame.utils.ToastUtils;
import com.qianjujun.rx.OnStateVmCallBack;
import com.qianjujun.vm.Child;
import com.qianjujun.vm.Group;
import com.qianjujun.vm.GroupVm;
import com.qianjujun.vm.HeadVm;
import com.qianjujun.vm2.TestStateVm;
import com.qianjujun.vm2.TitleWrapVm;

import java.util.List;

import static com.qianjujun.router.RouterPath.PATH_TEST_WRAP_VM;

@Route(path = PATH_TEST_WRAP_VM)
public class TestWrapVmFragment extends BetterCustomModuleFragment<FragmentTestWrapBinding> {

    private TestStateVm stateVm1 = new TestStateVm();
    private TestStateVm stateVm2 = new TestStateVm();
    private TestStateVm stateVm3 = new TestStateVm();
    private GroupVm groupVm = new GroupVm();



    @Override
    protected int getIncludeRecyclerViewLayoutId() {
        return R.layout.fragment_test_wrap;
    }

    @Override
    protected void initModule(RecyclerView recyclerView, FragmentTestWrapBinding dataBinding) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(
                new HeadVm("我是分组VM"),

                TitleWrapVm.wrap(stateVm2, "测试组头2"),
                TitleWrapVm.wrap(stateVm3, "测试组头3"),
                TitleWrapVm.wrap(stateVm1, "测试组头1"));
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new StickyItemDecoration(adapter.getAdapterHelper()));
        groupVm.setExpendable(true);

        request1();
        request2();
        request3();
        dataBinding.setClick(this);



        groupVm.setOnGroupItemClickListener(new OnGroupItemClickListener<Child, Group>() {
            @Override
            public void onGroupItemClick(Group group, int groupIndex, int dataPosition, boolean top) {
                if(!top){
                    groupVm.remove(groupIndex);
                }
            }
        });


        groupVm.setOnChildItemClickListener(new OnChildItemClickListener<Child, Group>() {
            @Override
            public void onChildItemClick(Child child, Group group, int groupIndex, int childIndex, int dataPosition) {
                if(groupIndex%2==0){
                    ToastUtils.show("删除操作");
                    //groupVm.removeChild(groupIndex,childIndex);
                }else {
                    ToastUtils.show("新增操作");
                    groupVm.addChild(groupIndex,childIndex,new Child("新怎child"+insertIndex++));
                }
            }
        });




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
//,
//        new HeadVm("测试头2"),
//    stateVm2,
//            new HeadVm("测试头3"),
//    stateVm3

    private static int insertIndex = 0;
    private static int index = 0;
    @SuppressLint("CheckResult")
    private void request1() {
        TestData.createTestStringListRequest(index++,10)
                .subscribeWith(new OnStateVmCallBack<List<String>>(stateVm1) {
                    @Override
                    public void onBegin() {
                        stateVm1.forceState(ViewModuleState.LOADING);
                    }
                    @Override
                    public void onSuccess(List<String> strings) throws AppException {
                        super.onSuccess(strings);
                        stateVm1.setList(strings);

                        Log.d(TAG, "onSuccess() called with: strings = [" + strings + "]");
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void request2() {
        TestData.createTestStringListRequest(index++)
                .subscribeWith(new OnStateVmCallBack<List<String>>(stateVm2) {

                    @Override
                    public void onBegin() {
                        stateVm2.forceState(ViewModuleState.LOADING);
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
        TestData.createGroupRequest()
                .subscribeWith(new OnStateVmCallBack<List<Group>>(groupVm) {
                    @Override
                    public void onBegin() {
                        groupVm.forceState(ViewModuleState.LOADING);
                    }

                    @Override
                    public void onSuccess(List<Group> groupList) throws AppException {
                        super.onSuccess(groupList);
                        groupVm.setList(groupList);
                    }
                });

        TestData.createTestStringListRequest(index++)
                .subscribeWith(new OnStateVmCallBack<List<String>>(stateVm3) {

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
