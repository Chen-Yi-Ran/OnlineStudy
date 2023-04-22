package com.example.netdemo

import com.blankj.utilcode.util.LogUtils
import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class OkHttpApi:HttpApi {


    companion object{
        private const val TAG="OkHttpApi"//Tag
    }

    private var baseUrl="https://www.wanandroid.com/"

    //okHttpClient
    private val mClient=OkHttpClient.Builder()
            //配置
        .callTimeout(10,TimeUnit.SECONDS)//完整请求超时时长
        .connectTimeout(10,TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
        .readTimeout(10,TimeUnit.SECONDS)//读取服务器返回数据的时长
        .writeTimeout(10,TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(true)//重连
        .followRedirects(false)//重定向
        .cache(Cache(File("sdcard/cache","okhttp"),1024))//okhttp缓存数据存放地址
        .build()



    override fun get(params: Map<String, Any?>, path: String, callback: IHttpCallback) {
        val url="$baseUrl$path"
        val urlBuilder=HttpUrl.Builder()
        //如果get请求有参数，下面语句可以简化的进行拼接
        params.forEach{
            entry ->
            urlBuilder.addEncodedQueryParameter(entry.key,entry.value.toString())
        }
        val request=Request.Builder()
            .get()
            .url(url)
            .build()
        mClient.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
              //  LogUtils.d("response$response")
                callback.onSuccess(response)
            }

        })

    }

    override fun post(body: Any, path: String, callback: IHttpCallback) {
        val url="$baseUrl$path"
        val request=Request.Builder()
            .get()
            .url(url)
            .build()
        mClient.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response)
            }

        })
    }
}