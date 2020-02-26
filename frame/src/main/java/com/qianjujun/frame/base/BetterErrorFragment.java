package com.qianjujun.frame.base;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/17 16:34
 * @describe
 */
public class BetterErrorFragment extends BetterSimpleFragment{


    @Override
    protected View getContentView(ViewGroup container) {
        TextView view = new TextView(container.getContext());
        ViewGroup.LayoutParams vl = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(vl);
        view.setText("未找到该页面");
        view.setGravity(Gravity.CENTER);
        view.setTextColor(Color.BLACK);
        view.setTextSize(16);
        return view;
    }


}
