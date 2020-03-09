package com.hello7890.adapter;

import android.util.SparseArray;

import com.hello7890.adapter.vm.ViewModule;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现多个相同viewModule的viewHolder的共享
 */
public class AdapterHelpImpl2 extends BaseAdapterHelperImpl {

    private static SparseArray<String> vmNames = new SparseArray<>();


    public AdapterHelpImpl2(RecyclerViewAdapter adapter, BaseViewModule... viewModules) {
        super(adapter);
        updateViewModule(viewModules);
    }

    @Override
    protected void onAddNewViewModule(BaseViewModule viewModule) {
        String name = viewModule.getClass().getName();
        int vmIndex = vmNames.indexOfValue(viewModule.getClass().getName());
        if (vmIndex == -1) {
            vmIndex = vmNames.size();
            vmNames.put(vmIndex, name);

        }
        viewModule.setTypeViewFlag(vmIndex * FLAG_VIEW_TYPE);
    }

    @Override
    protected void onRestData() {
        //do nothing
    }

    @Override
    public BaseViewModule findViewModule(int viewType) {
        int index = viewType / FLAG_VIEW_TYPE;
        String className = vmNames.get(index);
        BaseViewModule viewModule = findOneVmByName(className);
        if(viewModule!=null){
            return viewModule;
        }
        throw new RuntimeException("After the data changes, must be called notifyXXX");
    }


    @Override
    protected List<BaseViewModule> convert(BaseViewModule... viewModules) {
        List<BaseViewModule> viewModuleList = new ArrayList<>();
        for(BaseViewModule viewModule:viewModules){
            addViewModule(viewModule,viewModuleList);
        }
        return viewModuleList;
    }

    private void addViewModule(BaseViewModule viewModule,List<BaseViewModule> viewModuleList){
        if(viewModule==null){
            return;
        }
        viewModuleList.add(viewModule);
        addViewModule(viewModule.getChildBaseViewModule(),viewModuleList);

    }
}
