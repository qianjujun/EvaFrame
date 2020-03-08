package com.qianjujun.router;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.PretreatmentService;
import com.qianjujun.frame.utils.FrameConstant;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 10:24
 * @describe
 */
@Route(path = "/test/service")
public class PretreatmentServiceImpl implements PretreatmentService {
    @Override
    public boolean onPretreatment(Context context, Postcard postcard) {
        // 跳转前预处理，如果需要自行处理跳转，该方法返回 false 即可

        if(postcard.isFragment()){
            postcard.withString(FrameConstant.KEY_FRAGMENT_NAME,postcard.getPath());
            postcard.setPath(RouterPath.PATH_DEFAULT_ACTIVITY);
        }



        Log.d("qianjujun", "onPretreatment() called with: context = [" + context + "], postcard = [" + postcard + "]");

        return true;
    }

    @Override
    public void init(Context context) {

    }
}
