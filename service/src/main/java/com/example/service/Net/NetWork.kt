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

//    private val getListProject=ServiceCreator.create(NetWorkService::class.java)
//    fun getListProjectWanAndroid(page:Int)=
//        getListProject.getListProject(page)
    private val getBannerInfoService=ServiceCreator.create(NetWorkService::class.java)
    suspend fun getBannerInfoWanAndroid()= getBannerInfoService.getBannerInfo().await()


    private val getCollectOutSideWanAndroid=ServiceCreator.create(NetWorkService::class.java)
    suspend fun getCollectOutSideInfoWanAndroid(title:String,author:String,link:String)=
        getCollectOutSideWanAndroid.addCollectOutsideArticle(title,author, link).await()

    private val getCollectStaticWanAndroid=ServiceCreator.create(NetWorkService::class.java)
    suspend fun getCollectStaticInfoWanAndroid(id:Int)
    =getCollectStaticWanAndroid.addCollectArticle(id).await()

    private val getCollectWanAndroid=ServiceCreator.create(NetWorkService::class.java)
    suspend fun getCollectIndoWanAndroid(page:Int)
    =getCollectWanAndroid.getLikeList(page).await()

    private val getHomeListWanAndroid=ServiceCreator.create(NetWorkService::class.java)
    suspend fun getHomeListInfoWanAndroid(page: Int)= getHomeListWanAndroid.getHomeList(page).await()

    private val getCourseListWanAndroid=ServiceCreator.create(NetWorkService::class.java)
    suspend fun getCourseListInfoWanAndroid()= getCourseListWanAndroid.getSublist().await()



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