package com.hello7890.adapter

import com.hello7890.adapter.data.OpData
import java.util.*

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 16:59
 * @describe
 */
abstract class ViewModule<T> : BaseViewModule<T>(), OpData<T> {
    override fun setList(list: List<T>) {
        if (list == null || list.isEmpty()) {
            clear()
            return
        }
        val oldSize = size
        if (dataList.isNotEmpty()) {
            dataList.clear()
        }
        dataList.addAll(list)
        val newSize = size
        updateDate(oldSize, newSize)
    }

    protected fun updateDate(oldSize: Int, newSize: Int) {
        when {
            oldSize == newSize -> {
                notifyItemRangeChanged(0, newSize)
            }
            oldSize > newSize -> {
                notifyItemRangeChanged(0, newSize)
                notifyItemRemove(newSize, oldSize - newSize)
            }
            else -> {
                notifyItemRangeChanged(0, oldSize)
                notifyItemInserted(oldSize, newSize - oldSize)
            }
        }
    }

    protected fun updateDate(oldSize: Int, newSize: Int, anchorPosition: Int) {
        if (oldSize == newSize) {
            notifyItemRangeChanged(0, newSize)
        } else if (oldSize > newSize) {
            notifyItemRangeChanged(0, anchorPosition)
            notifyItemRemove(anchorPosition, oldSize - newSize)
        } else {
            notifyItemRangeChanged(0, anchorPosition)
            notifyItemInserted(anchorPosition, newSize - oldSize)
        }
    }

    override fun setData(data: T) {
        val list: MutableList<T> = ArrayList()
        list.add(data)
        setList(list)
    }

    override fun set(location: Int, data: T) {
        if (location < 0 || location >= dataList.size) {
            return
        }
        dataList[location] = data
        notifyItemChanged(location)
    }

    override fun add(data: T) {
        add(size(), data)
    }

    override fun add(location: Int, data: T) {
        var location = location
        if (location < 0) {
            location = 0
        }
        if (location > dataList.size) {
            location = dataList.size
        }
        val oldSize = size
        dataList.add(location, data)
        val newSize = size
        updateDate(oldSize, newSize, location)
    }

    override fun addAll(list: List<T>) {
        addAll(size(), list)
    }

    override fun addAll(location: Int, list: List<T>) {
        var location = location
        if (list == null || list.isEmpty()) {
            return
        }
        if (location < 0) {
            location = 0
        }
        if (location > dataList.size) {
            location = dataList.size
        }
        val oldSize = size
        dataList.addAll(location, list)
        val newSize = size
        updateDate(oldSize, newSize)
    }

    override fun remove(location: Int) {
        if (location < 0 || location >= size()) {
            return
        }
        val oldSize = size
        dataList.removeAt(location)
        val newSize = size
        updateDate(oldSize, newSize, location)
    }

    override fun remove(data: T) {
        val location = dataList.indexOf(data)
        remove(location)
    }

    override fun removeAll(list: List<T>) {
        if (list == null || list.isEmpty()) {
            return
        }
        val oldSize = size
        dataList.removeAll(list)
        val newSize = size
        updateDate(oldSize, newSize)
    }

    internal override fun _getWrapViewModule(): BaseViewModule<*>? {
        return if (spanCount > 1) SpaceViewModule() else null
    }

    public final override fun getSpanCount(dataPosition: Int): Int {
        return _getSpanCount(dataPosition)
    }

    internal open fun _getSpanCount(dataPosition: Int): Int {
        return spanCount
    }
}