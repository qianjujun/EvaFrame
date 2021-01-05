package com.hello7890.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.hello7890.adapter.data.IState
import com.hello7890.adapter.data.ModuleState
import com.hello7890.adapter.data.ViewModuleState
import com.hello7890.adapter.databinding.SimpleAdapterVhEmptyBinding
import com.hello7890.adapter.databinding.SimpleAdapterVhFailBinding
import com.hello7890.adapter.databinding.SimpleAdapterVhLoadingBinding
import com.hello7890.adapter.listener.DataStateChangeListener
import com.hello7890.adapter.vh.BaseDbViewHolder
import com.hello7890.adapter.vh.SpaceTViewHolder

internal class StateWrapViewModule : BaseViewModule<ModuleState>(), DataStateChangeListener {
    private val moduleState = ModuleState()
    private fun setDataState(state: ViewModuleState?) {
        if (state == null || wrapVm == null || wrapVm!!.size() > 0) {
            moduleState.state = IState.STATE_SUCCESS
        } else {
            moduleState.state = state.value
        }
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ModuleState> {
        if (wrapVm == null) {
            return SpaceTViewHolder(parent)
        }
        var temp: BaseViewHolder<ModuleState>?
        when (viewType) {
            ModuleState.STATE_EMPTY -> {
                temp = wrapVm!!.onCreateEmptyViewHolder(parent)
                if (temp == null) {
                    temp = onCreateEmptyViewHolder(parent)
                }
            }
            ModuleState.STATE_FAIL -> {
                temp = wrapVm!!.onCreateFailHolder(parent)
                if (temp == null) {
                    temp = onCreateFailHolder(parent)
                }
            }
            ModuleState.STATE_LOADING -> {
                temp = wrapVm!!.onCreateLoadingHolder(parent)
                if (temp == null) {
                    temp = onCreateLoadingHolder(parent)
                }
            }
            ModuleState.STATE_SUCCESS -> temp = SpaceTViewHolder(parent)
            else -> temp = SpaceTViewHolder(parent)
        }
        return temp
    }

    override fun getItemViewType(dataPosition: Int): Int {
        return moduleState.state
    }

    fun wrapWm(viewModule: BaseViewModule<*>?): StateWrapViewModule {
        if (viewModule == null) {
            return this
        }
        viewModule.addDataStateChangeListener(this)
        wrapVm = viewModule
        return this
    }

    private var wrapVm: BaseViewModule<*>? = null
    override fun _getWrapViewModule(): BaseViewModule<*>? {
        return wrapVm
    }

    override fun onCreateEmptyViewHolder(parent: ViewGroup): BaseViewHolder<ModuleState> {
        return object : BaseDbViewHolder<ModuleState, SimpleAdapterVhEmptyBinding>(R.layout.simple_adapter_vh_empty, parent) {
            override fun onBindData(moduleState: ModuleState, dataPosition: Int, adapterPosition: Int) {
                //do nothing
                Log.d(TAG, "onBindData() called with: moduleState = [$moduleState], dataPosition = [$dataPosition], adapterPosition = [$adapterPosition]")
            }
        }
    }

     override fun onCreateLoadingHolder(parent: ViewGroup): BaseViewHolder<ModuleState> {
        return object : BaseDbViewHolder<ModuleState, SimpleAdapterVhLoadingBinding>(R.layout.simple_adapter_vh_loading, parent) {
            override fun onBindData(moduleState: ModuleState, dataPosition: Int, adapterPosition: Int) {
                //do nothing
                Log.d(TAG, "onBindData() called with: moduleState = [$moduleState], dataPosition = [$dataPosition], adapterPosition = [$adapterPosition]")
            }
        }
    }

     override fun onCreateFailHolder(parent: ViewGroup): BaseViewHolder<ModuleState> {
        return object : BaseDbViewHolder<ModuleState, SimpleAdapterVhFailBinding>(R.layout.simple_adapter_vh_fail, parent!!) {
            override fun onBindData(moduleState: ModuleState, dataPosition: Int, adapterPosition: Int) {
                Log.d(TAG, "onBindData() called with: moduleState = [$moduleState], dataPosition = [$dataPosition], adapterPosition = [$adapterPosition]")
                mDataBinding!!.btnReload.setOnClickListener { v: View? ->
                    if (wrapVm != null && wrapVm!!.reloadRunnable != null) {
                        wrapVm!!.reloadRunnable!!.run()
                    }
                }
                mDataBinding!!.tvMessage.text = moduleState.message
            }
        }
    }

    override fun onSizeChange(size: Int) {
        setDataState(if (size > 0) ViewModuleState.SUCCESS else ViewModuleState.EMPTY)
    }

    override fun onLoading() {
        setDataState(ViewModuleState.LOADING)
    }

    override fun onFail(errorCode: Int, message: String?) {
        moduleState.message = message
        moduleState.errorCode = errorCode
        setDataState(ViewModuleState.FAIL)
    }

    companion object {
        const val TAG = "StateWrapViewModule"
    }

    init {
        dataList.add(moduleState)
        setDataState(ViewModuleState.SUCCESS)
    }
}