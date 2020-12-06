package com.qianjujun.vh;

import android.view.ViewGroup;

import com.hello7890.adapter.data.ModuleState;
import com.hello7890.adapter.vh.BaseDbViewHolder;
import com.hello7890.adapter.vh.NoneTDbViewHolder;
import com.qianjujun.R;
import com.qianjujun.databinding.VhFailBinding;

public class FailVh extends BaseDbViewHolder<ModuleState,VhFailBinding> {



    public FailVh(ViewGroup container) {
        super(R.layout.vh_fail, container);
    }



    @Override
    public void onBindData(ModuleState moduleState, int dataPosition, int adapterPosition) {
        mDataBinding.test.setText(moduleState.getMessage());
    }
}
