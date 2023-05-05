package com.example.mine.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.common.base.BaseFragment
import com.example.mine.R
import com.example.mine.databinding.FragmentUserInfoBinding

//用户信息界面Fragment
class UserInfoFragment :BaseFragment() {

    //args接收MineFragment传过来的值
    private val args by navArgs<UserInfoFragmentArgs>()

    override fun getLayoutRes()= R.layout.fragment_user_info

    override fun bindView(view: View, savedInstanceState: Bundle?)=
        FragmentUserInfoBinding.bind(view).apply {
            //toolbar返回
            toolbarUserInfo.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            toolbarUserInfo.navigationIcon?.setTint(Color.WHITE)
            //save返回
            btnSaveUserInfo.setOnClickListener {
                findNavController().navigateUp()
            }
            //进行赋值
            info=args.info

        }
}