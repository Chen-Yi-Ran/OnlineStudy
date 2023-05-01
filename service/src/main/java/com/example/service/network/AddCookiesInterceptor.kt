package com.example.service.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

/**
添加本地Cookie进行网络访问的拦截器
从Sharepreference中读取Cookie并添加到头部
 */
class AddCookiesInterceptor(context1: Context) :Interceptor {
    private  val context: Context=context1



    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val  preferences=context.getSharedPreferences("cookieData", Context.MODE_PRIVATE)
            .getStringSet("cookie", null) as HashSet<String>?
        if(preferences != null){
            for (i in preferences){
                builder.addHeader("Cookie",i)
            }
        }
        return chain.proceed(builder.build())
    }
}