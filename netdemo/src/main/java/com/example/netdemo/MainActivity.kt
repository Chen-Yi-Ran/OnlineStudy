package com.example.netdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_main.*

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
    }
}