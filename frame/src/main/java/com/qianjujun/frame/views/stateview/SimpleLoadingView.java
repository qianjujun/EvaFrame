package com.qianjujun.frame.views.stateview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qianjujun.frame.R;


/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-06  17:49
 */

public class SimpleLoadingView extends FrameLayout implements ILoadAnimation {
    public SimpleLoadingView(@NonNull Context context) {
        super(context);
        init();
    }

    public SimpleLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleLoadingView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }



    private void init(){
        setBackgroundColor(Color.WHITE);
        LayoutInflater.from(getContext()).inflate(R.layout.better_view_loading,this);
        findViewById(R.id.progressBar).setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }
}
