package com.qianjujun.vm;

import com.hello7890.adapter.vm.SingleDbViewModule;
import com.qianjujun.R;
import com.qianjujun.databinding.VmHeadBinding;

public class HeadVm extends SingleDbViewModule<String, VmHeadBinding> {

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
