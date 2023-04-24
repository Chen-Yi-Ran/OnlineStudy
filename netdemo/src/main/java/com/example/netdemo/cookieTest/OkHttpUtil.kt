package com.example.netdemo.cookieTest

import android.content.Context
import com.example.netdemo.network.KtHttpLogInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpUtil(context1: Context, connectTimeout: Int, writeTimeOut: Int, readTimeout: Int) {
    private var context: Context = context1

    private lateinit var client: OkHttpClient
    private lateinit var okHttpClient: OkHttpClient

    companion object{
        private  lateinit var okHttpUtil: OkHttpUtil
        fun setContext(context1: Context, connectTimeout: Int, writeTimeOut: Int, readTimeout: Int){
            okHttpUtil = OkHttpUtil(context1,connectTimeout, writeTimeOut, readTimeout)
        }

        fun getClient(): OkHttpClient {
            return okHttpUtil.client
        }

        fun getOkHttpClient(): OkHttpClient {
            return okHttpUtil.okHttpClient
        }
    }
   // 创建OkHttpClient对象，登录时拦截cookie信息
   // 设置连接，读写超时时间，设置网络拦截器，调用接收和添加cookie的拦截器
    init {
        val builder =
            OkHttpClient.Builder().connectTimeout(connectTimeout.toLong(), TimeUnit.SECONDS)
                .writeTimeout(writeTimeOut.toLong(), TimeUnit.SECONDS)
                .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS);
        builder.addNetworkInterceptor(KtHttpLogInterceptor())
        client = builder.addInterceptor(ReceivedCookiesInterceptor(context)).build()
        okHttpClient = builder.addInterceptor(AddCookiesInterceptor(context)).build()
    }


}
object NetWorkUtil{



}