package com.hello7890.adapter

import android.animation.ObjectAnimator
import android.util.Log
import android.util.SparseArray
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.IntRange
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.hello7890.adapter.data.GroupData
import com.hello7890.adapter.data.GroupDataHelper
import com.hello7890.adapter.databinding.SpaceVmBinding
import com.hello7890.adapter.listener.OnChildItemClickListener
import com.hello7890.adapter.listener.OnGroupItemClickListener
import com.hello7890.adapter.listener.OnModuleItemClickListener
import com.hello7890.adapter.vh.BaseDbViewHolder
import com.hello7890.adapter.vh.SpaceTViewHolder
import java.util.*

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/2 14:40
 * @describe
 */
abstract class GroupViewModule<C, G : GroupData<C>> : ViewModule<G>() {
    private var mRecyclerView: RecyclerView? = null
    private val recyclerViewHelper: RecyclerViewHelper?
    private val groupList: MutableList<G> = ArrayList()
    private val expendGroup: SparseArray<Boolean?>? = SparseArray()
    private var onGroupItemClickListener: OnGroupItemClickListener<C, G>? = null
    private var onChildItemClickListener: OnChildItemClickListener<C, G>? = null
    private var expendable = false
    fun setExpendable(expendable: Boolean) {
        this.expendable = expendable
    }

    fun setOnGroupItemClickListener(onGroupItemClickListener: OnGroupItemClickListener<C, G>?) {
        this.onGroupItemClickListener = onGroupItemClickListener
    }

    fun setOnChildItemClickListener(onChildItemClickListener: OnChildItemClickListener<C, G>?) {
        this.onChildItemClickListener = onChildItemClickListener
    }

    fun expandGroup(groupPosition: Int) {
        opGroup(groupPosition, true)
    }

    fun collapseGroup(groupPosition: Int) {
        opGroup(groupPosition, false)
    }

    private fun opGroup(groupPosition: Int, expend: Boolean) {
        if (groupPosition < 0 || groupPosition >= groupList!!.size) {
            return
        }
        val group = groupList!![groupPosition] ?: return
        val dataPosition = dataList.indexOf(group)
        val childSize = group.childSize
        opGroup2(groupPosition, dataPosition, childSize, expend)
    }

    /**
     * 对收缩或扩展的数据进行空占位处理
     * 对datasize不产生影响
     * @param groupPosition
     * @param dataPosition
     * @param childSize
     * @param expend
     */
    private fun opGroup2(groupPosition: Int, dataPosition: Int, childSize: Int, expend: Boolean) {
        if (mRecyclerView != null) {
            val adapterPosition = dataPosition + startPosition
            val firstCompletePosition = recyclerViewHelper!!.findFirstCompletePosition(mRecyclerView)
            if (firstCompletePosition > adapterPosition) { //处理因吸顶导致实际position已经划出界面外的情况
                mRecyclerView!!.scrollToPosition(adapterPosition)
            }
        }
        expendGroup!!.put(groupPosition, expend)
        notifyItemChanged(dataPosition + 1, childSize)
        notifyItemChanged(dataPosition, CHANGE_EXPEND)
    }

    /**
     * @param groupPosition
     * @param dataPosition
     * @param childSize
     * @param expend
     */
    @Deprecated(""" 真实操作了数据 ，可能会重复操作
      """)
    private fun opGroup(groupPosition: Int, dataPosition: Int, childSize: Int, expend: Boolean) {
        val group = groupList[groupPosition]
        if (expend) {
            val childList: MutableList<G> = ArrayList()
            for (i in 0 until childSize) {
                childList.add(group)
            }
            dataList.addAll(dataPosition + 1, childList)

            notifyItemInserted(dataPosition + 1, group.childList.size)
        } else {
            val iterator = dataList.iterator()
            var data: G?
            var deleteNum = -1
            while (iterator.hasNext()) {
                data = iterator.next()
                if (data == group) { //第一次过掉
                    if (deleteNum == -1) {
                        deleteNum = 0
                    }
                    if (deleteNum >= childSize) {
                        break
                    }
                    iterator.remove()
                    deleteNum++
                }
            }
            notifyItemRemove(dataPosition + 1, childSize)
        }
    }

    fun removeGroup(groupPosition: Int) {
        remove(groupPosition)
    }

    fun removeGroup(group: G) {
        remove(group)
    }

    fun getGroup(groupPosition: Int): G? {
        return if (groupPosition < 0 || groupPosition >= groupList.size) {
            null
        } else groupList[groupPosition]
    }

    fun removeChild(groupPosition: Int, childPosition: Int) {
        val group = getGroup(groupPosition)
        if (group == null) {
            Log.e(TAG, "removeChild: ", RuntimeException("the group is null"))
            return
        }
        val childList = group.getChildList()
        if (childList != null && childPosition >= 0 && childPosition < childList.size) {
            childList.removeAt(childPosition)
        }
        set(groupPosition, group)
    }

    fun addChild(groupPosition: Int, childPosition: Int, child: C?) {
        var childPosition = childPosition
        val group = getGroup(groupPosition)
        if (group == null) {
            Log.e(TAG, "removeChild: ", RuntimeException("the group is null"))
            return
        }
        val childList = group.getChildList()
        if (childPosition < 0) {
            childPosition = 0
        }
        if (childPosition > childList!!.size) {
            childPosition = childList.size
        }
        childList.add(childPosition, child)
        set(groupPosition, group)
    }

    fun addAllChild(groupPosition: Int, childs: MutableList<C?>?) {
        if (childs == null || childs.isEmpty()) {
            return
        }
        val group = getGroup(groupPosition)
        if (group == null) {
            Log.e(TAG, "removeChild: ", RuntimeException("the group is null"))
            return
        }
        val childList = group.childList
        childList!!.addAll(childs)
        set(groupPosition, group)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<G> {
        if (mRecyclerView == null && parent is RecyclerView) {
            mRecyclerView = parent
        }
        val type = viewType % 3
        return when (type) {
            0 -> onCreateChildViewHolder(parent, viewType / 3)
            1 -> onCreateGroupBottomViewHolder(parent, (viewType - 1) / 3)
            2 -> onCreateGroupTopViewHolder(parent, (viewType - 2) / 3)
            else -> SpaceTViewHolder(parent)
        }
    }

    protected abstract fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): ChildHolder<out ViewDataBinding>
    protected abstract fun onCreateGroupTopViewHolder(parent: ViewGroup, viewType: Int): GroupHolder<out ViewDataBinding>

    protected open fun onCreateGroupBottomViewHolder(parent: ViewGroup, viewType: Int): GroupHolder<out ViewDataBinding> {
        return object : GroupHolder<SpaceVmBinding>(R.layout.space_vm, parent) {
            override fun onBindData(dataBing: SpaceVmBinding, group: G, groupIndex: Int, dataPosition: Int, adapterPosition: Int, expend: Boolean) {
                // do nothing
                Log.d(TAG, "onBindGroupData() called with: group = [$group], groupIndex = [$groupIndex], dataPosition = [$dataPosition]")
            }
        }
    }

    abstract inner class ChildHolder<DB : ViewDataBinding>(layoutId: Int, container: ViewGroup) : BaseDbViewHolder<G, DB>(layoutId, container) {
        override fun onBindData(g: G, dataPosition: Int, adapterPosition: Int) {
            val firstIndex = dataList.indexOf(g)
            val childIndex = dataPosition - firstIndex - 1
            val groupIndex = groupList.indexOf(g)
            onBindData(mDataBinding, g, g.getChild(childIndex), groupIndex, childIndex, dataPosition, adapterPosition)
        }


        override fun onBindData(g: G, dataPosition: Int, adapterPosition: Int, payloads: List<*>) {
            val firstIndex = dataList.indexOf(g)
            val childIndex = dataPosition - firstIndex - 1
            val groupIndex = groupList.indexOf(g)
            onBindData(mDataBinding, g, g.getChild(childIndex), groupIndex, childIndex, dataPosition, adapterPosition, payloads)
        }



        protected abstract fun onBindData(dataBing: DB?, group: G?, child: C?, groupIndex: Int, childIndex: Int, dataPosition: Int, adapterPosition: Int)
        protected fun onBindData(dataBing: DB?, group: G?, child: C?, groupIndex: Int, childIndex: Int, dataPosition: Int, adapterPosition: Int, payloads: List<*>?) {}
    }

    fun getChildLocationInfo(dataPosition: Int, location: IntArray?) {
        if (location == null || location.size < 2) {
            throw RuntimeException("数组长度小于2")
        }
        val groupInfo = getItem(dataPosition) ?: throw RuntimeException("dataPosition错误")
        val firstIndex = dataList.indexOf(groupInfo)
        val childIndex = dataPosition - firstIndex - 1
        location[0] = childIndex
        location[1] = groupInfo.childSize
    }

    abstract inner class GroupHolder<DB : ViewDataBinding>(layoutId: Int, container: ViewGroup) : BaseDbViewHolder<G, DB>(layoutId, container) {
        override fun onBindData(g: G, dataPosition: Int, adapterPosition: Int) {
            val groupIndex = groupList!!.indexOf(g)
            onBindData(mDataBinding, g, groupIndex, dataPosition, adapterPosition, expendGroup!![groupIndex, true]!!)
        }

        override fun onBindData(g: G, dataPosition: Int, adapterPosition: Int, payloads: List<*>) {
            val groupIndex = groupList!!.indexOf(g)
            val expend = expendGroup!![groupIndex, true]!!
            if (payloads.contains(CHANGE_EXPEND)) {
                onGroupExpendChange(mDataBinding, g, groupIndex, adapterPosition, expend)
            } else {
                onBindData(mDataBinding, g, groupIndex, dataPosition, adapterPosition, expend, payloads)
            }
        }





        protected abstract fun onBindData(dataBing: DB, group: G, groupIndex: Int, dataPosition: Int, adapterPosition: Int, expend: Boolean)
        protected fun onBindData(dataBing: DB, group: G, groupIndex: Int, dataPosition: Int, adapterPosition: Int, expend: Boolean, payloads: List<*>) {}
        protected open fun onGroupExpendChange(dataBing: DB, group: G, groupIndex: Int, adapterPosition: Int, expend: Boolean) {}
        fun changeExpendImageAnim(imageView: ImageView?, expend: Boolean) {
            if (expend) {
                ObjectAnimator.ofFloat(imageView, "rotation", -180f, 0f)
                        .setDuration(200)
                        .start()
            } else {
                ObjectAnimator.ofFloat(imageView, "rotation", 0f, -180f)
                        .setDuration(200)
                        .start()
            }
        }

        fun changeExpendImage(imageView: ImageView?, expend: Boolean) {
            if (expend) {
                imageView!!.rotation = 0f
            } else {
                imageView!!.rotation = -180f
            }
        }
    }

    /**
     * 插入 修改等操作
     *
     * @param location group的location
     * @return 数据层的location
     */
    private fun convertLocation(location: Int): Int {
        val insertPosition = location - 1
        if (insertPosition < 0) {
            return 0
        }
        var index = 0
        var group: G?
        if (insertPosition >= groupList!!.size) {
            return size()
        }
        for (i in 0 until insertPosition) {
            group = groupList[i]
            index += 2 //头部+尾部
            index += group!!.childSize
        }
        return index
    }


    override fun setList(list: List<G>) {
        groupList.clear()
        if (list == null || list.isEmpty()) {
            clear()
            return
        }
        groupList.addAll(list)
        val result = GroupDataHelper.convert(list)
        val oldSize = size
        if (dataList.isNotEmpty()) {
            dataList.clear()
        }
        dataList.addAll(result!!)
        updateDate(oldSize, size)
    }




    override fun addAll(location: Int, list: List<G>) {
        var location = location
        if (list == null || list.isEmpty()) {
            return
        }
        groupList!!.addAll(list)
        val result = GroupDataHelper.convert(list)
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
        val list = ArrayList<G>()
        list.add(data)
        addAll(groupList.size, list)
    }

    override fun add(location: Int, data: G) {
        val list = ArrayList<G>()
        list.add(data)
        addAll(location, list)
    }

    override fun setData(data: G) {
        val list = ArrayList<G>()
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
        super.setList(GroupDataHelper.convert(groupList))
    }

    override fun removeAll(list: List<G>) {
        groupList.removeAll(list)
        super.setList(GroupDataHelper.convert(groupList))
    }

    // TODO: 2020/3/2  关于remove  可以精准计算出位置信息，提供删除动画 局部更新
    override fun remove(data: G) {
        groupList!!.remove(data)
        super.setList(GroupDataHelper.convert(groupList))
    }

    override fun remove(location: Int) {
        if (location < 0 || location >= groupList!!.size) {
            return
        }
        groupList!!.removeAt(location)
        super.setList(GroupDataHelper.convert(groupList))
    }

    override fun getItemViewType(dataPosition: Int): Int {
        val data = getItem(dataPosition)
        val firstDataIndex = dataList.indexOf(data)
        val groupIndex = groupList!!.indexOf(data)
        val dataType = getDataType(dataPosition)
        return when (dataType) {
            DATA_TYPE_GROUP_TOP -> getTopGroupViewType(groupIndex) * 3 + 2
            DATA_TYPE_GROUP_BOTTOM -> getBottomViewType(groupIndex) * 3 + 1
            DATA_TYPE_CHILD -> {
                val childIndex = dataPosition - firstDataIndex - 1
                getChildItemViewType(groupIndex, childIndex) * 3
            }
            DATA_TYPE_CHILD_SPACE -> spaceViewType
            else -> spaceViewType
        }
    }

    private val spaceViewType: Int
        private get() = ViewType.SPACE_VIEW_TYPE

    @IntRange(from = ViewType.MIN_NORMAL_VIEW_TYPE.toLong(), to = ViewType.MAX_GROUP_VIEW_TYPE.toLong())
    protected fun getChildItemViewType(groupPosition: Int, childPosition: Int): Int {
        return 0
    }

    @IntRange(from = ViewType.MIN_NORMAL_VIEW_TYPE.toLong(), to = ViewType.MAX_GROUP_VIEW_TYPE.toLong())
    protected fun getTopGroupViewType(groupPosition: Int): Int {
        return 0
    }

    @IntRange(from = ViewType.MIN_NORMAL_VIEW_TYPE.toLong(), to = ViewType.MAX_GROUP_VIEW_TYPE.toLong())
    protected open fun getBottomViewType(groupPosition: Int): Int {
        return 0
    }

    private fun getDataType(dataPosition: Int, groupIndex: Int): Int {
        val data = getItem(dataPosition)
        val preData = getItem(dataPosition - 1)
        val nextData = getItem(dataPosition + 1)
        if (data == preData && data == nextData) { //子项
            return if (expendable && !expendGroup!![groupIndex, true]!!) {
                DATA_TYPE_CHILD_SPACE
            } else DATA_TYPE_CHILD
        }
        if (data != preData) { //头
            return DATA_TYPE_GROUP_TOP
        }
        return if (data != nextData) { //尾
            DATA_TYPE_GROUP_BOTTOM
        } else DATA_TYPE_UNKNOWN
    }

    fun getDataType(dataPosition: Int): Int {
        val data = getItem(dataPosition)
        val groupIndex = groupList!!.indexOf(data)
        return getDataType(dataPosition, groupIndex)
    }

    fun isChildItem(dataPosition: Int): Boolean {
        val dataType = getDataType(dataPosition)
        return dataType == DATA_TYPE_CHILD || dataType == DATA_TYPE_CHILD_SPACE
    }

    override fun _getSpanCount(dataPosition: Int): Int {
        return if (isChildItem(dataPosition)) {
            spanCount
        } else 1
    }

    override val spanCount: Int
        get() = childSpanCount

    /**
     * @return
     */
    open val childSpanCount: Int
        get() = 1

    companion object {
        private val TAG: String? = "GroupViewModule3"
        val CHANGE_EXPEND: String? = "GroupViewModule3_change_expend"
        const val DATA_TYPE_GROUP_TOP = 1
        const val DATA_TYPE_GROUP_BOTTOM = 2
        const val DATA_TYPE_CHILD = 3
        const val DATA_TYPE_CHILD_SPACE = 4
        const val DATA_TYPE_UNKNOWN = 5
    }

    init {
        recyclerViewHelper = RecyclerViewHelper()
        setOnModuleItemClickListener(object : OnModuleItemClickListener<G> {
            override fun onModuleItemClick(group: G, dataPosition: Int, adapterPosition: Int) {
                val firstIndex = dataList.indexOf(group)
                val lastIndex = dataList.lastIndexOf(group)
                val childIndex = dataPosition - firstIndex - 1
                val groupIndex = groupList!!.indexOf(group)
                val childSize = group!!.childSize
                when {
                    dataPosition == firstIndex -> {
                        if (expendable && childSize > 0) {
                            if (expendGroup!![groupIndex, true]!!) {
                                opGroup2(groupIndex, dataPosition, childSize, false)
                            } else {
                                opGroup2(groupIndex, dataPosition, childSize, true)
                            }
                        }
                        onGroupItemClickListener?.onGroupItemClick(group, groupIndex, dataPosition, true)
                    }
                    dataPosition == lastIndex -> {
                        onGroupItemClickListener?.onGroupItemClick(group, groupIndex, dataPosition, false)
                    }
                    childIndex < group.childSize -> {
                        onChildItemClickListener?.onChildItemClick(group.getChild(childIndex), group, groupIndex, childIndex, dataPosition)
                    }
                }
            }
        })
    }
}