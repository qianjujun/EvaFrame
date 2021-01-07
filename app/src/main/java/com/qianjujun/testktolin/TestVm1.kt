package com.qianjujun.testktolin

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hello7890.adapter.BaseViewHolder
import com.hello7890.adapter.DbViewModule
import com.hello7890.adapter.ViewModule
import com.hello7890.adapter.listener.OnModuleItemClickListener
import com.qianjujun.R
import com.qianjujun.databinding.VmChildBinding
import kotlinx.android.synthetic.main.simple_adapter_vh_fail.view.*

class TestVm1 : ViewModule<User>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<User> {
        TODO("Not yet implemented")
    }


    fun test(){
        setOnModuleItemClickListener(object : OnModuleItemClickListener<User>{
            override fun onModuleItemClick(t: User, dataPosition: Int, adapterPosition: Int) {

            }

        })


        setOnModuleItemClickListener { t, dataPosition, adapterPosition ->
            Log.d("TAG", "test() called with: t = $t, dataPosition = $dataPosition, adapterPosition = $adapterPosition")
        }
    }

}

class TestVh(parent: ViewGroup,layoutId:Int) : BaseViewHolder<User>(layoutId,parent){
    override fun onBindData(t: User, dataPosition: Int, adapterPosition: Int) {
        TODO("Not yet implemented")
    }

}


class TestVm2 : DbViewModule<User,VmChildBinding>(){
    override val layoutId: Int
        get() = TODO("Not yet implemented")

    override fun onBindData(dataBinding: VmChildBinding, t: User, dataPosition: Int, layoutPosition: Int) {
        TODO("Not yet implemented")
    }


}