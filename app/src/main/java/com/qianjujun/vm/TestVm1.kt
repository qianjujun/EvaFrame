package com.qianjujun.vm

import android.graphics.Color
import android.view.ViewGroup
import com.hello7890.adapter.BaseViewHolder
import com.hello7890.adapter.ViewModule
import com.hello7890.adapter.vh.BaseDbViewHolder
import com.qianjujun.R
import com.qianjujun.databinding.TestHold1Binding

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 16:22
 * @describe
 */
open class TestVm1 : ViewModule<Int?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Int?>? {
        return TestHold1(parent)
    }

    override fun getSpanCount(): Int {
        return 3
    }

    internal class TestHold1(container: ViewGroup) : BaseDbViewHolder<Int?, TestHold1Binding>(R.layout.test_hold1, container) {
        override fun onBindData(t: Int?, dataPosition: Int, adapterPosition: Int) {
            mDataBinding!!.text.text = t.toString()
            val vl = mDataBinding!!.text.layoutParams
            if (testStickyItem(dataPosition)) {
                vl.height = 150
                itemView.setBackgroundColor(Color.RED)
            } else {
                itemView.setBackgroundColor(Color.WHITE)
                vl.height = 350
            }
        }

    }

    override fun isStickyItem(dataPosition: Int): Boolean {
        return testStickyItem(dataPosition)
    }

    companion object {
        private fun testStickyItem(dataPosition: Int): Boolean {
            return dataPosition % 15 == 0
        }
    }
}