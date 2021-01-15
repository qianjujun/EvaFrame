package com.hello7890.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.IntRange
import com.hello7890.adapter.data.ModuleState
import com.hello7890.adapter.listener.DataStateChangeListener
import com.hello7890.adapter.listener.OnModuleItemClickListener
import com.hello7890.adapter.listener.OnModuleItemLongClickListener
import java.util.*

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/22 14:43
 * @describe
 */
abstract class BaseViewModule<T> : ViewType {
    protected val dataList = ArrayList<T>()
    private val dataChangeListeners: MutableList<DataChangeListener> = ArrayList()
    private val dataStateChangeListeners: MutableList<DataStateChangeListener> = ArrayList()
    var enableState = false
    var reloadRunnable: Runnable? = null
        private set

    fun openEnableState(reloadRunnable: Runnable? = null): BaseViewModule<*> {
        enableState = true
        this.reloadRunnable = reloadRunnable
        return this
    }

    /**
     * 包含的数据model
     * @return
     */
     open  fun _getWrapViewModule(): BaseViewModule<*>? {
        return null
    }

    fun notifyLoading() {
        for (stateChangeListener in dataStateChangeListeners) {
            stateChangeListener.onLoading()
        }
    }

    fun notifyForceLoading() {
        clear()
        for (stateChangeListener in dataStateChangeListeners) {
            stateChangeListener.onLoading()
        }
    }

    fun notifyEmpty() {
        for (stateChangeListener in dataStateChangeListeners) {
            stateChangeListener.onSizeChange(0)
        }
    }

    fun notifyError(errorCode: Int, message: String?) {
        for (stateChangeListener in dataStateChangeListeners) {
            stateChangeListener.onFail(errorCode, message)
        }
    }


    fun notifySuccess(){
        dataStateChangeListeners.forEach {
            it.onSuccess()
        }
    }

    fun notifyDataSizeChange() {
        for (stateChangeListener in dataStateChangeListeners) {
            stateChangeListener.onSizeChange(size())
        }
    }

    fun notifyItemInserted(dataPosition: Int, itemCount: Int) {
        for (mDataChangeListener in dataChangeListeners) {
            mDataChangeListener.onDataSizeChangeByInserted(this, dataPosition, itemCount)
        }
        notifyDataSizeChange()
    }

    fun notifyItemRemove(dataPosition: Int, itemCount: Int) {
        for (mDataChangeListener in dataChangeListeners) {
            mDataChangeListener.onDataSizeChangeByRemove(this, dataPosition, itemCount)
        }
        notifyDataSizeChange()
    }

    fun notifyDataSetChanged() {
        for (mDataChangeListener in dataChangeListeners) {
            mDataChangeListener.onDataSizeChange(this)
        }
        notifyDataSizeChange()
    }

    fun notifyItemRangeChanged(dataPosition: Int, itemCount: Int) {
        for (mDataChangeListener in dataChangeListeners) {
            mDataChangeListener.onDataItemRangeChanged(this, dataPosition, itemCount, startPosition + dataPosition)
        }
    }

    fun notifyDataChanged() {
        for (mDataChangeListener in dataChangeListeners) {
            mDataChangeListener.onDataItemRangeChanged(this, 0, size(), startPosition)
        }
    }

    fun notifyItemChanged(dataPosition: Int) {
        for (mDataChangeListener in dataChangeListeners) {
            mDataChangeListener.onDataItemRangeChanged(this, dataPosition, 1, startPosition + dataPosition)
        }
    }

    fun notifyItemChanged(dataPosition: Int, payload: Any?) {
        for (mDataChangeListener in dataChangeListeners) {
            mDataChangeListener.onDataItemRangeChanged(this, dataPosition, 1, dataPosition + startPosition, payload)
        }
    }

    fun getDataPosition(adapterPosition: Int): Int {
        return adapterPosition - startPosition
    }

    fun clear() {
        if (dataList.isEmpty()) {
            return
        }
        val size = size()
        dataList.clear()
        notifyItemRemove(0, size)
        //notifyDataSetChanged();
    }

    fun addDataChangeListener(dataChangeListener: DataChangeListener?) {
        if (dataChangeListener == null) {
            return
        }
        if (!dataChangeListeners.contains(dataChangeListener)) {
            dataChangeListeners.add(dataChangeListener)
        }
    }

    fun removeDataChangeListener(dataChangeListener: DataChangeListener?) {
        dataChangeListeners.remove(dataChangeListener)
    }

    fun removeDataStateChangeListener(dataStateChangeListener: DataStateChangeListener?) {
        dataStateChangeListeners.remove(dataStateChangeListener)
    }

    fun addDataStateChangeListener(dataStateChangeListener: DataStateChangeListener?) {
        if (dataStateChangeListener == null) {
            return
        }
        if (!dataStateChangeListeners.contains(dataStateChangeListener)) {
            dataStateChangeListeners.add(dataStateChangeListener)
        }
    }

    open fun isStickyItem(dataPosition: Int): Boolean {
        return false
    }



    /**
     * @return 返回源数据  操作后必须调用相关更新方法
     */
    fun _getDataList(): List<T> {
        return dataList
    }

    @IntRange(from = ViewType.MIN_NORMAL_VIEW_TYPE.toLong(), to = ViewType.MAX_NORMAL_VIEW_TYPE.toLong())
    open fun getItemViewType(dataPosition: Int): Int {
        return 0
    }

    //需要在界面上显示的数量
    val size: Int
        get() = size()

    fun getSpanSize(position: Int): Int {
        val maxSpanSize = maxSpanSize
        if (maxSpanSize <= 1) {
            return 1
        }
        val spanCount = _getSpanCount(position)
        if (maxSpanSize % spanCount != 0) {
            throw RuntimeException("最大列数必须是所取列数的整数倍")
        }
        return maxSpanSize / spanCount
    }

    private fun _getSpanCount(position: Int): Int {
        val dataPosition = position - startPosition
        return if (isStickyItem(dataPosition)) { //吸顶item占满全列
            1
        } else getSpanCount(dataPosition)
    }

    /**
     * 特别注意：
     * 约定
     *
     * @param dataPosition
     * @return 约定只返回1或者其他如 返回1或3 1或4 1或5
     *
     *
     * --- ---  2
     * -------  1
     * ---      2
     * -------  1
     *
     *
     * 如返回 3|2|1
     * ---  ---  2
     * -- -- --  3
     * --- --    2|3
     * -- -- --  3
     * --------  1
     * 此混合模式很难找到当前item属于该行的第几列  如需要画分割线 则尽量避免此种组合
     *
     *
     * 多种列结构 建议使用多个module组合非方式
     */
    @IntRange(from = 1)
    protected open fun getSpanCount(dataPosition: Int): Int {
        return spanCount
    }

    open val spanCount: Int
        get() = 1

    private var typeViewFlag = 0
    var startPosition = 0
    var context: Context? = null
        private set
    var itemClickListener: OnModuleItemClickListener<T>? = null
        private set
    var itemLongClickListener: OnModuleItemLongClickListener<T>? = null
        private set

    fun setTypeViewFlag(typeViewFlag: Int) {
        this.typeViewFlag = typeViewFlag
    }

    fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        if (context == null) {
            context = parent.context
        }
        return onCreateViewHolder(parent, viewType)
    }

    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T>

    open fun onCreateEmptyViewHolder(parent: ViewGroup): BaseViewHolder<ModuleState>? {
        return null
    }

    open fun onCreateLoadingHolder(parent: ViewGroup): BaseViewHolder<ModuleState>? {
        return null
    }

    open fun onCreateFailHolder(parent: ViewGroup): BaseViewHolder<ModuleState>? {
        return null
    }

    fun getAdapterItemViewTypeByVmItemType(itemType: Int): Int {
        return typeViewFlag + itemType
    }

    fun handlerStateAndEmptyViewType(dataPosition: Int): Int {
        return if (getItem(dataPosition) == null) { //过滤空数据类型
            ViewType.SPACE_VIEW_TYPE
        } else getItemViewType(dataPosition)
    }

    /**
     * 真实数据的数量
     *
     * @return
     */
    fun size(): Int {
        return dataList.size
    }


    fun setOnModuleItemClickListener(listener:(t: T, dataPosition: Int, adapterPosition: Int)->Unit){
        this.itemClickListener = object : OnModuleItemClickListener<T>{
            override fun onModuleItemClick(t: T, dataPosition: Int, adapterPosition: Int) {
                listener.invoke(t,dataPosition,adapterPosition)
            }
        }
    }


    fun setOnModuleItemClickListener(itemClickListener: OnModuleItemClickListener<T>?) {
        this.itemClickListener = itemClickListener
    }

    fun setOnModuleItemLongClickListener(itemLongClickListener: OnModuleItemLongClickListener<T>?) {
        this.itemLongClickListener = itemLongClickListener
    }

    fun getItem(dataPosition: Int): T? {
        return if (dataPosition >= 0 && dataPosition < dataList.size) {
            dataList[dataPosition]
        } else null
    }

    var maxSpanSize = 1

    val stickyLayoutPosition: List<Int>
        get() {
            val list: MutableList<Int> = ArrayList()
            for (i in 0 until size) {
                if (isStickyItem(i)) {
                    list.add(startPosition + i)
                }
            }
            return list
        }
}