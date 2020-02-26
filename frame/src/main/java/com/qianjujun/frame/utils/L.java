package com.qianjujun.frame.utils;

import android.util.Log;

/**
 * 介绍：log打印
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/17
 */
public final class L {
    public static final String DEFAULT_TAG = "WSN";
    private static boolean DEBUG = true;
    static {
        //DEBUG = ConfigUtil.isDebug();

    }


    public static void setDebug(boolean debug){
        DEBUG = debug;
        //DEBUG = ConfigUtil.isDebug();

    }

    public static void i(String message) {
        if (DEBUG) {
            Log.i(DEFAULT_TAG, message+"");
        }
    }

    public static void i(String tag, String message) {
        if (DEBUG) {
            Log.i(tag, message+"");
        }
    }

    public static void v(String message) {
        if (DEBUG) {
            Log.v(DEFAULT_TAG, message+"");
        }
    }

    public static void v(String tag, String message) {
        if (DEBUG) {
            Log.v(tag, message+"");
        }
    }

    public static void d(String message) {
        if (DEBUG) {
            Log.d(DEFAULT_TAG, message+"");
        }
    }

    public static void d(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message+"");
        }
    }


    public static void e(String message) {
        if (DEBUG) {
            Log.e(DEFAULT_TAG, message+"");
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message+"");
        }
    }


    public static L getL(Class<?> clazz) {
        return new L(clazz.getSimpleName());
    }

    public static L getL(String tag){
       return new L(tag);
    }

    private final String tag;

    private L(String tag) {
        this.tag = tag;
    }

    public void print(String message) {
        if (DEBUG) {
            Log.d(tag, getFunctionName() +"    "+ message);
        }
    }

    public void print() {
        if (DEBUG) {
            Log.d(tag, getFunctionName());
        }
    }


    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }
            return "[ " + Thread.currentThread().getName() + ": "
                    + st.getFileName() + ":" + st.getLineNumber() + " "
                    + st.getMethodName() + " ]";
        }
        return null;
    }
}
