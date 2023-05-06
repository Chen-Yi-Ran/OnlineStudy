package com.example.service.Net

import android.util.Log
import com.blankj.utilcode.util.LogUtils
import com.example.service.model.UserInfoResponse
import com.example.service.network.ServiceCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//定义一个统一的网络数据源访问入口，对所有网络请求的API进行封装
object NetWork {

    private val registerService=ServiceCreator.create(NetWorkService::class.java)
    suspend fun registerWanAndroid(username:String,password:String,rePassword:String)
    = registerService.registerWanAndroid(username,password,rePassword).await()

    private val loginService=ServiceCreator.create(NetWorkService::class.java)
    suspend fun loginWanAndroid(username:String?,password:String?)=
        loginService.loginWanAndroid(username, password).await()


    private val getUserInfoService=ServiceCreator.create(NetWorkService::class.java)
    suspend fun getUserInfoWanAndroid()= getUserInfoService.getUserInfo().await()



    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine {
            continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body=response.body()
                    Log.d("LoginNetwork", "--->body: "+response)
                    if(body!=null){
                        continuation.resume(body)
                    }else{
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }




}