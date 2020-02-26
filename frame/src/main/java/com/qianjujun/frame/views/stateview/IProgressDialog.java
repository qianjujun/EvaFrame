package com.qianjujun.frame.views.stateview;

/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-17  14:57
 */

public interface IProgressDialog {
    //默认，请稍后
    void showProgress();

    //发请求，带loading
    void showProgressWithMessage(String message);


    void showProgressWithProMessage(String message, int progress);

    //直接消失
    void dissmissProgress();

    //消失，弹Toast
    void dissmissWithTip(String tip);

}
