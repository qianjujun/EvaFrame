package com.qianjujun.rx;


import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qianjujun
 * @date 2017/11/23 下午4:07
 * @e-mail 380180278@qq.com
 */
public class RxUtil {

    /**
     * 统一线程处理/compose简化线程
     *
     * @param <T>
     * @return
     */
    //.delay(300, TimeUnit.MILLISECONDS)
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> FlowableTransformer<T, T> rxBaseParamsHelper(){
        return rxSchedulerHelper();
    }



}
