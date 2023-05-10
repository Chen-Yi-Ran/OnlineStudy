package com.example.study.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankj.utilcode.util.LogUtils
import com.example.common.base.BaseViewModel
import com.example.service.model.CategoryProject
import com.example.service.model.ListProjectResponse
import com.example.study.adapter.adapter.StudyPagerAdapter
import com.example.study.net.GitHubService
import com.example.study.repo.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class StudyViewModel:BaseViewModel() {

    //获取项目列表数据
    fun getPagingData(id: Int): Flow<PagingData<ListProjectResponse.Data.DataX>> {
        LogUtils.d(Repository.getPagingData(id))
        return Repository.getPagingData(id).cachedIn(viewModelScope)
    }



    //获取项目分类数据
//     fun getCategory() :CategoryProject?{
//        val create = GitHubService.create()
//        val category = create.getCategory()
//        var data:CategoryProject?=null
//
//        LogUtils.d(data)
//     return data
//
//    }
     fun getCategory( studyPagerAdapter: StudyPagerAdapter) {

        GitHubService.create().getCategory().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            object : Subscriber<CategoryProject>() {
                override fun onCompleted() {

                }

                override fun onError(e: Throwable?) {

                }

                override fun onNext(category: CategoryProject?) {
                    LogUtils.d("http返回：", category.toString() + "");
                    launchUI {
                        LogUtils.d(category)
                        if (category != null) {
                            studyPagerAdapter.setCategories(category)
                        }
                    }
                }

            }
        )
    }
}




