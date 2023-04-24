package com.example.netdemo.network

import androidx.collection.SimpleArrayMap
import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class OkHttpApi: HttpApi {


    companion object{
        private const val TAG="OkHttpApi"//Tag
    }

    private var baseUrl="https://www.wanandroid.com/"

    //存储请求，用于取消
    private val callMap=SimpleArrayMap<Any,Call>()
    //okHttpClient
    private val mClient=OkHttpClient.Builder()
//            //配置
//        .callTimeout(10,TimeUnit.SECONDS)//完整请求超时时长
//        .connectTimeout(10,TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
//        .readTimeout(10,TimeUnit.SECONDS)//读取服务器返回数据的时长
//        .writeTimeout(10,TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
//        .retryOnConnectionFailure(true)//重连
//        .followRedirects(false)//重定向
//        .cache(Cache(File("sdcard/cache","okhttp"),1024))//okhttp缓存数据存放地址

        .addInterceptor(KtHttpLogInterceptor{//如果添加的是NetWorkInterceptor获取到的只有post请求没有get请求
            logLevel(KtHttpLogInterceptor.LogLevel.BODY)


        }).build()



    override fun get(params: Map<String, Any?>, path: String, callback: IHttpCallback) {
        val url="$baseUrl$path"
        val urlBuilder=url.toHttpUrl().newBuilder()
        //如果get请求有参数，下面语句可以简化的进行拼接
        params.forEach{
            entry ->
            urlBuilder.addEncodedQueryParameter(entry.key,entry.value.toString())
        }
        val request=Request.Builder()
            .get()
            .tag(params)
            .url(urlBuilder.build())
            .cacheControl(CacheControl.FORCE_CACHE)//缓冲控制
            .build()
       val newCall=mClient.newCall(request)
        //存储请求，用于取消
        callMap.put(request.tag(),newCall)
        newCall.enqueue(object :Callback{
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
        LogUtils.d(url)
        val request=Request.Builder()
            .post(Gson().toJson(body).toRequestBody())
            .url(url)
            .tag(body)
//            .addHeader("contentType","multipart/form-data; boundary=<calculated when request is sent>")
            .build()
        val newCall= mClient.newCall(request)
        //存储请求，用于取消
        callMap.put(request.tag(),newCall)
        newCall.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body?.string())
            }

        })


    }

    /**
     * 取消网络请求，tag就是每次请求的id标记，也就是请求的传参
     */
    override fun cancelRequest(tag: Any) {
        callMap.get(tag)?.cancel()
    }

    /**
     * 取消所有网络请求
     */
    override fun cancelAllRequest() {
        for(i in 0 until callMap.size()){
            callMap.get(callMap.keyAt(i))?.cancel()
        }
    }
}