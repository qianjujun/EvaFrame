package com.qianjujun.frame.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.DimenRes;
import androidx.annotation.StringRes;

/**
 * 介绍：International
 * 作者：xjzhao
 * 邮箱：mr.feeling.heart@gmail.com
 * 时间: 2017-03-08  13:59
 */

public class Itn {
    private static Context context;
    private static Resources resources;

    public static void init(Context context){
        Itn.context = context;
    }

    public static void setResources(Resources resources){
        Itn.resources = resources;
    }

    /**
     * 获取String
     * @param id
     * @return
     */
    public static String getString(@StringRes int id){
        check();
        return resources.getString(id);
    }

    /**
     * 获取颜色
     * @param id
     * @return
     */
    public static int getColor(int id){
        check();
        return resources.getColor(id);
    }

    /**
     * 获取Drawable
     * @param id
     * @return
     */
    public static Drawable getDrawable(int id){
        check();
        return resources.getDrawable(id);
    }

    /**
     * 向上取整
     * @param res
     * @return
     */
    public static int getDimensionPixelOffset(@DimenRes int res){
        check();
        return (int) Math.ceil(resources.getDimension(res));
    }

    /**
     * 获取定义的selector
     * @param selectorId
     * @return
     */
    public static ColorStateList getColorStateList(int selectorId){
        check();
        return resources.getColorStateList(selectorId);
    }

    private static void check(){
        if (null == context) {
            throw new RuntimeException("Context is null in Itn !");
        }else if (null == resources){
            resources = context.getResources();
        }
    }
}
