package com.hello7890.adapter.listener

interface DataStateChangeListener {
    fun onSizeChange(size: Int)
    fun onLoading()
    fun onFail(errorCode: Int, message: String?)
    fun onSuccess(){}
}