package com.example.mine.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.blankj.utilcode.util.LogUtils
import com.example.common.base.BaseViewModel
import com.example.common.model.SingleLiveData
import com.example.service.model.CollectListResponse
import com.example.service.model.SubListResponse
import com.example.service.repo.Repository

class CollectViewModel: BaseViewModel()  {
    val collectListInfo= MutableLiveData<Int>()
    val collectListInfoLiveData = Transformations.switchMap(collectListInfo){
          page  ->
           LogUtils.d("经来了执行collectListInfoLiveData")
       Repository.getCollectListInfo(page)
    }

    fun getCollectListInfo(page:Int) {

        collectListInfo.value=page
          LogUtils.d("  collectListInfo.value=page赋值成功")
    }
}