package com.qianjujun.frame.permission;



/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 14:42
 * @describe
 */
public abstract class PermissionCallBack {
    public void onPermissionGranted(){

    }

    public void onPermissionDenied(boolean refuseForever){

    }
}
