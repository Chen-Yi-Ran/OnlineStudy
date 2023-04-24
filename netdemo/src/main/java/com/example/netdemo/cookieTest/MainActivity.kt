package com.example.netdemo.cookieTest

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.example.netdemo.network.LoginResponse
import com.example.netdemo.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val map= mapOf(
//            "key" to "free",
//            "appid" to "0",
//            "msg" to "你好呀，可以吗"
//        )
//        val httpApi:HttpApi=OkHttpApi()
//        httpApi.get(map,"article/list/0/json",object :IHttpCallback{
//            override fun onSuccess(data: Any?) {
//                LogUtils.d("success result:${data.toString()}")
//                runOnUiThread{
//                   tv_hello.text=data.toString()
//                }
//            }
//
//            override fun onFailed(error: Any?) {
//                LogUtils.d("failed msg:${error.toString()}")
//            }
//
//        })
//        val requestBody=  FormBody.Builder()
//            .add("username", "20011212")
//            .add("password","111111")
//            .build()


                 //Preference.setContext(this)
//
//        val loginWanAndroid = RetrofitHelper.retrofitService.loginWanAndroid("ChenYiRan", "13670332298abc")
//        loginWanAndroid.enqueue(object :retrofit2.Callback<LoginResponse> {
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                LogUtils.d(response.body())
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                LogUtils.d(t.message)
//            }
//
//        })
        val create = ServiceCreator.create<RetrofitService>()
        create.loginWanAndroid("ChenYiRan", "13670332298abc").enqueue(object :Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                LogUtils.d(response.body())
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                LogUtils.d(t.message)
            }

        })
        SystemClock.sleep(1000)
        val create2 = ServiceCreator.create<RetrofitService>()
        create2.addCollectArticle(26281).enqueue(object:Callback<HomeListResponse>{
            override fun onResponse(
                call: Call<HomeListResponse>,
                response: Response<HomeListResponse>
            ) {
                LogUtils.d(response.body())
            }

            override fun onFailure(call: Call<HomeListResponse>, t: Throwable) {
                LogUtils.d(t.message)
            }

        })
        val create3 = ServiceCreator.create<RetrofitService>()
        create3.getLikeList(0).enqueue(object :Callback<CollectionListResponse>{
            override fun onResponse(
                call: Call<CollectionListResponse>,
                response: Response<CollectionListResponse>
            ) {
                LogUtils.d(response.body())
            }

            override fun onFailure(call: Call<CollectionListResponse>, t: Throwable) {
                LogUtils.d(t.message)
            }

        })

//                val requestBody= FormBody.Builder()
//        requestBody.add("username","ChenYiRan")
//        .add("password","13670332298abc")
//
//        httpApi.post(requestBody,"user/login",object :IHttpCallback{
//            override fun onSuccess(data: Any?) {
//                LogUtils.d("success result:${data.toString()}")
//                runOnUiThread{
//                    tv_hello.text=data.toString()
//                }
//            }
//
//            override fun onFailed(error: Any?) {
//                LogUtils.d("failed msg:${error.toString()}")
//            }
//
//        })
        //系统等待200ms然后取消
     //   SystemClock.sleep(200)
     //   httpApi.cancelRequest(requestBody)
    }
//    data class LoginReq( val username:String="ChenYiRan",
//                         val password:String="13670332298abc")



}
