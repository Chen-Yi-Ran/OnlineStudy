package com.example.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Fragment的抽象父类
 */
abstract class BaseFragment :Fragment {

//    constructor()
//
//    constructor(name:String):super()
//constructor(name:String):this()


    constructor():super()
    constructor(layout:Int):super(layout)


    //私有即使是子类也拿不到，但我们在子类实现的时候通过抽象函数返回真正的bingding对象
    private var mBinding:ViewDataBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutRes(),container,false)//加载进布局
    }

    @LayoutRes
    abstract fun getLayoutRes():Int

    abstract fun bindView(view: View,savedInstanceState: Bundle?): ViewDataBinding

    //onViewCreated在onCreateView之后触发
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //onViewCreated中的view来自onCreateView中(LayoutInflater, ViewGroup, Bundle)
        mBinding= bindView(view, savedInstanceState)//判断view
        mBinding?.apply {
            lifecycleOwner=viewLifecycleOwner
        }
        initConfig()
        initData()

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }
    open fun initData() {

    }



    //open表示可被子类重写
    open fun initConfig() {

    }



    /**
     * 扩展liveData的observe函数
     */
    protected  fun <T:Any> LiveData<T>.observerKt(block:(T?) ->Unit){//liveData回传数据可能为空
        this.observe(viewLifecycleOwner, Observer {
                it->
            block.invoke(it)
        })
    }

}