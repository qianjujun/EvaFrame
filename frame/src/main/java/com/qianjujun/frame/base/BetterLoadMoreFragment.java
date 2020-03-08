package com.qianjujun.frame.base;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hello7890.adapter.RecyclerViewAdapter;
import com.hello7890.adapter.vm.ViewModule;
import com.qianjujun.frame.R;
import com.qianjujun.frame.data.IData;
import com.qianjujun.frame.data.IPageData;
import com.qianjujun.frame.data.OnResponse;
import com.qianjujun.frame.exception.AppException;
import com.qianjujun.frame.exception.EmptyException;
import com.qianjujun.frame.loadmore.Footer;
import com.qianjujun.frame.loadmore.LoadMoreViewModule;
import com.qianjujun.frame.loadmore.OnLoadMoreListener;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BetterLoadMoreFragment<T, PD extends IPageData<T>, VM extends ViewModule<T>> extends BetterModuleFragment implements OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private Disposable disposable;
    protected VM viewModule;
    protected LoadMoreViewModule loadMoreViewModule;
    protected RecyclerViewAdapter adapter;
    protected SwipeRefreshLayout swipeRefreshLayout;

    private String nextPageParam;


    public static final String TAG = "BetterLoadMoreFragment";

    @Override
    protected void initModule(RecyclerView recyclerView, View contentView) {
        nextPageParam = getFirstPageParam();
        swipeRefreshLayout = contentView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setEnabled(isEnableRefresh());
        swipeRefreshLayout.setOnRefreshListener(this);
        loadMoreViewModule = createLoadMoreViewModule();
        viewModule = createViewModule();
        adapter = createAdapter(viewModule, loadMoreViewModule);
        recyclerView.setLayoutManager(createLayoutManager());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isHaveData()){
            request();
        }
    }

    protected boolean isEnableRefresh(){
        return true;
    }

    private void setSwipeRefreshLayoutEnable(boolean enable){
        swipeRefreshLayout.setEnabled(enable&&isEnableRefresh());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.better_loadmore_fragment;
    }

    protected LoadMoreViewModule createLoadMoreViewModule() {
        return new LoadMoreViewModule(this);
    }

    protected abstract VM createViewModule();

    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(mActivity);
    }

    protected RecyclerViewAdapter createAdapter(VM viewModule, LoadMoreViewModule loadMoreViewModule) {
        return new RecyclerViewAdapter(viewModule, loadMoreViewModule);
    }

    protected abstract Flowable<? extends IData<PD>> createRequest(String nextPageParam);


    private void request() {
        Log.d(TAG, "request() called");
        cancelRequest();
        setRefreshLoadMoreEnable();
        Flowable<? extends IData<PD>> flowable = createRequest(nextPageParam);
        if(flowable==null){
            flowable = Flowable.error(new RuntimeException("沒有實現該接口"));
        }
        disposable = flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new LoadOnResponse());
    }

    private void cancelRequest(){
        if(disposable!=null&&!disposable.isDisposed()){
            disposable.dispose();
        }
    }


    /**
     * 設置加載互斥
     */
    private void setRefreshLoadMoreEnable() {
        if (isRefreshRequest()) {//
            setSwipeRefreshLayoutEnable(true);
            loadMoreViewModule.setEnable(false);
        } else {
            loadMoreViewModule.setEnable(true);
            setSwipeRefreshLayoutEnable(false);
        }
    }


    class LoadOnResponse extends OnResponse<PD,IData<PD>>{
        @Override
        public void onBegin() {
            super.onBegin();
        }

        @Override
        public void onSuccess(PD pd) throws AppException {
            List<T> list = pd.getDataList();
            if(list==null||list.isEmpty()){
                throw new EmptyException();
            }
            if (isRefreshRequest()) {
                viewModule.setList(list);
            } else {
                viewModule.addAll(list);
            }
            nextPageParam = nextPageParam(pd);

            if(haveNextPage(pd,nextPageParam)){
                loadMoreViewModule.setState(Footer.STATE_SUCCESS);
            }else {
                loadMoreViewModule.setState(Footer.STATE_NO_MORE);
            }
        }

        @Override
        public void onFail(AppException ex) {
            super.onFail(ex);
            loadMoreViewModule.setState(Footer.STATE_FAIL);
        }

        @Override
        public void onEnd(boolean success,int state, String message) {
            super.onEnd(success,state, message);
            if(isHaveData()){
                showSuccessView();
            }else {
                showEmptyView();
            }

            swipeRefreshLayout.setRefreshing(false);
            loadMoreViewModule.setEnable(true);
            setSwipeRefreshLayoutEnable(true);


        }
    }


    protected String nextPageParam(@NonNull PD data) {
        return data.nextPageParam();
    }

    protected boolean haveNextPage(@NonNull PD data, String currentNt) {
        return data.haveNextPage();
    }



    private boolean isRefreshRequest() {
        return TextUtils.equals(nextPageParam,getFirstPageParam());
    }


    @Override
    public void onLoadMore() {
        request();
    }

    @Override
    public void onRefresh() {
        nextPageParam = getFirstPageParam();
        request();
        Log.d(TAG, "onRefresh: ");
    }


    protected String getFirstPageParam(){
        return "1";
    }

    protected boolean isHaveData() {
        return viewModule.size()>0;
    }
}
