package com.qianjujun.testvm;

import com.qianjujun.R;
import com.qianjujun.databinding.VmTestBinding;
import com.qianjujun.frame.adapter.SingleTypeViewModule;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/4 18:27
 * @describe
 */
public class TestVm extends SingleTypeViewModule<String, VmTestBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.vm_test;
    }

    @Override
    protected void onBindData(VmTestBinding dataBinding, String s, int dataPosition, int layoutPosition) {

    }
}
