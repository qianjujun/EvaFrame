package com.hello7890.adapter.data

import android.util.Log
import java.util.*

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/2 14:26
 * @describe
 */
object GroupDataHelper {
    const val TAG = "GroupInfoData"
    fun <C, G : GroupData<C>> convert(list: List<G>): List<G> {
        val result: MutableList<G> = ArrayList()
        var childList: List<C>
        Log.d(TAG, "convert: start")
        for (groupData in list) {
            result.add(groupData) //头部数据
            childList = groupData!!.childList
            if (childList != null) {
                for (i in childList.indices) {
                    result.add(groupData)
                }
            }
            result.add(groupData) //尾部数据
        }
        Log.d(TAG, "convert: end")
        return result
    }

    fun <C1, C2, G : Group2Data<C1, C2>> convert2(list: List<G>): List<G> {
        val result: MutableList<G> = ArrayList()
        var childList: List<C1>
        var childList2: List<C2>
        Log.d(TAG, "convert: start")
        for (groupData in list) {
            result.add(groupData) //头部数据
            childList = groupData!!.child1List
            if (childList != null) {
                for (i in childList.indices) {
                    result.add(groupData)
                }
            }
            result.add(groupData) //第一个Child尾部数据
            childList2 = groupData.child2List
            if (childList2 != null) {
                for (i in childList2.indices) {
                    result.add(groupData)
                }
            }
            result.add(groupData) //第二个Child尾部数据
        }
        Log.d(TAG, "convert: end")
        return result
    }
}