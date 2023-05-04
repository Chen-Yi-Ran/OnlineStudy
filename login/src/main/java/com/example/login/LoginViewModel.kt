package com.example.login

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils

import com.example.Model.LoginValue
import com.example.common.model.SingleLiveData
import com.example.repo.Repository

//登录界面逻辑的ViewModel
class LoginViewModel :ViewModel(){

    //账号，密码的observable对象
    val obMobile=ObservableField<String>()
    val obPassword=ObservableField<String>()

    private val loLiveData=SingleLiveData<LoginValue>()

    val loginLiveData=Transformations.switchMap(loLiveData){
        it->
        Repository.loginAccount(it.userName,it.password)
    }


    fun goLogin(){
        //当点击按钮的时候会通过给livedata赋值从而进行回调上面的switchMap方法从而进行网络请求
        LogUtils.d("obMobile${obMobile.get()}")
        LogUtils.d("obPassword${obPassword.get()}")
        val data=LoginValue(obMobile.get(),obPassword.get())
        loLiveData.value=data

    }
    fun wechat(context: Context){
       // Toast.makeText(,"当前课程项目为实现注册账号功能", Toast.LENGTH_SHORT).show()
        LogUtils.d("点击了微信")
        ToastUtils.showShort("点击了微信")
    }

    fun qq(view: View){
        LogUtils.d("点击了qq")
        ToastUtils.showShort("点击了qq")
    }

    fun weibo(){
        LogUtils.d("点击了微博")
        ToastUtils.showShort("点击了微博")
    }
    fun AA(view: View) {
        LogUtils.d("静态点击方式")
        ToastUtils.showShort("静态点击方式")
    }
}