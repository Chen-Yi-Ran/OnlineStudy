package com.example.mine.repo

import androidx.lifecycle.LiveData
import com.example.service.model.UserInfoResponse


//Mine模块的数据获取 接口
interface IMineResource {

    val liveUserInfo: LiveData<UserInfoResponse>


    //获取userinfo的api函数
    suspend fun getUserInfo()

}