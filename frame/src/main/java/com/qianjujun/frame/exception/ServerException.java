package com.qianjujun.frame.exception;

import com.bumptech.glide.load.HttpException;

/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-13  18:28
 */

public class ServerException extends AppException {
    public ServerException(String message, int errorCode) {
        super(message, errorCode);
    }
}
