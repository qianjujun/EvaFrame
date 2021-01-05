package com.hello7890.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.databinding.ViewDataBinding
import com.hello7890.adapter.data.Group2Data
import com.hello7890.adapter.data.GroupDataHelper
import com.hello7890.adapter.databinding.SpaceVmBinding
import com.hello7890.adapter.vh.BaseDbViewHolder
import com.hello7890.adapter.vh.SpaceTViewHolder
import java.util.*

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/28 14:55
 * @describe 暂未实现
 */
abstract class Group2ViewModule<C1, C2, G : Group2Data<C1, C2>> : ViewModule<G>() {
    private var mDataInfo: DataInfo<C1,C2,G>? = null
    private val groupList = ArrayList<G>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<G> {
        val type = viewType % 5
        return when (type) {
            0 -> onCreateGroupTopViewHolder(parent, viewType / 5)
            1 -> onCreateChild1ViewHolder(parent, (viewType - 1) / 5)
            2 -> onCreateChild1BottomViewHolder(parent, (viewType - 2) / 5)
            3 -> onCreateChild2ViewHolder(parent, (viewType - 3) / 5)
            4 -> onCreateChild2BottomViewHolder(parent, (viewType - 4) / 5)
            else -> SpaceTViewHolder(parent)
        }
    }

    protected abstract fun onCreateGroupTopViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder<out ViewDataBinding>
    protected abstract fun onCreateChild1ViewHolder(parent: ViewGroup, viewType: Int): Child1ViewHolder<out ViewDataBinding>
    protected abstract fun onCreateChild2ViewHolder(parent: ViewGroup, viewType: Int): Child2ViewHolder<out ViewDataBinding>
    protected open fun onCreateChild1BottomViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder<out ViewDataBinding> {
        return EmptyGroupViewHolder(parent)
    }

    protected fun onCreateChild2BottomViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder<out ViewDataBinding>{
        return EmptyGroupViewHolder(parent)
    }

    private inner class EmptyGroupViewHolder(container: ViewGroup) : GroupViewHolder<SpaceVmBinding>(R.layout.space_vm, container) {
        override fun onBindData(dataBing: SpaceVmBinding, group: G, groupIndex: Int, dataPosition: Int, adapterPosition: Int) {}
    }

    abstract inner class GroupViewHolder<DB : ViewDataBinding>(layoutId: Int, container: ViewGroup) : BaseDbViewHolder<G, DB>(layoutId, container) {
        override fun onBindData(g: G, dataPosition: Int, adapterPosition: Int) {
            val dataInfo: DataInfo<C1,C2,G> = getDataType(dataPosition)
            onBindData(mDataBinding, g, dataInfo.groupPosition, dataPosition, adapterPosition)
        }

        override fun onBindData(g: G, dataPosition: Int, adapterPosition: Int, payloads: List<*>) {
            val dataInfo: DataInfo<C1,C2,G> = getDataType(dataPosition)
            onBindData(mDataBinding, g, dataInfo!!.groupPosition, dataPosition, adapterPosition, payloads)
        }

        protected abstract fun onBindData(dataBing: DB, group: G, groupIndex: Int, dataPosition: Int, adapterPosition: Int)
        protected fun onBindData(dataBing: DB, group: G, groupIndex: Int, dataPosition: Int, adapterPosition: Int, payloads: List<*>) {}
    }

    abstract inner class Child1ViewHolder<DB : ViewDataBinding>(layoutId: Int, container: ViewGroup) : BaseDbViewHolder<G, DB>(layoutId, container) {
        override fun onBindData(g: G, dataPosition: Int, adapterPosition: Int) {
            val dataInfo: DataInfo<C1,C2,G> = getDataType(dataPosition)
            val child = g!!.getChild1(dataInfo!!.childPosition)
            onBindData(mDataBinding, g, child, dataInfo.groupPosition, dataInfo.childPosition, dataPosition, adapterPosition)
        }

        override fun onBindData(g: G, dataPosition: Int, adapterPosition: Int, payloads: List<*>) {
            val dataInfo: DataInfo<C1,C2,G> = getDataType(dataPosition)
            val child = g!!.getChild1(dataInfo!!.childPosition)
            onBindData(mDataBinding, g, child, dataInfo.groupPosition, dataInfo.childPosition, dataPosition, adapterPosition, payloads)
        }

        protected abstract fun onBindData(dataBing: DB?, group: G?, child: C1?, groupIndex: Int, childIndex: Int, dataPosition: Int, adapterPosition: Int)
        protected fun onBindData(dataBing: DB?, group: G?, child: C1?, groupIndex: Int, childIndex: Int, dataPosition: Int, adapterPosition: Int, payloads: List<*>?) {}
    }

    abstract inner class Child2ViewHolder<DB : ViewDataBinding>(layoutId: Int, container: ViewGroup) : BaseDbViewHolder<G, DB>(layoutId, container) {
        override fun onBindData(g: G, dataPosition: Int, adapterPosition: Int) {
            val dataInfo: DataInfo<C1,C2,G> = getDataType(dataPosition)
            val child = g!!.getChild2(dataInfo!!.childPosition)
            onBindData(mDataBinding, g, child, dataInfo.groupPosition, dataInfo.childPosition, dataPosition, adapterPosition)
        }

        override fun onBindData(g: G, dataPosition: Int, adapterPosition: Int, payloads: List<*>) {
            val dataInfo: DataInfo<C1,C2,G> = getDataType(dataPosition)
            val child = g.getChild2(dataInfo.childPosition)
            onBindData(mDataBinding, g, child, dataInfo.groupPosition, dataInfo.childPosition, dataPosition, adapterPosition, payloads)
        }

        protected abstract fun onBindData(dataBing: DB?, group: G?, child: C2?, groupIndex: Int, childIndex: Int, dataPosition: Int, adapterPosition: Int)
        protected fun onBindData(dataBing: DB?, group: G?, child: C2?, groupIndex: Int, childIndex: Int, dataPosition: Int, adapterPosition: Int, payloads: List<*>?) {}
    }

    fun getGroup(groupPosition: Int): G? {
        return if (groupPosition < 0 || groupPosition >= groupList!!.size) {
            null
        } else groupList[groupPosition]
    }

    @IntRange(from = ViewType.MIN_NORMAL_VIEW_TYPE.toLong(), to = ViewType.MAX_TWO_GROUP_VIEW_TYPE.toLong())
    protected fun getTopGroupViewType(group: G?, groupIndex: Int): Int {
        return 0
    }

    @IntRange(from = ViewType.MIN_NORMAL_VIEW_TYPE.toLong(), to = ViewType.MAX_TWO_GROUP_VIEW_TYPE.toLong())
    protected open fun getChild1ViewType(group: G?, groupPosition: Int, child1Position: Int): Int {
        return 0
    }

    @IntRange(from = ViewType.MIN_NORMAL_VIEW_TYPE.toLong(), to = ViewType.MAX_TWO_GROUP_VIEW_TYPE.toLong())
    protected fun getChild1BottomViewType(group: G?, groupPosition: Int): Int {
        return 0
    }

    @IntRange(from = ViewType.MIN_NORMAL_VIEW_TYPE.toLong(), to = ViewType.MAX_TWO_GROUP_VIEW_TYPE.toLong())
    protected open fun getChild2ViewType(group: G?, groupPosition: Int, child1Position: Int): Int {
        return 0
    }

    @IntRange(from = ViewType.MIN_NORMAL_VIEW_TYPE.toLong(), to = ViewType.MAX_TWO_GROUP_VIEW_TYPE.toLong())
    protected fun getChild2BottomViewType(group: G?, groupPosition: Int): Int {
        return 0
    }

    override fun getItemViewType(dataPosition: Int): Int {
        val group = getItem(dataPosition) ?: return spaceViewType
        val dataInfo: DataInfo<C1,C2,G>? = getDataType(dataPosition)
        val dataType = dataInfo!!.dataType
        val groupIndex = dataInfo.groupPosition
        Log.d(TAG, "getItemViewType() called with: dataPosition = [$dataPosition]$dataType")
        return when (dataType) {
            DATA_TYPE_GROUP_TOP -> getTopGroupViewType(group, groupIndex) * 5
            DATA_TYPE_CHILD1 -> getChild1ViewType(group, groupIndex, dataInfo.childPosition) * 5 + 1
            DATA_TYPE_GROUP_BOTTOM1 -> getChild1BottomViewType(group, groupIndex) * 5 + 2
            DATA_TYPE_CHILD2 -> getChild2ViewType(group, groupIndex, dataInfo.childPosition) * 5 + 3
            DATA_TYPE_GROUP_BOTTOM2 -> getChild2BottomViewType(group, groupIndex) + 4
            else -> spaceViewType
        }
    }

    private val spaceViewType: Int
        private get() = ViewType.SPACE_VIEW_TYPE

    class DataInfo<C1,C2,G : Group2Data<C1, C2>> {
        var dataType = 0
        var groupPosition = 0
        var childPosition = 0
        var data: G? = null

        fun isTopChild(span: Int): Boolean {
            return childPosition / span == 0
        }

        fun isBottomChild(span: Int): Boolean {
            return if (data == null) false else childPosition / span == (data!!.getChild1Size() - 1) / span
        }
    }

    private fun logDataType(dataPosition: Int, firstDataIndex: Int, groupPosition: Int, child1Size: Int, child1Start: Int, child1End: Int) {
        Log.d(TAG, "logDataType() called with: dataPosition = [$dataPosition], firstDataIndex = [$firstDataIndex], groupPosition = [$groupPosition], child1Size = [$child1Size], child1Start = [$child1Start], child1End = [$child1End]")
    }

    fun getDataType(dataPosition: Int): DataInfo<C1,C2,G> {
        if (mDataInfo == null) {
            mDataInfo = DataInfo()
        }
        val data = getItem(dataPosition)
        if (data == null) {
            mDataInfo!!.dataType = DATA_TYPE_UNKNOWN
            return mDataInfo!!
        }
        val firstDataIndex = dataList.indexOf(data)
        val groupPosition = groupList!!.indexOf(data)
        val child1Size = data.getChild1Size()
        val child2Size = data.getChild2Size()

        mDataInfo!!.data = data
        if (firstDataIndex == dataPosition) {
            mDataInfo!!.dataType = DATA_TYPE_GROUP_TOP
            mDataInfo!!.groupPosition = groupPosition
            mDataInfo!!.childPosition = -1
            return mDataInfo!!
        }
        val child1Start = firstDataIndex + 1
        val child1End = child1Start + child1Size
        logDataType(dataPosition, firstDataIndex, groupPosition, child1Size, child1Start, child1End)
        if (dataPosition in child1Start until child1End) {
            mDataInfo!!.dataType = DATA_TYPE_CHILD1
            mDataInfo!!.groupPosition = groupPosition
            mDataInfo!!.childPosition = dataPosition - child1Start
            return mDataInfo!!
        }
        if (dataPosition == child1End) {
            mDataInfo!!.dataType = DATA_TYPE_GROUP_BOTTOM1
            mDataInfo!!.groupPosition = groupPosition
            mDataInfo!!.childPosition = -1
            return mDataInfo!!
        }
        val child2Start = child1End + 1
        val child2End = child2Start + child2Size
        if (dataPosition in child2Start until child2End) {
            mDataInfo!!.dataType = DATA_TYPE_CHILD2
            mDataInfo!!.groupPosition = groupPosition
            mDataInfo!!.childPosition = dataPosition - child2Start
            return mDataInfo!!
        }
        if (dataPosition == child2End) {
            mDataInfo!!.dataType = DATA_TYPE_GROUP_BOTTOM2
            mDataInfo!!.groupPosition = groupPosition
            mDataInfo!!.childPosition = -1
            return mDataInfo!!
        }
        mDataInfo!!.dataType = DATA_TYPE_UNKNOWN
        mDataInfo!!.groupPosition = groupPosition
        mDataInfo!!.childPosition = -1
        return mDataInfo!!
    }

    private fun getRealSize(group: G?): Int {
        var size = 1
        size += group!!.child1Size
        size += 1
        size += group.child2Size
        size += 1
        return size
    }

    private fun convertLocation(location: Int): Int {
        val insertPosition = location - 1
        if (insertPosition < 0) {
            return 0
        }
        if (insertPosition >= groupList!!.size) {
            return size()
        }
        var index = 0
        var group: G?
        for (i in 0 until insertPosition) {
            group = groupList[i]
            index += getRealSize(group)
        }
        return index
    }

    override fun setList(list: List<out G>) {
        groupList!!.clear()
        if (list == null || list.isEmpty()) {
            clear()
            return
        }
        groupList.addAll(list)
        val result = GroupDataHelper.convert2(list)
        val oldSize = size
        if (dataList.isNotEmpty()) {
            dataList.clear()
        }
        dataList.addAll(result!!)
        updateDate(oldSize, size)
    }

    override fun addAll(location: Int, list: List<out G>) {
        var location = location
        if (list == null || list.isEmpty()) {
            return
        }
        groupList!!.addAll(list)
        val result = GroupDataHelper.convert2(list)
        location = convertLocation(location)
        if (location < 0) {
            location = 0
        }
        if (location > dataList.size) {
            location = dataList.size
        }
        val oldSize = size
        dataList.addAll(location, result!!)
        updateDate(oldSize, size)
    }

    override fun add(data: G) {
        val list: MutableList<G> = ArrayList()
        list.add(data)
        addAll(groupList!!.size, list)
    }

    override fun add(location: Int, data: G) {
        val list: MutableList<G> = ArrayList()
        list.add(data)
        addAll(location, list)
    }

    override fun setData(data: G) {
        val list: MutableList<G> = ArrayList()
        list.add(data)
        setList(list)
    }

    override fun set(location: Int, data: G) {
        //可能涉及子项数量变化 难以实现局部刷新
        //throw new RuntimeException("暂未实现!不要调用此方法");
        if (location < 0 || location >= groupList!!.size) {
            return
        }
        groupList!![location] = data
        val list = GroupDataHelper.convert2(groupList)
        super.setList(list!!)
    }

    override fun removeAll(list: List<out G>) {
        groupList.removeAll(list)
        super.setList(GroupDataHelper.convert2(groupList))
    }

    // TODO: 2020/3/2  关于remove  可以精准计算出位置信息，提供删除动画 局部更新
    override fun remove(data: G) {
        groupList!!.remove(data)
        super.setList(GroupDataHelper.convert2(groupList))
    }

    override fun remove(location: Int) {
        if (location < 0 || location >= groupList!!.size) {
            return
        }
        groupList!!.removeAt(location)
        super.setList(GroupDataHelper.convert2(groupList))
    }

    override fun _getSpanCount(dataPosition: Int): Int {
        val dataInfo = getDataType(dataPosition)
        return when (dataInfo!!.dataType) {
            DATA_TYPE_CHILD1 -> getChild1SpanCount(dataInfo.groupPosition, dataInfo.data)
            DATA_TYPE_CHILD2 -> getChild2SpanCount(dataInfo.groupPosition, dataInfo.data)
            else -> 1
        }
    }

    override val spanCount: Int
        get() = super.spanCount

    open fun getChild1SpanCount(groupPosition: Int, group: G?): Int {
        return 1
    }

    open fun getChild2SpanCount(groupPosition: Int, group: G?): Int {
        return 1
    }

    companion object {
        val TAG: String? = "TwoGroupViewModule"
        const val DATA_TYPE_GROUP_TOP = 1
        const val DATA_TYPE_CHILD1 = 2
        const val DATA_TYPE_GROUP_BOTTOM1 = 3
        const val DATA_TYPE_CHILD2 = 4
        const val DATA_TYPE_GROUP_BOTTOM2 = 5
        const val DATA_TYPE_UNKNOWN = 6
    }
}