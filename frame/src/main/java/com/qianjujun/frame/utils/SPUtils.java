package com.qianjujun.frame.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.UUID;

/**
 * 介绍：sharedpreferences
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-13  13:29
 */

public class SPUtils {

    private static final String KEY_UUID = "KEY_UUID";
    public static final String KEY_MAIL_SAVE = "KEY_MAIL_SAVE";
    public static final String KEY_BALANCE_ADD = "balanceAdd";

    public static final String KEY_USER_PASSWORD = "USER_PASSWORD";

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;

    public static void init(Context context) {
        if(mSharedPreferences==null){
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            mEditor = mSharedPreferences.edit();
        }

    }


    public static String getUUID() {
        String uuid = getString(KEY_UUID);
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            putString(KEY_UUID, uuid);
        }
        return uuid;
    }


    public static void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public static String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public static String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public static void putBollean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public static boolean getBollean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public static boolean getBollean(String key, boolean defaltKey) {
        return mSharedPreferences.getBoolean(key, defaltKey);
    }


    public static void putInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public static int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }


    public static void putLong(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public static long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }




    public static void clearKey(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }






}
