package com.example.onlinestudy

import com.example.common.BaseApplication
import com.example.common.ktx.application
import com.example.service.assistant.AssistantApp

//Application声明类
class OnApplication :BaseApplication(){


    override fun initConfig() {
        super.initConfig()
        //doKit的初始化配置
        AssistantApp.initConfig(application)
    }

}