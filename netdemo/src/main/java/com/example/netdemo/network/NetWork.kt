package com.example.netdemo.network

import android.util.Log
import com.example.netdemo.cookieTest.RetrofitService
import com.example.netdemo.cookieTest.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetWork {

    private val placeService= ServiceCreator.create(RetrofitService::class.java)
    suspend fun searchPlaces()= placeService.loginWanAndroid("ChenYiRan","13670332298abc")
        .await()
    //将await()函数定义成了Call<T>的扩展函数，这样所有返回值是Call类型的Retrofit网络请求接口
    //都可以直接调用await()函数了。
    //await()函数中使用了suspendCoroutine函数来挂起当前协程，并且由于扩展函数的原因，我们现在拥有了
    //Call对象的上下文，那么这里就可以直接调用enqueue()方法让Retrofit发起网络请求。
    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine {
                continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body=response.body()
                    Log.d("SunnyWeatherNetwork", "--->body: "+response)
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