package com.example.mine.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils

import com.example.common.base.BaseFragment
import com.example.mine.R
import com.example.mine.databinding.FragmentMineBinding

import com.example.service.Net.NetWorkService
import com.example.service.model.UserInfoResponse
import com.example.service.network.ServiceCreator
import com.example.service.repo.OnlineStudyDbHelper
import com.example.service.repo.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MineFragment:BaseFragment() {


    private  val viewModel: MineViewModel by viewModels { defaultViewModelProviderFactory }
    private  var info:UserInfoResponse?=null
    override fun getLayoutRes()= R.layout.fragment_mine

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentMineBinding.bind(view).apply {
            vm=viewModel

            //UI操作,登出
            btnLogoutMine.setOnClickListener {
                LogUtils.d("点击登录")
                //删除数据库中的信息并跳转到登录界面
                OnlineStudyDbHelper.deleteUserInfo(requireContext())
                ARouter.getInstance().build("/login/login").navigation()
            }
//

                //跳转userInfoFragment
                ivUserIconMine.setOnClickListener {
               //     LogUtils.d("info${info}viewModel.liveUser.value${viewModel.liveUser.value}")
                   if(info!=null&&viewModel.liveUser.value!=null){
                       LogUtils.d("执行了ivUserIconMine点击事件")
//                val info=viewModel.liveUserInfo.value
                       //         LogUtils.d("info${info}")
                       info?.let {
                           val action=MineFragmentDirections
                               .actionMineFragmentToUserInfoFragment(info!!)
                           findNavController().navigate(action)
                       }
                   }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("执行销毁MineFragment了")
    }

    override fun initData() {
        super.initData()
        //TODO：需要对登录状态进行判断,不然的话你没有登录，但是之前登录的时候到时数据库查询得到数据就会将查询到的数据赋给viewModel.liveUser.value
        //从而监听数据
        LogUtils.d("执行到了")
        val data=viewModel.getUserInfo()
        LogUtils.d(data)
//用户登录：当数据库查询数据发生变化的时候(在登录的时候就插入数据)观察数据
        OnlineStudyDbHelper.getLiveUserInfo(requireContext()).observerKt{
                it->
            LogUtils.d("it${it}")
                  viewModel.liveUser.value=it

        }

        //监听个人信息页面数据
        viewModel.loginLiveData.observe(this,{
                result->
            val data=result.getOrNull()
            LogUtils.d(data)
            if(data!=null){
                LogUtils.d(data)
                info=data
            }else{
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

//    fun getUserInfo(){
//        val getUserInfoService= ServiceCreator.create(NetWorkService::class.java)
//        getUserInfoService.getUserInfo().enqueue(object : Callback<UserInfoResponse> {
//            override fun onResponse(
//                call: Call<UserInfoResponse>,
//                response: Response<UserInfoResponse>
//            ) {
//                val body=response.body()
//                LogUtils.d(body)
//                viewModel.liveUserInfo.value=body
//            }
//
//            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
//                LogUtils.d(t.message)
//            }
//
//        })
    }



