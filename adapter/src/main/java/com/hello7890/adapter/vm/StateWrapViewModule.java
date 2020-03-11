package com.hello7890.adapter.vm;

import android.view.ViewGroup;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.DataChangeListener;
import com.hello7890.adapter.WrapViewModule;
import com.hello7890.adapter.data.IState;
import com.hello7890.adapter.data.ModuleState;
import com.hello7890.adapter.data.ViewModuleState;
import com.hello7890.adapter.vh.SpaceTViewHolder;


/**
 * 方案不好实现  暂搁置
 */
public class StateWrapViewModule extends WrapViewModule<ModuleState> implements DataChangeListener {

    private ModuleState moduleState = new ModuleState();
    public StateWrapViewModule() {
        setData(moduleState);
    }

    /**
     * 此类无需调用这个方法
     * @param data
     */
    @Override
    public void setData(ModuleState data) {
        throw new IllegalArgumentException("改变状态直接调用setState");
    }


    public void setState(ViewModuleState state){
        if(state==null){
            moduleState.setState(IState.STATE_EMPTY);
        }else {
            moduleState.setState(state.getValue());
        }
        notifyItemChanged(0);
    }

    public void loading(){
        setState(ViewModuleState.LOADING);
    }

    public void error(String message){
        moduleState.setMessage(message);
        setState(ViewModuleState.FAIL);
    }





    @Override
    public BaseViewHolder<ModuleState> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ModuleState.STATE_EMPTY:
                return onCreateEmptyViewHolder1(parent);
            case ModuleState.STATE_FAIL:
                return onCreateLoadingHolder1(parent);
            case ModuleState.STATE_LOADING:
                return onCreateFailHolder1(parent);
            case ModuleState.STATE_SUCCESS:
            default:
                return new SpaceTViewHolder<>(parent);
        }
    }


    @Override
    public int getItemViewType(int dataPosition) {
        return moduleState.getState();
    }

    @Override
    public final WrapViewModule wrap(ViewModule viewModule) {
        if(viewModule==null){
            return null;
        }
        viewModule.addDataChangeListener(this);
        return super.wrap(viewModule);
    }

    protected BaseViewHolder<ModuleState> onCreateEmptyViewHolder1(ViewGroup parent) {
        return new SpaceTViewHolder<>(parent);
    }
    protected BaseViewHolder<ModuleState> onCreateLoadingHolder1(ViewGroup parent) {
        return new SpaceTViewHolder<>(parent);
    }

    protected BaseViewHolder<ModuleState> onCreateFailHolder1(ViewGroup parent) {
        return new SpaceTViewHolder<>(parent);
    }


    @Override
    public void onDataItemRangeChanged(BaseViewModule viewModule, int dataPosition, int itemCount, int layoutPosition) {
        //size 无变化
    }

    @Override
    public void onDataItemRangeChanged(BaseViewModule viewModule, int dataPosition, int itemCount, int layoutPosition, Object payload) {
        //size 无变化
    }

    @Override
    public void onDataSizeChangeByInserted(BaseViewModule viewModule, int positionStart, int itemCount) {
        checkDataIsEmpty(viewModule);
    }

    @Override
    public void onDataSizeChangeByRemove(BaseViewModule viewModule, int positionStart, int itemCount) {
        checkDataIsEmpty(viewModule);
    }

    @Override
    public void onDataSizeChange(BaseViewModule viewModule) {
        checkDataIsEmpty(viewModule);
    }


    private void checkDataIsEmpty(BaseViewModule viewModule){
        setState(viewModule.size()==0?ViewModuleState.EMPTY:ViewModuleState.SUCCESS);
    }



}
