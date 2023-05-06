package com.example.mine.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils
import com.example.common.base.BaseViewModel
import com.example.common.model.SingleLiveData
import com.example.service.model.CoinInfo
import com.example.service.model.Data
import com.example.service.model.UserInfoResponse
import com.example.service.repo.Repository
import com.example.service.repo.UserInfo

class MineViewModel:BaseViewModel() {

val liveUser=MutableLiveData<UserInfo>()


val liveUserInfo= SingleLiveData<UserInfoResponse>()


//
val loginLiveData=Transformations.switchMap(liveUserInfo){
        _->
    LogUtils.d("Transformations经来了${ Repository.getUserInfo()}")
    val userInfo = Repository.getUserInfo()
    userInfo
}

fun getUserInfo():UserInfoResponse? {
    LogUtils.d("Repository.getUserInfo().value?.getOrNull()${Repository.getUserInfo()}")
    liveUserInfo.value=Repository.getUserInfo().value?.getOrNull()
    return liveUserInfo.value
    }
}


