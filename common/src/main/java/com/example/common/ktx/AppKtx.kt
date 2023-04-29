package com.example.common.ktx

import android.app.Application

//Application相关的ktx扩展

//扩展属性Application的get方法返回Application本身
val Application.application :Application
get() = this
