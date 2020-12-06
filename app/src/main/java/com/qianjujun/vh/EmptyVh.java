package com.qianjujun.vh;

import android.view.ViewGroup;

import com.hello7890.adapter.data.ModuleState;
import com.hello7890.adapter.databinding.SimpleAdapterVhEmptyBinding;
import com.hello7890.adapter.vh.BaseDbViewHolder;
import com.hello7890.adapter.vh.NoneTViewHolder;
import com.qianjujun.R;

public class EmptyVh extends BaseDbViewHolder<ModuleState, SimpleAdapterVhEmptyBinding> {
    public EmptyVh(ViewGroup container) {
        super(R.layout.simple_adapter_vh_empty, container);
    }



    @Override
    public void onBindData(ModuleState moduleState, int dataPosition, int adapterPosition) {

    }
}
