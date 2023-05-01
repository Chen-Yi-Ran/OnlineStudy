package com.example.service.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

/**
 首先设置注册或登录后获取到本地拦截的cookie信息
通过循环将Cookie信息放入到HashSet集合中
然后通过SharePreference将Cookie信息保存在本地
 */
class ReceivedCookiesInterceptor(context1:Context) :Interceptor {

   private var  context=context1


    override fun intercept(chain: Interceptor.Chain): Response {

        val originResponse = chain.proceed(chain.request())
        //这里获取请求返回的cookie
        if(!originResponse.headers("Set-Cookie").isEmpty()){
            val cookies = HashSet<String>()
             for (i in originResponse.headers("Set-Cookie")){
                 cookies.add(i)
             }
            //保存的sharepreference文件名为cookieData
            val sharedPreferences =
                context.getSharedPreferences("cookieData", Context.MODE_PRIVATE);
            val edit = sharedPreferences.edit()
            edit.putStringSet("cookie",cookies)
            edit.commit()
        }
        return originResponse
    }
}