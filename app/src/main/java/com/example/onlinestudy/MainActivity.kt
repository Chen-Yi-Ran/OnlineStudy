package com.example.onlinestudy

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.common.base.BaseActivity

import com.example.onlinestudy.databinding.ActivityMainBinding

class MainActivity:BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes()=R.layout.activity_main

    override fun initData() {
        super.initData()
    }

    override fun initView() {
        super.initView()
        //进行设置BottomNavigationView可以联动切换Fragment
        val navController = findNavController(R.id.fcv_main)
        mBinding.bnvMain.setupWithNavController(navController)

    }

    override fun initConfig() {
        super.initConfig()
    }
}