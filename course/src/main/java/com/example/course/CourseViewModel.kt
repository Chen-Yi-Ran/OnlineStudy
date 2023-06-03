package com.example.course

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView
import com.aliyun.player.source.UrlSource
import com.example.common.model.SingleLiveData
import com.example.service.model.BannerResponse
import com.example.service.model.SubListResponse
import com.example.service.repo.Repository


class CourseViewModel : ViewModel() {


      val courseInfo= SingleLiveData<SubListResponse>()
    val courseInfoLiveData = Transformations.switchMap(courseInfo){
        _->
        //   LogUtils.d("Transformations经来了${ Repository.getBannerInfo()}")
        val courseInfo = Repository.getCourseListInfo()
        courseInfo
    }
    fun getCourseInfo(): SubListResponse? {
        //  LogUtils.d("Repository.getUserInfo().value?.getOrNull()${Repository.getBannerInfo()}")
        courseInfo.value=Repository.getCourseListInfo().value?.getOrNull()
        return courseInfo.value
    }
    fun playerVideo(mAliyunVodPlayerView: AliyunVodPlayerView) {
        val urlSource = UrlSource()
        //视频地址
        //视频地址
        urlSource.uri = "http://player.alicdn.com/video/aliyunmedia.mp4"
        mAliyunVodPlayerView.setLocalSource(urlSource)
    }


}