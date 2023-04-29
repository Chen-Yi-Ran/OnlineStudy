package com.example.common.ktx

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

//Activity相关的KTX，扩展函数或扩展属性

/**
 * Activity中使用DataBinding时setContentView的简化
 * [layout] 布局文件
 * @return 返回一个Binding对象的实例
 */

//注解标签layout为资源文件LayoutRes
fun <T:ViewDataBinding> Activity.bindView(@LayoutRes layout:Int):T{


    return DataBindingUtil.setContentView(this,layout)
}

/**
 * Activity中使用DataBinding时setContentView的简化
 * @return 返回一个Binding的对象实例T类型的 可null的
 */
fun<T:ViewDataBinding> Activity.bindView(view: View):T?{

    return DataBindingUtil.bind<T>(view)
}

/**
 * 界面Activity的沉浸式状态栏，使得可以在状态栏里面显示部分需要的图片
 * 注意点，需要在setContentView之前调用该函数才生效
 */
fun Activity.immediateStatusBar(){
    window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}


/**
 * 软键盘的隐藏
 * [view]事件控件View
 */
fun Activity.dismissKeyBoard(view:View){
    val imm=getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken,0)

}

/**
 * 扩展属性
 */
/**
 * 扩展LifeCycleOwner属性，便于和Fragment之间使用LifeCycleOwner一致性
 */
val ComponentActivity.viewLifeCycleOwner:LifecycleOwner
get()=this

/**
 * Activity的扩展字段，便于和Fragment中使用LiveData之类的时候，参数一直
 */

val Activity.context:Context
get() = this