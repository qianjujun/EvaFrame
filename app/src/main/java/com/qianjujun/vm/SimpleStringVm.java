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
public class SimpleStringVm<T extends IString> extends SingleDbViewModule<T, VmChildBinding> {
    private int backColor;
    private int textColor;
    public SimpleStringVm(){
        this(Color.WHITE,Color.BLACK);
    }

    public SimpleStringVm(int backColor, int textColor) {
        this.backColor = backColor;
        this.textColor = textColor;
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
    public int getSpanCount() {
        return columnNum;
    }


}
