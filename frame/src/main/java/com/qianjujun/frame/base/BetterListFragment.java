package com.qianjujun.frame.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.RecyclerViewAdapter2;
import com.hello7890.adapter.ViewModule;
import com.qianjujun.frame.data.IData;
import com.qianjujun.frame.data.OnResponse;
import com.qianjujun.frame.exception.AppException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BetterListFragment<T,VM extends ViewModule<T>> extends BetterModuleFragment {
    private Disposable disposable;
    protected RecyclerViewAdapter adapter;
    protected VM mViewModule;

    @Override
    protected void initModule(RecyclerView recyclerView, View contentView) {
        recyclerView.setLayoutManager(createLayoutManger());
        mViewModule = createVm();
        mViewModule.openEnableState(() -> loadData());
        RecyclerViewAdapter adapter = createAdapter(mViewModule);
        recyclerView.setAdapter(adapter);
    }


    protected RecyclerView.LayoutManager createLayoutManger(){
        return new LinearLayoutManager(mActivity);
    }

    protected abstract VM createVm();

    protected RecyclerViewAdapter createAdapter(VM vm){
        return new RecyclerViewAdapter2(vm);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }


    protected abstract Flowable<? extends IData<List<T>>> createRequest();



    private void loadData(){
        cancelRequest();
        Flowable<? extends IData<List<T>>> flowable = createRequest();
        if(flowable==null){
            flowable = Flowable.error(new RuntimeException("沒有實現該接口"));
        }
        disposable = flowable
                .delay(1,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new OnResponse<List<T>,IData<List<T>>>(){
                    @Override
                    public void onBegin() {
                        super.onBegin();
                        mViewModule.notifyLoading();
                    }

                    @Override
                    public void onFail(AppException ex) {
                        super.onFail(ex);
                        mViewModule.notifyError(ex.getErrorCode(),ex.getMessage());
                    }

                    @Override
                    public void onSuccess(List<T> ts) throws AppException {
                        mViewModule.setList(ts);
                    }
                });




    }

    private void cancelRequest(){
        if(disposable!=null&&!disposable.isDisposed()){
            disposable.dispose();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelRequest();
    }
}
