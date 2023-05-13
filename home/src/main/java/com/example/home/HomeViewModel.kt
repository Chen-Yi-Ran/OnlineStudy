package com.example.home

import androidx.lifecycle.Transformations
import com.blankj.utilcode.util.LogUtils
import com.example.common.base.BaseViewModel
import com.example.common.model.SingleLiveData
import com.example.service.model.BannerResponse
import com.example.service.repo.Repository


class HomeViewModel:BaseViewModel() {

    val bannerInfo= SingleLiveData<BannerResponse>()

    val addStaticCollect= SingleLiveData<Int>()

    val addOutSideCollect= SingleLiveData<com.example.service.model.HomeResponse.Data.DataX>()


    val getHomeList=SingleLiveData<Int>()

    val bannerLiveData= Transformations.switchMap(bannerInfo){
            _->
     //   LogUtils.d("Transformations经来了${ Repository.getBannerInfo()}")
        val bannerInfo = Repository.getBannerInfo()
        bannerInfo
    }

    fun getBannerInfo(): BannerResponse? {
      //  LogUtils.d("Repository.getUserInfo().value?.getOrNull()${Repository.getBannerInfo()}")
        bannerInfo.value=Repository.getBannerInfo().value?.getOrNull()
        return bannerInfo.value
    }

    val collectStaticLiveData= Transformations.switchMap(addStaticCollect){
            it->
        //   LogUtils.d("Transformations经来了${ Repository.getBannerInfo()}")
        val staticCollect = Repository.addCollectStaticInfo(it)
        staticCollect
    }

    fun getStaticCollect(id:Int){
        addStaticCollect.value=id
    }

    val collectOutSideLiveData=Transformations.switchMap(addOutSideCollect){
        it->
        val staticCollect = Repository.addCollectOutSideInfo(it.title,it.author,it.link)
        staticCollect
    }

    fun getOutSideCollect( data: com.example.service.model.HomeResponse.Data.DataX){
        addOutSideCollect.value=data
    }

    val getHomeListLiveData=Transformations.switchMap(getHomeList){
        it->
        LogUtils.d(it)
        val getHomeList=Repository.getHomeListInfo(it)
        LogUtils.d(getHomeList)
        getHomeList
    }

    fun getHomeListInfo(page:Int){
        getHomeList.value=page
    }

}