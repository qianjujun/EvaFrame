package com.qianjujun.vm;

import android.graphics.Color;

import com.hello7890.adapter.vm.SingleDbViewModule;
import com.qianjujun.R;
import com.qianjujun.databinding.VmChildBinding;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/3 13:57
 * @describe
 */
public class SimpleTextVm extends SingleDbViewModule<String, VmChildBinding> {
    private int backColor;
    private int textColor;
    public SimpleTextVm(){
        this(Color.WHITE,Color.BLACK);
    }

    public SimpleTextVm(int backColor, int textColor) {
        this.backColor = backColor;
        this.textColor = textColor;
    }


    private int columnNum = 1;

    public SimpleTextVm setColumnNum(int columnNum) {
        this.columnNum = columnNum;
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.vm_child;
    }

    @Override
    protected void onBindData(VmChildBinding dataBinding, String s, int dataPosition, int layoutPosition) {
        dataBinding.tvText.setText(s);
        dataBinding.tvText.setBackgroundColor(backColor);
        dataBinding.tvText.setTextColor(textColor);
    }

    @Override
    protected int getSpanCount(int dataPosition) {
        return columnNum;
    }

    @Override
    protected boolean isGridLayout() {
        return true;
    }
}
