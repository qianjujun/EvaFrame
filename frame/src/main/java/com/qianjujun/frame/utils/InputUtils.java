package com.qianjujun.frame.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-05-24  15:48
 */

public class InputUtils {
    public static void hideKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && context.getCurrentFocus() != null) {
            if (context.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    public static void hideKeyboard(EditText editText) {
        if(!editText.hasFocus()){
            hideKeyboard((Activity) editText.getContext());
            return;
        }
        Context context = editText.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            if (editText.getWindowToken() != null) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    public static void showKeyBoard(EditText editText){
        if(editText==null){
            return;
        }
        editText.requestFocus();
        ((InputMethodManager)editText.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE)).showSoftInput(editText, 0);
    }



}
