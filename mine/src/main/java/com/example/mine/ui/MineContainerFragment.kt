package com.example.mine.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.common.base.BaseFragment
import com.example.mine.R
import com.example.mine.databinding.FragmentContainerMineBinding

class MineContainerFragment:BaseFragment() {

    override fun getLayoutRes()= R.layout.fragment_container_mine

    override fun bindView(view: View, savedInstanceState: Bundle?)=
        FragmentContainerMineBinding.bind(view)

}