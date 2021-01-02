package com.qianjujun.vm;

import android.graphics.Color;
import android.view.ViewGroup;

import com.hello7890.adapter.DbViewModule;
import com.qianjujun.R;
import com.qianjujun.databinding.TestHold1Binding;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 16:34
 * @describe
 */
public class TestVm2 extends DbViewModule<String, TestHold1Binding> {
    @Override
    protected int getLayoutId() {
        return R.layout.test_hold1;
    }

    @Override
    protected void onBindData(TestHold1Binding dataBinding, String integer, int dataPosition, int layoutPosition) {
        dataBinding.text.setText(String.valueOf(integer));

        ViewGroup.LayoutParams vl = dataBinding.text.getLayoutParams();
        if(dataPosition==0){
            vl.height = 150;
            dataBinding.getRoot().setBackgroundColor(Color.YELLOW);
        }else {
            dataBinding.getRoot().setBackgroundColor(Color.GRAY);
            vl.height = 350;
        }
    }



    @Override
    public boolean isStickyItem(int dataPosition) {
        return dataPosition==0;
    }

    @Override
    public int getSpanCount() {
        return 3;
    }
}
