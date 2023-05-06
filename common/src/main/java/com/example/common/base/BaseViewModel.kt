package com.example.common.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

open class BaseViewModel:ViewModel() {
    //运行在UI线程的协程
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        try {

                block()

        } catch (e: Exception) {
            //此处接收到BaseRepository里的request抛出的异常
            //根据业务逻辑自行处理代码...

        }
    }
}