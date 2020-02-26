package com.qianjujun.frame.views.stateview;

import android.view.View;

/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-06  17:42
 */

public interface IErrorViewConfig {
    View getErrorView(int state, String message);

    <T extends View & ILoadAnimation> T getLoadingView();

    View getEmptyView();

}
