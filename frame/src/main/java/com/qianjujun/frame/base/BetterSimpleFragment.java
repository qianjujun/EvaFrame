package com.qianjujun.frame.base;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/17 17:39
 * @describe
 */
public abstract class BetterSimpleFragment extends BetterLoadFragment {
    @Override
    protected final int getLayoutId() {
        return 0;
    }

    @Override
    protected final View getLayoutView(ViewGroup container) {
        return getContentView(container);
    }

    @Override
    protected void initView(View contentView) {

    }

    protected abstract View getContentView(ViewGroup container);
}
