package com.qianjujun.frame.data;

public interface IData<T> {
    T getData();

    boolean isSuccess();

    int getCode();

    String getMessage();
}
