package com.qianjujun.vh;

import android.view.ViewGroup;

import com.hello7890.adapter.vh.NoneTViewHolder;
import com.qianjujun.R;

public class EmptyVh extends NoneTViewHolder {
    public EmptyVh(ViewGroup container) {
        super(R.layout.vh_empty, container);
    }

    @Override
    protected void onBindData(int dataPosition, int layoutPosition) {

    }
}
