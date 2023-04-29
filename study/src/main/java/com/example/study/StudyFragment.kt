package com.example.study

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.common.base.BaseFragment
import com.example.study.databinding.FragmentStudyBinding

class StudyFragment:BaseFragment() {
    override fun getLayoutRes()=R.layout.fragment_study

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentStudyBinding.bind(view)
    }
}