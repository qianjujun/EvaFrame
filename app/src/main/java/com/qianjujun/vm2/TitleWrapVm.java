package com.qianjujun.vm2;

import android.view.ViewGroup;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.DataViewModule;
import com.hello7890.adapter.vh.BaseDbViewHolder;
import com.qianjujun.R;
import com.qianjujun.databinding.VmTitleWrapBinding;

public class TitleWrapVm extends DataViewModule<String> {

    private TitleWrapVm(){

    }


    public static TitleWrapVm create(String title){
        TitleWrapVm titleWrapVm = new TitleWrapVm();
        titleWrapVm.setData(title);
        return titleWrapVm;
    }


    @Override
    public boolean isStickyItem(int dataPosition) {
        return true;
    }

    @Override
    public BaseViewHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseDbViewHolder<String,VmTitleWrapBinding>(R.layout.vm_title_wrap,parent) {

            @Override
            public void onBindData(String s, int dataPosition, int adapterPosition) {
                mDataBinding.title.setText(s);
            }
        };
    }
}
