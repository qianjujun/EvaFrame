package com.qianjujun.frame.data;


import com.qianjujun.frame.exception.AppException;
import com.qianjujun.frame.exception.EmptyException;
import com.qianjujun.frame.exception.IErrorCode;
import com.qianjujun.frame.exception.ServerException;
import com.qianjujun.frame.utils.L;


import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-09  13:39
 */

public abstract class OnResponse<T, M extends IData<T>> extends ResourceSubscriber<M> implements IErrorCode {
    L logger = L.getL("OnResponse");

    @Override
    public void onNext(M dt) {
        logger.print();
        if (!dt.isSuccess()) {
            onFail(new ServerException(dt.getMessage(), dt.getCode()));
            onEnd(false,dt.getCode(), dt.getMessage());
            return;
        }


        T t = dt.getData();

        if(t==null){
            onEmptyData();
            onEnd(false,STATE_EMPTY_DATA, dt.getMessage());
            return;
        }

        if(t instanceof List){
            if(((List)t).size()==0){
                onEmptyData();
                onEnd(false,STATE_EMPTY_DATA, dt.getMessage());
                return;
            }
        }

        try {
            onSuccess(t);
            onSuccess(dt);
            onEnd(true,dt.getCode(), dt.getMessage());
        } catch (AppException e) {
            onFail(e);
            onEnd(false,e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            onFail(AppException.create(e));
            onEnd(false,STATE_ERROR_UI_HANDLER,"");
        }



    }

    @Override
    public final void onError(Throwable t) {
        AppException exception = AppException.create(t);

        if(exception.getErrorCode()==IErrorCode.STATE_EMPTY_DATA){
            onEmptyData();
        }else {
            onFail(exception);
        }
        onEnd(false,exception.getErrorCode(), exception.getMessage());
        t.printStackTrace();

    }


    @Override
    public final void onComplete() {

    }

    @Override
    protected final void onStart() {
        super.onStart();
        onBegin();
    }

    public void onSuccess(M date) throws AppException{
    }

    public void onBegin() {
        logger.print();
    }


    public void onEnd(boolean success,int state, String message) {
        logger.print("state:" + state + "message:" + message);
    }


    public void onSuccess(T t) throws AppException{
    }


    public void onFail(AppException ex) {

    }


    public void onEmptyData(){

    }


}
