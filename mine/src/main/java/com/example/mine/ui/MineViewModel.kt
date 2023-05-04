package com.example.mine.ui


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.service.repo.UserInfo

class MineViewModel:ViewModel() {

val liveUser=MutableLiveData<UserInfo>()
}