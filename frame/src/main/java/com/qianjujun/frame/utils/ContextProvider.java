package com.qianjujun.frame.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 描述 : 说俩句呗
 * 作者 : qianjujun
 * 时间 : 2018/6/180:47
 * 邮箱 : qianjujun@163.com
 */

public class ContextProvider {
    private static Context context;
    public static void init(Context context){
        ContextProvider.context = context;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    public static int screenWidth;

    public static int screenHeight;


    public static Context getContext(){
        return context;
    }

}
