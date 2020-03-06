package com.qianjujun.vm;

import android.graphics.Color;

import com.qianjujun.R;
import com.qianjujun.databinding.VmChildBinding;
import com.qianjujun.frame.adapter.SingleTypeViewModule;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/3 13:57
 * @describe
 */
public class SimpleStringVm<T extends IString> extends SingleTypeViewModule<T, VmChildBinding> {
    private int backColor;
    private int textColor;
    public SimpleStringVm(){
        this(Color.WHITE,Color.BLACK);
    }

    public SimpleStringVm(int backColor, int textColor) {
        this.backColor = backColor;
        this.textColor = textColor;
    }

    @Override
    public int getSize() {
        return super.getSize();
    }

    private int columnNum = 1;

    public SimpleStringVm setColumnNum(int columnNum) {
        this.columnNum = columnNum;
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.vm_child;
    }

    @Override
    protected void onBindData(VmChildBinding dataBinding, T s, int dataPosition, int layoutPosition) {
        dataBinding.tvText.setText(s.getStringText());
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
