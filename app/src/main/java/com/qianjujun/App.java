package com.qianjujun;

import android.app.Application;

import com.qianjujun.frame.utils.Frame;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/17 17:15
 * @describe
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Frame.init(this,BuildConfig.DEBUG);
    }
}
