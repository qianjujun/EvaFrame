package com.qianjujun.vh;

import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

import com.hello7890.adapter.vh.NoneTDbViewHolder;
import com.hello7890.adapter.vh.NoneTViewHolder;
import com.qianjujun.R;
import com.qianjujun.databinding.VhFailBinding;

public class FailVh extends NoneTDbViewHolder<VhFailBinding> {



    public FailVh(ViewGroup container) {
        super(R.layout.vh_fail, container);
    }


    @Override
    protected void onBindData(VhFailBinding dataBind, int dataPosition, int layoutPosition) {
    }
}
