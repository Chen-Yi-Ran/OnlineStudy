package com.example.mine.ui


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.service.model.CoinInfo
import com.example.service.model.Data
import com.example.service.model.UserInfoResponse
import com.example.service.repo.Repository
import com.example.service.repo.UserInfo

class MineViewModel:ViewModel() {

val liveUser=MutableLiveData<UserInfo>()


val liveUserInfo= MutableLiveData<UserInfoResponse>()

//val getUserInfoLiveData=Transformations.switchMap(liveUserInfo){ _ ->
//    Repository.getUserInfo()
//}
//
//    fun getUserInfoData(){
//    }


}