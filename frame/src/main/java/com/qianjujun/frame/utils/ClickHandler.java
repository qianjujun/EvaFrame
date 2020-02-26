package com.qianjujun.frame.utils;


import com.qianjujun.frame.R;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2019/4/11 10:07
 * @describe app内任何俩次点击事件(包括不同事件)的间隔不得超过1秒
 */
public class ClickHandler {
    private static long lastClickTime;
    private static final long MAX_TIME = 500;
    public static boolean isValidClick(){
        long time = System.currentTimeMillis();
        if(time-lastClickTime<MAX_TIME){
            ToastUtils.showWarning(R.string.better_common_fast_op);
            return false;
        }
        lastClickTime = time;
        return true;
    }
}
