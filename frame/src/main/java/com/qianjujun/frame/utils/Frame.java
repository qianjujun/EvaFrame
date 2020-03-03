package com.qianjujun.frame.utils;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 描述 : 说俩句呗
 * 作者 : qianjujun
 * 时间 : 2018/5/1023:57
 * 邮箱 : qianjujun@163.com
 */

public class Frame {
    public static boolean DEBUG = true;
    public static void init(Application context,boolean debug){
        DEBUG = debug;
        ContextProvider.init(context);
        Itn.init(context);
        ScrrenUtil.init(context);
        SPUtils.init(context);
        ToastUtils.init(context);
        if(debug){
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(context);
    }
}
