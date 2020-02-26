package com.qianjujun.frame.views.stateview;

import android.view.View;

/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-06  17:46
 */

public class SimpleErrorViewConfig implements IErrorViewConfig {
    @Override
    public View getErrorView(int state, String message) {
        return null;
    }

    @Override
    public <T extends View & ILoadAnimation> T getLoadingView() {
        return null;
    }

    @Override
    public View getEmptyView() {
        return null;
    }
}
