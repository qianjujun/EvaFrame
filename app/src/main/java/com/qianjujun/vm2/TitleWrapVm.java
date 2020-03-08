package com.qianjujun.vm2;

import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.vm.ViewModule;
import com.qianjujun.R;
import com.qianjujun.databinding.VmTitleWrapBinding;

import com.hello7890.adapter.vm.SingleDbViewModule;

public class TitleWrapVm extends SingleDbViewModule<String, VmTitleWrapBinding> {

    private TitleWrapVm(){}

    private ViewModule viewModule;

    public static TitleWrapVm wrap(ViewModule viewModule,String title){
        TitleWrapVm titleWrapVm = new TitleWrapVm();
        titleWrapVm.setData(title);
        titleWrapVm.viewModule = viewModule;
        return titleWrapVm;
    }

    @Override
    protected BaseViewModule getChildBaseViewModule() {
        return viewModule;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.vm_title_wrap;
    }

    @Override
    protected void onBindData(VmTitleWrapBinding dataBinding, String bean, int dataPosition, int layoutPosition) {
        dataBinding.title.setText(bean);
    }


    @Override
    public boolean isStickyItem(int dataPosition) {
        return true;
    }
}
