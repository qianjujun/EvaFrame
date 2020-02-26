package com.alibaba.android.arouter.launcher;

import android.app.Application;

import com.alibaba.android.arouter.facade.Postcard;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 10:47
 * @describe
 */
public class Router {
    public static void init(Application application,boolean debug){
        if(debug){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
    }

    /**
     * Inject params and services.
     */
    public static void inject(Object thiz) {
        _ARouter.inject(thiz);
    }

    /**
     * Build the roadmap, draw a postcard.
     *
     * @param path Where you go.
     */
    public static Postcard buildFragment(String path) {
        return _ARouter.getInstance().build(path).setFragment(true);
    }

    public static Postcard build(String path) {
        return _ARouter.getInstance().build(path).setFragment(false);
    }


}
