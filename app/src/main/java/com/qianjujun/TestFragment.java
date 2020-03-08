package com.qianjujun;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qianjujun.frame.base.BetterSimpleFragment;
import com.qianjujun.router.RouterPath;


/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/17 17:38
 * @describe
 */


@Route(path = RouterPath.PATH_TEST)
public class TestFragment extends BetterSimpleFragment {

    public TestFragment(){}


    @Override
    protected View getContentView(ViewGroup container) {
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(16);
        textView.setTextColor(Color.BLACK);
        textView.setText("测试页面");
        return textView;
    }


    @Override
    protected void initView(View contentView) {

    }
}
