package com.example.netdemo.cookieTest

import android.app.Application
import com.example.netdemo.network.Preference

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //全局初始化
        Preference.setContext(applicationContext)
        OkHttpUtil.setContext(applicationContext,1000,1000,1000)
    }
}