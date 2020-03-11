package com.qianjujun.vm;

import android.graphics.Color;
import android.view.ViewGroup;

import com.hello7890.adapter.vm.SingleDbViewModule;
import com.qianjujun.R;
import com.qianjujun.databinding.VmChildBinding;
import com.qianjujun.frame.utils.ToastUtils;

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
//        if(dataPosition%columnNum==1){
//            dataBinding.tvText.setBackgroundColor(Color.parseColor("#60773399"));
//        }else if(dataPosition%columnNum==2){
//            dataBinding.tvText.setBackgroundColor(Color.parseColor("#336699"));
//        }else {
//            dataBinding.tvText.setBackgroundColor(Color.WHITE);
//        }

        dataBinding.tvText.setText(s);

        dataBinding.tvText.setTextColor(textColor);
    }

    @Override
    public int getSpanCount() {
        return columnNum;
    }

    @Override
    protected boolean isGridLayout() {
        return true;
    }


    @Override
    public void onModuleItemClick(String s, int dataPosition, int adapterPosition) {
        super.onModuleItemClick(s, dataPosition, adapterPosition);
        ToastUtils.showSuccess(s);
    }
}
