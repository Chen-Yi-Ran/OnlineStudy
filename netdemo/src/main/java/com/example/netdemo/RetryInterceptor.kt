package com.example.netdemo

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

//重试请求的网络拦截器
class RetryInterceptor(private val maxRetry:Int=0):Interceptor {

    private var retireNum:Int=0//已经重试的次数，注意，设置maxRetry重试次数，作用于重试，所以总的请求次数，可能就是原始的

    override fun intercept(chain: Interceptor.Chain): Response {
        val request:Request =chain.request()
        Log.d("RetryInterceptor", "intercept 29行：当前retriedNum=$retireNum")
        var response=chain.proceed(request)
        while (!response.isSuccessful&&retireNum<maxRetry){
            retireNum++
            Log.d("RetryInterceptor", "intercept 33行：当前retriedNum=$retireNum")
            response=chain.proceed(request)
        }
        return response
    }


}