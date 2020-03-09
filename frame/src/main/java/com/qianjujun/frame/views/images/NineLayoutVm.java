package com.qianjujun.frame.views.images;

import android.view.ViewGroup;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.DataChangeListener;
import com.hello7890.adapter.vm.ViewModule;

public class NineLayoutVm<T> extends ViewModule<T> {


    public NineLayoutVm(){
        setDataChangeListener(new DataChangeListener() {
            @Override
            public void onDataItemRangeChanged(BaseViewModule viewModule, int dataPosition, int itemCount, int layoutPosition) {
            }

            @Override
            public void onDataItemRangeChanged(BaseViewModule viewModule, int dataPosition, int itemCount, int layoutPosition, Object payload) {

            }

            @Override
            public void onDataSizeChangeByInserted(BaseViewModule viewModule, int positionStart, int itemCount) {

            }

            @Override
            public void onDataSizeChangeByRemove(BaseViewModule viewModule, int positionStart, int itemCount) {

            }

            @Override
            public void onDataSizeChange(BaseViewModule viewModule) {

            }
        });
    }
    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


}
