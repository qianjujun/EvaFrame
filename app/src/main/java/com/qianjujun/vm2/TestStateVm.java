package com.qianjujun.vm2;

import android.view.ViewGroup;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.DbViewModule;
import com.qianjujun.R;
import com.qianjujun.databinding.VmTestStateBinding;
import com.qianjujun.frame.utils.ToastUtils;
import com.qianjujun.vh.EmptyVh;
import com.qianjujun.vh.FailVh;
import com.qianjujun.vh.LoadingVh;

public class TestStateVm extends DbViewModule<String, VmTestStateBinding> {
    private String name;

    public TestStateVm() {
    }

    public TestStateVm(String name) {
        this();
        this.name = name;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.vm_test_state;
    }

    @Override
    protected void onBindData(VmTestStateBinding dataBinding, String bean, int dataPosition, int layoutPosition) {
        dataBinding.text.setText(bean);
    }


    @Override
    protected BaseViewHolder onCreateLoadingHolder(ViewGroup parent) {
        return new LoadingVh(parent);
    }

    @Override
    protected BaseViewHolder onCreateFailHolder(ViewGroup parent) {
        return new FailVh(parent);
    }

    @Override
    protected BaseViewHolder onCreateEmptyViewHolder(ViewGroup parent) {
        return new EmptyVh(parent);
    }

    @Override
    public String toString() {
        return "TestStateVm{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void onModuleItemClick(String s, int dataPosition, int adapterPosition) {
        ToastUtils.show("name:"+name+"  data:"+s);
    }
}
