package com.hello7890.adapter;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 17:17
 * @describe 相同的viewModule的viewHolder返回的viewType不同
 */
public class AdapterHelpImpl2 extends BaseAdapterHelperImpl {


    //按顺序添加ViewModule  key:添加的index  依次加1
    private Map<Integer, BaseViewModule> mIndexViewModules = new TreeMap<>();


    private int lastIndex = 0;


    public AdapterHelpImpl2(RecyclerViewAdapter adapter, BaseViewModule... viewModules) {
        super(adapter);
        updateViewModule(viewModules);
    }




    @Override
    protected void onAddNewViewModule(BaseViewModule viewModule) {
        viewModule.setTypeViewFlag(lastIndex * FLAG_VIEW_TYPE);
        mIndexViewModules.put(lastIndex, viewModule);
        lastIndex++;
    }

    @Override
    protected void onRestData() {
        mIndexViewModules.clear();
        lastIndex = 0;
    }


    @Override
    public BaseViewModule findViewModule(int viewType) {
        int index = viewType / FLAG_VIEW_TYPE;
        if (mIndexViewModules.containsKey(index)) {
            return mIndexViewModules.get(index);
        }
        throw new RuntimeException("After the data changes, must be called notifyXXX");
    }


    @Override
    protected List<BaseViewModule> convert(BaseViewModule... viewModules) {
        List<BaseViewModule> viewModuleList = new ArrayList<>();
        for(BaseViewModule viewModule:viewModules){
            if(viewModule.enableState){
                StateWrapViewModule stateWrapViewModule = new StateWrapViewModule();
                stateWrapViewModule.wrapWm(viewModule);
                addViewModule(stateWrapViewModule,viewModuleList);
            }else {
                addViewModule(viewModule,viewModuleList);
            }


        }
        return viewModuleList;
    }

    private void addViewModule(BaseViewModule viewModule,List<BaseViewModule> viewModuleList){
        if(viewModule==null){
            return;
        }



        viewModuleList.add(viewModule);

        addViewModule(viewModule._getWrapViewModule(),viewModuleList);

    }


}
