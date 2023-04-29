package com.example.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.common.ktx.bindView
import com.example.common.ktx.viewLifeCycleOwner

abstract class BaseActivity<ActBinding:ViewDataBinding> :AppCompatActivity{

    constructor():super()

    constructor(@LayoutRes layout:Int):super(layout)

    //protected表示子类可以取到对象
    protected lateinit var mBinding:ActBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       mBinding= bindView<ActBinding>(getLayoutRes())
        initConfig()
        initView()
        initData()
    }

    /**
     * 扩展liveData的observe函数
     */
    protected  fun <T:Any> LiveData<T>.observerKt(block:(T?) ->Unit){
        this.observe(viewLifeCycleOwner, Observer {
            it->
            block.invoke(it)
        })
    }
    @LayoutRes
    abstract fun getLayoutRes():Int

    open fun initData() {

    }
    open fun initView() {

    }


    //open表示可被子类重写
    open fun initConfig() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if(this::mBinding.isInitialized){
            mBinding.unbind()
        }
    }

}