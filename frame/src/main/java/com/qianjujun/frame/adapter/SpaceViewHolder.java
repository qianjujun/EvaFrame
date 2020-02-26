package com.qianjujun.frame.adapter;

import android.view.ViewGroup;

import com.qianjujun.frame.R;
import com.qianjujun.frame.databinding.SpcaeViewmodelBinding;


/**
 * 介绍：空数据
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间： 2016/8/9
 */
public class SpaceViewHolder extends BaseViewHolder<Object, SpcaeViewmodelBinding>{

    public SpaceViewHolder(ViewGroup parent) {
        super(R.layout.spcae_viewmodel,parent);
    }

    @Override
    public void onBindData(Object o, int dataPosition, int layoutPosition) {
        //do nothing
    }
}
