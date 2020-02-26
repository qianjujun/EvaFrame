package com.qianjujun;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;


/**
 * 介绍：router 拦截器
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2018-05-21  18:19
 */

@Interceptor(priority = 1, name = "测试用拦截器")
public class RouterInterceptor implements IInterceptor ,RouterPath{
    public static final String TAG = "RouterInterceptor";
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

        String path = postcard.getPath();
        String group = postcard.getGroup();


        log(path,group);
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {

    }

    private  void log(String path,String group){
        Log.d(TAG, "log() called with: path = [" + path + "], group = [" + group + "]");
    }
}
