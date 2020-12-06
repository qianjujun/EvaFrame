package com.hello7890.album.bean;

import com.qianjujun.frame.data.IData;

public class LocalData<T> implements IData<T> {
    private T data;
    private int code;
    @Override
    public T getData() {
        return data;
    }

    @Override
    public boolean isSuccess() {
        return code == 0;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return null;
    }


    public static <T> LocalData<T> success(T data){
        LocalData<T> localData = new LocalData<>();
        localData.data = data;
        return localData;
    }
}
