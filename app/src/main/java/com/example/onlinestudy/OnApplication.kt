package com.example.onlinestudy

import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.BaseApplication
import com.example.common.ktx.application
import com.example.service.assistant.AssistantApp
import com.example.service.moduleService
import com.example.service.network.OkHttpUtil
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

//Application声明类
class OnApplication :BaseApplication(){

    private val modules = arrayListOf<Module>(
//     moduleService, /*moduleHome,*/ moduleLogin, moduleMine, /*moduleStudy, moduleCourse*/
    )

    override fun initConfig() {
        super.initConfig()
        //初始化Arouter框架
        ARouter.init(application)
        //添加common模块之外的其他模块，组件的koin的modules
     //   loadKoinModules(modules)
        //doKit的初始化配置
        AssistantApp.initConfig(application)
        OkHttpUtil.setContext(applicationContext,1000,1000,1000)

    }

}