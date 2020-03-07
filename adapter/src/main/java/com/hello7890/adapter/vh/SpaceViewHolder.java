package com.hello7890.adapter.vh;

import android.view.ViewGroup;

import com.hello7890.adapter.R;


/**
 * 介绍：空数据
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间： 2016/8/9
 */
public class SpaceViewHolder extends NoneTViewHolder{


    public SpaceViewHolder(ViewGroup container) {
        super(R.layout.space_vm, container);
    }

    @Override
    protected void onBindData(int dataPosition, int layoutPosition) {
        //do nothing
    }
}
