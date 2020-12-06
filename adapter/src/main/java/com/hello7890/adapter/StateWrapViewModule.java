package com.hello7890.adapter;

import android.util.Log;
import android.view.ViewGroup;

import com.hello7890.adapter.data.IState;
import com.hello7890.adapter.data.ModuleState;
import com.hello7890.adapter.data.ViewModuleState;
import com.hello7890.adapter.databinding.SimpleAdapterVhEmptyBinding;
import com.hello7890.adapter.databinding.SimpleAdapterVhFailBinding;
import com.hello7890.adapter.databinding.SimpleAdapterVhLoadingBinding;
import com.hello7890.adapter.listener.DataStateChangeListener;
import com.hello7890.adapter.vh.BaseDbViewHolder;
import com.hello7890.adapter.vh.SpaceTViewHolder;


/**
 * 方案不好实现  暂搁置
 */
public class StateWrapViewModule extends BaseViewModule<ModuleState> implements DataStateChangeListener {
    public static final String TAG = "StateWrapViewModule";

    private ModuleState moduleState = new ModuleState();
    public StateWrapViewModule() {
        this.dataList.add(moduleState);
        setDataState(ViewModuleState.SUCCESS);
    }











    private void setDataState(ViewModuleState state){
        if(state==null||wrapVm==null||wrapVm.size()>0){
            moduleState.setState(IState.STATE_SUCCESS);
        }else {
            moduleState.setState(state.getValue());
        }
        notifyItemChanged(0);
    }







    @Override
    public BaseViewHolder<ModuleState> onCreateViewHolder(ViewGroup parent, int viewType) {
        if(wrapVm==null){
            return new SpaceTViewHolder<>(parent);
        }

        BaseViewHolder<ModuleState> temp;

        switch (viewType) {
            case ModuleState.STATE_EMPTY:
                temp = wrapVm.onCreateEmptyViewHolder(parent);
                if(temp==null){
                    temp = onCreateEmptyViewHolder(parent);
                }
                break;
            case ModuleState.STATE_FAIL:
                temp = wrapVm.onCreateFailHolder(parent);
                if(temp==null){
                    temp = onCreateFailHolder(parent);
                }
                break;
            case ModuleState.STATE_LOADING:
                temp = wrapVm.onCreateLoadingHolder(parent);
                if(temp==null){
                    temp = onCreateLoadingHolder(parent);
                }
                break;
            case ModuleState.STATE_SUCCESS:
            default:
               temp = new SpaceTViewHolder<>(parent);
               break;
        }

        return temp;
    }


    @Override
    public int getItemViewType(int dataPosition) {
        return moduleState.getState();
    }

    public final StateWrapViewModule wrapWm(BaseViewModule viewModule) {
        if(viewModule==null){
            return this;
        }
        viewModule.addDataStateChangeListener(this);
        this.wrapVm = viewModule;
        return this;
    }


    private BaseViewModule wrapVm;

    @Override
    public BaseViewModule _getWrapViewModule() {
        return wrapVm;
    }


    @Override
    protected BaseViewHolder<ModuleState> onCreateEmptyViewHolder(ViewGroup parent) {
        return new BaseDbViewHolder<ModuleState,SimpleAdapterVhEmptyBinding>(R.layout.simple_adapter_vh_empty,parent) {
            @Override
            public void onBindData(ModuleState moduleState, int dataPosition, int adapterPosition) {
                //do nothing
                Log.d(TAG, "onBindData() called with: moduleState = [" + moduleState + "], dataPosition = [" + dataPosition + "], adapterPosition = [" + adapterPosition + "]");
            }
        };
    }


    @Override
    protected BaseViewHolder<ModuleState> onCreateLoadingHolder(ViewGroup parent) {
        return new BaseDbViewHolder<ModuleState, SimpleAdapterVhLoadingBinding>(R.layout.simple_adapter_vh_loading,parent) {
            @Override
            public void onBindData(ModuleState moduleState, int dataPosition, int adapterPosition) {
                //do nothing
                Log.d(TAG, "onBindData() called with: moduleState = [" + moduleState + "], dataPosition = [" + dataPosition + "], adapterPosition = [" + adapterPosition + "]");
            }
        };
    }

    @Override
    protected BaseViewHolder<ModuleState> onCreateFailHolder(ViewGroup parent) {
        return new BaseDbViewHolder<ModuleState, SimpleAdapterVhFailBinding>(R.layout.simple_adapter_vh_fail,parent) {
            @Override
            public void onBindData(ModuleState moduleState, int dataPosition, int adapterPosition) {
                Log.d(TAG, "onBindData() called with: moduleState = [" + moduleState + "], dataPosition = [" + dataPosition + "], adapterPosition = [" + adapterPosition + "]");
                mDataBinding.btnReload.setOnClickListener(v -> {
                    if(wrapVm!=null&&wrapVm.getReloadRunnable()!=null){
                        wrapVm.getReloadRunnable().run();
                    }
                });

                mDataBinding.tvMessage.setText(moduleState.getMessage());
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
