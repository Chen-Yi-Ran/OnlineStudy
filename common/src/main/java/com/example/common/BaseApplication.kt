package com.example.common

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/**
 * 抽象的公用BaseApplication
 */
abstract class BaseApplication :Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@BaseApplication)
        }
    }

}