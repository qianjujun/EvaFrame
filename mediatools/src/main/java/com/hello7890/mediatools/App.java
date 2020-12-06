package com.hello7890.mediatools;

import android.app.Application;

import com.qianjujun.frame.utils.Frame;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Frame.init(this, BuildConfig.DEBUG);
    }
}
