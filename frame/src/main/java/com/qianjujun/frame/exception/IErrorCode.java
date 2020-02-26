package com.qianjujun.frame.exception;

public interface IErrorCode {
    int STATE_ERROR_UI_HANDLER = 10000;//ui层处理数据错误

    int STATE_ERROR_NO_NET = 10001;// 无网络

    int STATE_EMPTY_DATA = 10002;//空数据

    int STATE_ERROR_SERVER = 10003;//服务器 ，网络相关错误
}
