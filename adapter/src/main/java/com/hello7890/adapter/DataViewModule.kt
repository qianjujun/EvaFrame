package com.hello7890.adapter

abstract class DataViewModule<T> : BaseViewModule<T>() {
    fun setData(data: T) {
        dataList.clear()
        dataList.add(data)
        notifyItemChanged(0)
    }

    val data: T?
        get() = if (dataList.isEmpty()) null else _getDataList()[0]
}