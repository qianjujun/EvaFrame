package com.hello7890.adapter;


import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 17:17
 * @describe
 */
public class AdapterHelpImpl extends BaseAdapterHelperImpl {


    //按顺序添加ViewModule  key:添加的index  依次加1
    private Map<Integer, BaseViewModule> mIndexViewModules = new TreeMap<>();


    private int lastIndex = 0;


    public AdapterHelpImpl(RecyclerViewAdapter adapter, BaseViewModule... viewModules) {
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
        throw new RuntimeException("After the data changes, must be called notif");
    }


}
