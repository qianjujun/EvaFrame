package com.qianjujun.frame.exception;

import com.bumptech.glide.load.HttpException;

/**
 * 介绍：todo
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-17  16:13
 */

public class EmptyException extends AppException {
    public EmptyException() {
        super(STATE_EMPTY_DATA);
    }
}
