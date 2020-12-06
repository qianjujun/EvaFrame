package com.qianjujun.vh;

import android.view.ViewGroup;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.data.ModuleState;
import com.hello7890.adapter.databinding.SimpleAdapterVhLoadingBinding;
import com.hello7890.adapter.vh.BaseDbViewHolder;
import com.hello7890.adapter.vh.NoneTDbViewHolder;
import com.qianjujun.R;
import com.qianjujun.databinding.VhLoadingBinding;

public class LoadingVh extends BaseDbViewHolder<ModuleState, SimpleAdapterVhLoadingBinding> {
    public LoadingVh(ViewGroup container) {
        super(R.layout.simple_adapter_vh_loading, container);
    }


    @Override
    public void onBindData(ModuleState moduleState, int dataPosition, int adapterPosition) {

    }
}
