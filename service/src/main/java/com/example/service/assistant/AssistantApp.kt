package com.example.service.assistant

import android.app.Application
import com.didichuxing.doraemonkit.DoraemonKit
import com.example.common.BaseApplication

//配置doKit的工具类
object AssistantApp {

    fun initConfig(application: Application){
        DoraemonKit.install(application, mutableListOf(
            ServerHostKit()
        ))
    }

}