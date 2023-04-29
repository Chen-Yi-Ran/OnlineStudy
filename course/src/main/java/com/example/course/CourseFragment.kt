package com.example.course

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.common.base.BaseFragment
import com.example.course.databinding.FragmentCourseBinding

//课程中心的Fragment
class CourseFragment:BaseFragment() {//通过在构造函数中传入布局文件

//private var mBinding:FragmentCourseBinding?=null
    override fun getLayoutRes(): Int {
         return  R.layout.fragment_course
    }

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentCourseBinding.bind(view)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        mBinding= FragmentCourseBinding.bind(view)
//        mBinding?.apply {
//            lifecycleOwner=viewLifecycleOwner
//        }
//
//    }

//    override fun onDestroy() {
//        super.onDestroy()
//        mBinding?.unbind()
//    }


}