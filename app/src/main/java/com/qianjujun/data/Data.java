package com.qianjujun.data;

import com.qianjujun.frame.data.IData;

public class Data<T> implements IData<T> {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean isSuccess() {
        return code==0;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> Data<T> success(T data){
        Data<T> result = new Data<>();
        result.setData(data);
        return result;
    }
}
