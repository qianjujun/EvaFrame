package com.qianjujun.vm;

import android.graphics.Color;
import android.view.ViewGroup;

import com.qianjujun.R;
import com.qianjujun.databinding.TestHold1Binding;
import com.qianjujun.frame.adapter.BaseViewHolder;
import com.qianjujun.frame.adapter.GroupViewModule;
import com.qianjujun.frame.utils.ToastUtils;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/22 16:27
 * @describe
 */
public class TestVm5 extends GroupViewModule<Test.Group, Test.Child,TestHold1Binding> {

    public TestVm5() {
        setOnChildItemClickListener((child, dataPosition, layoutPosition) -> ToastUtils.showWarning(child.getText()));
    }

    @Override
    protected BaseViewHolder<Test.Child, ?> onCreateChildViewHolder(ViewGroup parent) {
        return new ChildHolder(parent);
    }

    @Override
    protected int getSpanCount(int dataPosition) {
        return 3;
    }

    @Override
    protected int getGroupLayoutId() {
        return R.layout.test_hold1;
    }

    @Override
    protected void onBindGroupViewHolder(TestHold1Binding dataBing, Test.Group group, int groupPosition, int dataPosition, int layoutPosition) {
        dataBing.text.setText("点击可收缩"+group.getText());
        dataBing.text.setBackgroundColor(Color.RED);
        dataBing.text.setTextColor(Color.WHITE);
    }


    static class ChildHolder extends BaseViewHolder<Test.Child, TestHold1Binding>{

        public ChildHolder(ViewGroup container) {
            super(R.layout.test_hold1, container);
        }

        @Override
        public void onBindData(Test.Child child, int dataPosition, int layoutPosition) {
            mDataBinding.text.setText(child.getText());
            mDataBinding.text.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public boolean isStickyItem(int dataPosition) {
        Test.Child child = getItem(dataPosition);
        return child!=null&&child.isGroup();
    }
}
