package com.example.netdemo

import okhttp3.Callback


//网络请求的统一接口类
interface HttpApi {


    /**
     * 抽象的http的get请求封装，异步
     */
    fun get(params:Map<String,Any?>,path:String,callback: IHttpCallback)

    //可能会出现比如进入首页发起4个请求带有head参数，但是token失效,那么我们可以
    // 使用okhttp校验拦截器，让其中拦截一条请求得到结果让它执行请求去刷新token后，同时拦截另外3条请求，刷新token后再放开另外三条请求
   //刷新token这一块去同步执行
    /**
     * 抽象的http同步的get请求
     */
    fun getSync(params: Map<String, Any?>, path: String):Any?{
        return Any()
    }

    /**
     * 抽象的http的post的请求，异步
     */
    fun post(body:Any,path: String,callback: IHttpCallback)


    /**
     * 抽象的Http的post请求
     */
    fun postSync(body: Any,path: String):Any?=Any()


}