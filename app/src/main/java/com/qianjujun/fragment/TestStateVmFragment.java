package com.qianjujun.fragment;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.vm.StateWrapViewModule;
import com.qianjujun.R;
import com.qianjujun.TestData;
import com.qianjujun.databinding.FragmentTestStatVmBinding;
import com.qianjujun.frame.base.BetterCustomModuleFragment;
import com.qianjujun.frame.exception.AppException;
import com.qianjujun.rx.OnStateVmCallBack;
import com.qianjujun.vm2.TestStateVm;

import java.util.List;

import io.reactivex.disposables.Disposable;

import static com.qianjujun.router.RouterPath.PATH_TEST_STATE_VM;


@Route(path = PATH_TEST_STATE_VM)
public class TestStateVmFragment extends BetterCustomModuleFragment<FragmentTestStatVmBinding> {

    private TestStateVm testStateVm = new TestStateVm("--");
    private Disposable disposable;

    @Override
    protected int getIncludeRecyclerViewLayoutId() {
        return R.layout.fragment_test_stat_vm;
    }

    @Override
    protected void initModule(RecyclerView recyclerView, FragmentTestStatVmBinding dataBinding) {
        dataBinding.setClick(this);

        BaseViewModule viewModule = new StateWrapViewModule().setReloadRunnable(new Runnable() {
            @Override
            public void run() {
                testLoadData();
            }
        }).wrapWm(testStateVm);

        recyclerView.setAdapter(new RecyclerViewAdapter(viewModule));
        testLoadData();
    }

    private int index = 0;

    private void testLoadData(){
        disposable = TestData.createTestStringListRequest(index++)
                .subscribeWith(new OnStateVmCallBack<List<String>>(testStateVm) {

                    @Override
                    public void onBegin() {
                        testStateVm.notifyForceLoading();
                    }

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
                testStateVm.notifyForceLoading();
                break;
            case R.id.btn_fail:
                testStateVm.notifyError(1,"测试错误");
                break;
            case R.id.btn_empty:
                testStateVm.clear();
                break;
            case R.id.btn_clear_reload:
                testStateVm.clear();
                testLoadData();
                break;
        }
    }
}
