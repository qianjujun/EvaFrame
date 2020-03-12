package com.hello7890.adapter.vm;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.R;
import com.hello7890.adapter.data.IState;
import com.hello7890.adapter.data.ModuleState;
import com.hello7890.adapter.data.ViewModuleState;
import com.hello7890.adapter.databinding.SimpleAdapterVhEmptyBinding;
import com.hello7890.adapter.databinding.SimpleAdapterVhLoadingBinding;
import com.hello7890.adapter.databinding.SimpleAdapterVhFailBinding;
import com.hello7890.adapter.listener.DataStateChangeListener;
import com.hello7890.adapter.vh.BaseDbViewHolder;
import com.hello7890.adapter.vh.SpaceTViewHolder;


/**
 * 方案不好实现  暂搁置
 */
public class StateWrapViewModule extends XWrapViewModule<ModuleState> implements DataStateChangeListener {
    public static final String TAG = "StateWrapViewModule";

    private Runnable reloadRunnable;
    private ModuleState moduleState = new ModuleState();
    public StateWrapViewModule() {
        this.dataList.add(moduleState);
        setDataState(ViewModuleState.EMPTY);
    }

    public StateWrapViewModule setReloadRunnable(Runnable reloadRunnable) {
        this.reloadRunnable = reloadRunnable;
        return this;
    }






    /**
     * 此类无需调用这个方法
     * @param data
     */
    @Override
    public void setData(ModuleState data) {
        throw new IllegalArgumentException("改变状态请调用wrap对象的notify  如：notifyLoading，notifyError ，成功和空数据状态会自动判断");
    }


    private void setDataState(ViewModuleState state){
        if(state==null){
            moduleState.setState(IState.STATE_EMPTY);
        }else {
            moduleState.setState(state.getValue());
        }
        notifyItemChanged(0);
    }







    @Override
    public BaseViewHolder<ModuleState> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ModuleState.STATE_EMPTY:
                return onCreateEmptyViewHolder1(parent);
            case ModuleState.STATE_FAIL:
                return onCreateFailHolder1(parent);
            case ModuleState.STATE_LOADING:
                return onCreateLoadingHolder1(parent);
            case ModuleState.STATE_SUCCESS:
            default:
                return new SpaceTViewHolder<ModuleState>(parent){
                    @Override
                    public void onBindData(ModuleState moduleState, int dataPosition, int adapterPosition) {
                        Log.d(TAG, "onBindData() called with: moduleState = [" + moduleState + "], dataPosition = [" + dataPosition + "], adapterPosition = [" + adapterPosition + "]");
                    }
                };
        }
    }


    @Override
    public int getItemViewType(int dataPosition) {
        return moduleState.getState();
    }

    @Override
    public final BaseViewModule wrapWm(ViewModule viewModule) {
        if(viewModule==null){
            return this;
        }

        viewModule.addDataStateChangeListener(this);
        return super.wrapWm(viewModule);
    }

    protected BaseViewHolder<ModuleState> onCreateEmptyViewHolder1(ViewGroup parent) {
        return new BaseDbViewHolder<ModuleState,SimpleAdapterVhEmptyBinding>(R.layout.simple_adapter_vh_empty,parent) {
            @Override
            public void onBindData(ModuleState moduleState, int dataPosition, int adapterPosition) {
                //do nothing
                Log.d(TAG, "onBindData() called with: moduleState = [" + moduleState + "], dataPosition = [" + dataPosition + "], adapterPosition = [" + adapterPosition + "]");
            }
        };
    }
    protected BaseViewHolder<ModuleState> onCreateLoadingHolder1(ViewGroup parent) {
        return new BaseDbViewHolder<ModuleState, SimpleAdapterVhLoadingBinding>(R.layout.simple_adapter_vh_loading,parent) {
            @Override
            public void onBindData(ModuleState moduleState, int dataPosition, int adapterPosition) {
                //do nothing
                Log.d(TAG, "onBindData() called with: moduleState = [" + moduleState + "], dataPosition = [" + dataPosition + "], adapterPosition = [" + adapterPosition + "]");
            }
        };
    }

    protected BaseViewHolder<ModuleState> onCreateFailHolder1(ViewGroup parent) {
        return new BaseDbViewHolder<ModuleState, SimpleAdapterVhFailBinding>(R.layout.simple_adapter_vh_fail,parent) {
            @Override
            public void onBindData(ModuleState moduleState, int dataPosition, int adapterPosition) {
                Log.d(TAG, "onBindData() called with: moduleState = [" + moduleState + "], dataPosition = [" + dataPosition + "], adapterPosition = [" + adapterPosition + "]");
                mDataBinding.btnReload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(reloadRunnable!=null){
                            reloadRunnable.run();
                        }
                    }
                });
            }
        };
    }





    @Override
    public void onSizeChange(int size) {
        setDataState(size>0?ViewModuleState.SUCCESS:ViewModuleState.EMPTY);
    }

    @Override
    public void onLoading() {
        setDataState(ViewModuleState.LOADING);
    }

    @Override
    public void onFail(int errorCode, String message) {
        moduleState.setMessage(message);
        moduleState.setErrorCode(errorCode);
        setDataState(ViewModuleState.FAIL);
    }








}
