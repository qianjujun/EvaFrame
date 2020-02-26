package com.qianjujun.vm;

import com.qianjujun.R;
import com.qianjujun.databinding.VmHeadBinding;
import com.qianjujun.frame.adapter.SingleTypeViewModule;

public class HeadVm extends SingleTypeViewModule<String, VmHeadBinding> {

    public HeadVm(){
        setData("");
    }
    @Override
    protected int getLayoutId() {
        return R.layout.vm_head;
    }

    @Override
    protected void onBindData(VmHeadBinding dataBinding, String s, int dataPosition, int layoutPosition) {

    }
}
