package com.qianjujun.frame.exception;

import android.text.TextUtils;

import com.qianjujun.frame.BuildConfig;



/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-13  13:55
 */

public class AppException extends RuntimeException implements IErrorCode{

    public AppException(Throwable throwable){
        super(throwable);
    }


    private int errorCode;


    public AppException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


    public AppException(int errorCode){
        super();
        this.errorCode = errorCode;
        switch (errorCode){
            case STATE_ERROR_UI_HANDLER:
                break;

            case STATE_ERROR_NO_NET:
                break;
        }


    }


    public AppException(String message){
        super(message);
        this.errorCode = STATE_ERROR_THROW_BY_USER;
    }




    public String getMessage(){
        String message = super.getMessage();
        if(TextUtils.isEmpty(message)){
            switch (errorCode){
                case STATE_ERROR_NO_NET:
                    message = "无网络";
                    break;
                case STATE_EMPTY_DATA:
                    message = "没有数据";
                    break;
                default:
                    message = "服务错误";
                    break;
            }
        }
        return message;
    }


    public static AppException create(Throwable throwable){
        if(throwable instanceof AppException){
            return (AppException) throwable;
        }
        AppException httpException;
        if(BuildConfig.DEBUG){
            httpException = new AppException(throwable);
        }else {
            httpException = new AppException("服务错误",STATE_ERROR_SERVER);
        }

        httpException.errorCode = STATE_ERROR_SERVER;
        return httpException;
    }


    public int getErrorCode() {
        return errorCode;
    }







}
