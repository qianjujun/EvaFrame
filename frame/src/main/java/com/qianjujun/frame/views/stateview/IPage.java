package com.qianjujun.frame.views.stateview;

/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-06  17:24
 */

public interface IPage {


    void showLoadingView();


    void showErrorView(int errorCode, String msg);


    void showEmptyView();


    void showSuccessView();


}
