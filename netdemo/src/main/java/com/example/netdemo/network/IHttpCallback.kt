package com.example.netdemo.network

//网络请求的接口回调
interface IHttpCallback {

    /**
     * 网络请求成功的回调
     * [data]返回回调成功的数据结果
     */
    fun onSuccess(data:Any?)

    /**
     * 接口回调失败
     * [error]错误信息的数据类
     */
    fun onFailed(error: Any?)
}