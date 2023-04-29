package com.example.home

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.common.base.BaseFragment
import com.example.home.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment(){


    override fun getLayoutRes()=R.layout.fragment_home

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {

        return FragmentHomeBinding.bind(view)
    }


}