package com.qianjujun.frame.views.title;

import android.view.View;

import androidx.annotation.StringRes;

/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-14  14:53
 */

public interface ITitleUpdate {
    void updateLeftImageRes(int leftImageRes);

    void updateLeftText(String leftText);

    void updateLeftText(@StringRes int leftTextRes);
    
    void updateCenterImageRes(int centerImageRes);

    void updateCenterText(String centerText);

    void updateCenterText(@StringRes int centerTextRes);

    void updateCenterCoustomView(View centerCoustomView);

    void updateRightImageRes(int rightImageRes);

    void updateRightText(String rightText);

    void updateRightText(@StringRes int rightTextRes);

    void updateRight2ImageRes(int right2ImageRes);

    void updateRight2Text(String right2Text);

    void updateRight2Text(@StringRes int right2TextRes);

    void updateRightImageNum(int imageRes, int num);

    void updateRightNum(int num);

    void updateRight2ImageNum(int imageRes, int num);

    void updateRight2Num(int num);


    void updateDivider(boolean visable);




}
