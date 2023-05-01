package com.example.login

import com.example.common.BaseApplication
import com.example.service.moduleService
import com.example.service.network.OkHttpUtil
import org.koin.core.context.loadKoinModules

class LoginApplication: BaseApplication() {

    override fun initConfig() {
        super.initConfig()
        OkHttpUtil.setContext(applicationContext,1000,1000,1000)
        loadKoinModules(moduleService)
 //       loadKoinModules()
    }
}