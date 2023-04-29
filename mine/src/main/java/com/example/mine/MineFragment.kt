package com.example.mine

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.common.base.BaseFragment
import com.example.mine.databinding.FragmentMineBinding

class MineFragment:BaseFragment() {
    override fun getLayoutRes()=R.layout.fragment_mine

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentMineBinding.bind(view)
    }
}