package com.qianjujun.vh;

import android.view.ViewGroup;

import com.hello7890.adapter.vh.NoneTDbViewHolder;
import com.qianjujun.R;
import com.qianjujun.databinding.VhLoadingBinding;

public class LoadingVh extends NoneTDbViewHolder<VhLoadingBinding> {
    public LoadingVh(ViewGroup container) {
        super(R.layout.simple_adapter_vh_loading, container);
    }

    @Override
    protected void onBindData(VhLoadingBinding dataBind, int dataPosition, int layoutPosition) {

    }
}
