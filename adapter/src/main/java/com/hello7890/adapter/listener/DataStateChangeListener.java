package com.hello7890.adapter.listener;

public interface DataStateChangeListener {
    void onSizeChange(int size);

    void onLoading();

    void onFail(int errorCode,String message);
}
