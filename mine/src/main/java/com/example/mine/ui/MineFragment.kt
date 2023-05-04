package com.example.mine.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils

import com.example.common.base.BaseFragment
import com.example.mine.R
import com.example.mine.databinding.FragmentMineBinding
import com.example.service.repo.OnlineStudyDbHelper

class MineFragment:BaseFragment() {


    private  val viewModel: MineViewModel by viewModels { defaultViewModelProviderFactory }

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

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("执行销毁MineFragment了")
    }

    override fun initData() {
        super.initData()
        LogUtils.d("执行到了")
//
        OnlineStudyDbHelper.getLiveUserInfo(requireContext()).observerKt{

                it->
            LogUtils.d("it${it}")
                  viewModel.liveUser.value=it

        }

    }

}