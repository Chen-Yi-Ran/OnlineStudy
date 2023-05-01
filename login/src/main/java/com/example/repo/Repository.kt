package com.example.repo

import androidx.lifecycle.liveData
import com.example.net.LoginNetWork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {
    //一般在仓库层中定义的方法，为了将异步(Retrofit的enqueue会切换到子线程执行网络操作)获取的数据以响应式编程的方式
    //通知给上一层，通常会返回一个LiveData对象。
    //liveData()函数会自动构建并返回一个LiveData对象，然后在它的代码块中提供一个挂起函数的上下文
    //线程参数指定为Dispatchers.IO，这样代码块中的所有代码就都运行在子线程中


    fun registerAccount(username:String,password:String,rePassword:String)= fire(Dispatchers.IO){

        val registerResponse=LoginNetWork.registerWanAndroid(username,password,rePassword)
        if(registerResponse.errorCode==0){
            val data=registerResponse.data
            Result.success(data)
        }else{
            Result.failure(RuntimeException("response errorMsg is${registerResponse.errorMsg}"))
        }
    }

    fun loginAccount(username:String?,password:String?)= fire(Dispatchers.IO){
        val loginResponse=LoginNetWork.loginWanAndroid(username,password)
        if(loginResponse.errorCode==0){
            val data=loginResponse.data
            Result.success(data)
        }else{
            Result.failure(RuntimeException("response errorMsg is${loginResponse.errorMsg}"))
        }
    }


    //新增一个fire()函数，使得每次只需要进行一次try-catch处理
    //lambda表达式要在挂起函数中运行，所以在高阶函数的函数类型中声明一个suspend关键字，这样传入的
        //lambda表达式也是拥有挂起函数的上下文
        private fun <T> fire(context: CoroutineContext, block:suspend()->Result<T>)=
        liveData<Result<T>>(context) {
            val result=try {
                block()
            }catch (e: Exception){
                Result.failure<T>(e)
            }
            emit(result)
        }
}