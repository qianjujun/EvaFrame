package com.qianjujun.vm;

import android.graphics.Color;

import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.DbViewModule;
import com.qianjujun.ColorUtils;
import com.qianjujun.R;
import com.qianjujun.databinding.VmChildBinding;
import com.qianjujun.frame.utils.ToastUtils;

import org.jetbrains.annotations.Nullable;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/3 13:57
 * @describe
 */
public class SimpleTextVm extends DbViewModule<String, VmChildBinding> {
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


        dataBinding.tvText.setBackgroundColor(ColorUtils.getColor(dataPosition));

        dataBinding.tvText.setText(s);

        dataBinding.tvText.setTextColor(textColor);
    }

    @Override
    public int getSpanCount() {
        return columnNum;
    }



}
