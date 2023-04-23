package com.example.netdemo

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.FormBody
import okhttp3.MultipartBody
import okhttp3.RequestBody


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val map= mapOf(
            "key" to "free",
            "appid" to "0",
            "msg" to "你好呀，可以吗"
        )
        val httpApi:HttpApi=OkHttpApi()
        httpApi.get(map,"article/list/0/json",object :IHttpCallback{
            override fun onSuccess(data: Any?) {
                LogUtils.d("success result:${data.toString()}")
                runOnUiThread{
                   tv_hello.text=data.toString()
                }
            }

            override fun onFailed(error: Any?) {
                LogUtils.d("failed msg:${error.toString()}")
            }

        })
        val requestBody=  FormBody.Builder()
            .add("username", "20011212")
            .add("password","111111")
            .build()

//        val requestBody= MultipartBody.Builder()
//        requestBody.addFormDataPart("username","ChenYiRan")
//        .addFormDataPart("password","13670332298abc")
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
