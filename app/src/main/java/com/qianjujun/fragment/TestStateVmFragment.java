package com.qianjujun.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewStub;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.data.ViewModuleState;
import com.qianjujun.R;
import com.qianjujun.TestData;
import com.qianjujun.data.Data;
import com.qianjujun.databinding.FragmentTestStatVmBinding;
import com.qianjujun.frame.base.BetterCustomModuleFragment;
import com.qianjujun.frame.base.BetterModuleFragment;
import com.qianjujun.frame.exception.AppException;
import com.qianjujun.frame.exception.EmptyException;
import com.qianjujun.rx.OnDataCallBack;
import com.qianjujun.rx.OnStateVmCallBack;
import com.qianjujun.rx.RxUtil;
import com.qianjujun.vm2.TestStateVm;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

import static com.qianjujun.router.RouterPath.PATH_TEST_STATE_VM;


@Route(path = PATH_TEST_STATE_VM)
public class TestStateVmFragment extends BetterCustomModuleFragment<FragmentTestStatVmBinding> {

    private TestStateVm testStateVm = new TestStateVm();
    private Disposable disposable;

    @Override
    protected int getIncludeRecyclerViewLayoutId() {
        return R.layout.fragment_test_stat_vm;
    }

    @Override
    protected void initModule(RecyclerView recyclerView, FragmentTestStatVmBinding dataBinding) {
        dataBinding.setClick(this);
        recyclerView.setAdapter(new RecyclerViewAdapter(testStateVm));
        testLoadData();
    }

    private void testLoadData(){
        disposable = TestData.createTestStringListRequest()
                .subscribeWith(new OnStateVmCallBack<List<String>>(testStateVm){
                    @Override
                    public void onSuccess(List<String> strings) throws AppException {
                        super.onSuccess(strings);
                        testStateVm.setList(strings);
                    }
                });

    }

    private void cancelRequest(){
        if(disposable!=null&&!disposable.isDisposed()){
            disposable.dispose();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelRequest();
    }

    @Override
    public void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()){
            case R.id.btn_reload:
                testLoadData();
                break;
            case R.id.btn_loading:
                testStateVm.forceState(ViewModuleState.LOADING);
                break;
            case R.id.btn_fail:
                testStateVm.forceState(ViewModuleState.FAIL);
                break;
            case R.id.btn_empty:
                testStateVm.forceState(ViewModuleState.EMPTY);
                break;
            case R.id.btn_clear_reload:
                testStateVm.clear();
                testLoadData();
                break;
        }
    }
}
