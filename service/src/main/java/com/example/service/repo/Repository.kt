package com.example.service.repo

import androidx.lifecycle.liveData
import com.blankj.utilcode.util.LogUtils
import com.example.service.Net.NetWork
import com.example.service.base.BaseRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository :BaseRepository() {
    //一般在仓库层中定义的方法，为了将异步(Retrofit的enqueue会切换到子线程执行网络操作)获取的数据以响应式编程的方式
    //通知给上一层，通常会返回一个LiveData对象。
    //liveData()函数会自动构建并返回一个LiveData对象，然后在它的代码块中提供一个挂起函数的上下文
    //线程参数指定为Dispatchers.IO，这样代码块中的所有代码就都运行在子线程中


    fun registerAccount(username: String, password: String, rePassword: String) =
        fire(Dispatchers.IO) {

            val registerResponse = NetWork.registerWanAndroid(username, password, rePassword)
            if (registerResponse.errorCode == 0) {
                val data = registerResponse.data
                Result.success(data)
            } else {
                Result.failure(RuntimeException("response errorMsg is${registerResponse.errorMsg}"))
            }
        }

    //注册信息
    fun loginAccount(username: String?, password: String?) = fire(Dispatchers.IO) {

        val loginResponse = NetWork.loginWanAndroid(username, password)
     //   LogUtils.d(loginResponse)
        if (loginResponse.errorCode == 0) {
            //  val data=loginResponse.data
            Result.success(loginResponse)
        } else {
            Result.failure(RuntimeException("response errorMsg is${loginResponse.errorMsg}"))
        }
    }

//   suspend fun getUserInfo() = request {
//        NetWork.getUserInfoWanAndroid()
//    }



    //个人信息
    fun getUserInfo()= fire(Dispatchers.IO){
        val userInfoResponse=NetWork.getUserInfoWanAndroid()
     //   LogUtils.d(userInfoResponse)
        if(userInfoResponse.errorCode==0){
            Result.success(userInfoResponse)
        }else{
            Result.failure(RuntimeException("response errorMsg is${userInfoResponse.errorMsg}"))
        }
    }

    //轮播图信息
    fun getBannerInfo()= fire(Dispatchers.IO){
        val bannerInfoResponse=NetWork.getBannerInfoWanAndroid()
//        LogUtils.d(bannerInfoResponse)
        if(bannerInfoResponse.errorCode==0){
            Result.success(bannerInfoResponse)
        }else{
            Result.failure(RuntimeException("response errorMsg is${bannerInfoResponse.errorMsg}"))
        }
    }

    //收藏站内信息
    fun addCollectStaticInfo(id:Int)= fire(Dispatchers.IO){
        val addCollectInfoResponse=NetWork.getCollectStaticInfoWanAndroid(id)
        LogUtils.d(id)
        LogUtils.d(addCollectInfoResponse)
        if(addCollectInfoResponse.errorCode==0){
            Result.success(addCollectInfoResponse)
        }else{
            Result.failure(RuntimeException("response errorMsg is${addCollectInfoResponse.errorMsg}"))
        }
    }

    //收藏站外信息
    fun addCollectOutSideInfo(title:String,author:String,link:String)= fire(Dispatchers.IO){
        val addCollectInfoResponse=NetWork.getCollectOutSideInfoWanAndroid(title, author, link)
        LogUtils.d(addCollectInfoResponse)
        if(addCollectInfoResponse.errorCode==0){
            Result.success(addCollectInfoResponse)
        }else{
            Result.failure(RuntimeException("response errorMsg is${addCollectInfoResponse.errorMsg}"))
        }
    }

    //获取首页信息列表
    fun getHomeListInfo(page:Int)= fire(Dispatchers.IO){
        val getHomeListResponse=NetWork.getHomeListInfoWanAndroid(page)
        LogUtils.d(getHomeListResponse)
        if(getHomeListResponse.errorCode==0){
            LogUtils.d(getHomeListResponse)
            Result.success(getHomeListResponse)
        }else{
            LogUtils.d(getHomeListResponse.errorMsg)
            Result.failure(RuntimeException("response errorMsg is${getHomeListResponse.errorMsg}"))
        }
    }



    //收藏文章列表

//      fun getListProject(page:Int):ListProjectResponse {
//       return NetWork.getListProjectWanAndroid(page)
//    }

//    suspend fun  request(call: suspend () -> UserInfoResponse): UserInfoResponse {
//        LogUtils.d( "NetWork接口返回数据---------->${this}")
//        return withContext(Dispatchers.IO) {
//            call.invoke()
//
//        }.apply {
//            LogUtils.d( "NetWork接口返回数据---------->${this}")
//            when (errorCode) {
//                0, 200 -> this
//                100, 401 -> ""
//                403 -> ""
//                404 -> ""
//                500 -> ""
//                else -> ""
//            }
//        }
//    }
    //新增一个fire()函数，使得每次只需要进行一次try-catch处理
    //lambda表达式要在挂起函数中运行，所以在高阶函数的函数类型中声明一个suspend关键字，这样传入的
    //lambda表达式也是拥有挂起函数的上下文
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}