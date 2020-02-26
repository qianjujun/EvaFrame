package com.qianjujun.frame.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 11:35
 * @describe
 */
public abstract class BetterDbFragment<T extends ViewDataBinding> extends BetterLoadFragment {

    protected T mDataBinding;

    @Override
    protected final View getLayoutView(ViewGroup container) {
        return super.getLayoutView(container);
    }

    @Override
    protected final void initView(View contentView) {
        mDataBinding = DataBindingUtil.bind(contentView);
        initView(mDataBinding);
    }

    protected abstract void initView(T dataBinding);
}
