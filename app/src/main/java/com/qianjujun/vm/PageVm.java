package com.qianjujun.vm;

import com.qianjujun.R;
import com.qianjujun.databinding.ItemPageBinding;
import com.qianjujun.frame.adapter.SingleTypeViewModule;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/4 15:15
 * @describe
 */
public class PageVm extends SingleTypeViewModule<String, ItemPageBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_page;
    }

    @Override
    protected void onBindData(ItemPageBinding dataBinding, String s, int dataPosition, int layoutPosition) {
        dataBinding.text.setText("page"+s);
    }
}
