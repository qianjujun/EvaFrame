package com.qianjujun.vm;

import android.graphics.Color;
import android.view.ViewGroup;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.vh.BaseDbViewHolder;
import com.hello7890.adapter.vm.ViewModule;
import com.qianjujun.R;
import com.qianjujun.databinding.TestHold1Binding;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 16:22
 * @describe
 */
public class TestVm1 extends ViewModule<Integer> {
    @Override
    public BaseViewHolder<Integer> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestHold1(parent);
    }

    @Override
    public int getSpanCount(int dataPosition) {
        return 3;
    }

    static class TestHold1 extends BaseDbViewHolder<Integer, TestHold1Binding> {

        public TestHold1(ViewGroup container) {
            super(R.layout.test_hold1, container);
        }

        @Override
        public void onBindData(Integer integer, int dataPosition, int adapterPosition) {
            mDataBinding.text.setText(String.valueOf(integer));
            ViewGroup.LayoutParams vl = mDataBinding.text.getLayoutParams();
            if (testStickyItem(dataPosition)) {
                vl.height = 150;
                itemView.setBackgroundColor(Color.RED);
            } else {
                itemView.setBackgroundColor(Color.WHITE);
                vl.height = 350;
            }
        }
    }



    @Override
    public boolean isStickyItem(int dataPosition) {
        return testStickyItem(dataPosition);
    }

    private static boolean testStickyItem(int dataPosition) {
        return dataPosition % 15 == 0;
    }
}
