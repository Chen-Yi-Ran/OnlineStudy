package com.example.course

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aliyun.vodplayerview.activity.AliyunPlayerSettingActivity
import com.blankj.utilcode.util.LogUtils
import com.example.common.base.BaseFragment
import com.example.common.utils.MySizeUtils
import com.example.course.databinding.FragmentCourseBinding
import com.example.course.widget.CourseListAdapter

//课程中心的Fragment
class CourseFragment:BaseFragment() {//通过在构造函数中传入布局文件

//private var mBinding:FragmentCourseBinding?=null
    override fun getLayoutRes(): Int {
         return  R.layout.fragment_course
    }
    private val viewModel: CourseViewModel by viewModels { defaultViewModelProviderFactory }
    private val courseListAdapter=CourseListAdapter()
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentCourseBinding.bind(view).apply {

            vm = viewModel
//            tvTitle.setOnClickListener {
//                var intent= Intent(context,PlayerActivity::class.java)
//                startActivity(intent)
//            }
            viewModel.getCourseInfo()
            viewModel.courseInfoLiveData.observe(this@CourseFragment, {

                    result ->
                val data = result.getOrNull()
                if(data!=null){
                    LogUtils.d(data)
                    courseListAdapter.setHomeDataList(data)
                    Toast.makeText(context,"获取教程信息有关", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"无法成功获取教程信息", Toast.LENGTH_LONG).show()
                }
            })

            recycler.adapter= courseListAdapter
            recycler.layoutManager= LinearLayoutManager(context)
            recycler.addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.top = MySizeUtils.dip2px(context!!,2.5f)
                    outRect.bottom = MySizeUtils.dip2px(context!!,2.5f)
                    outRect.left = MySizeUtils.dip2px(context!!,2.5f)
                    outRect.right = MySizeUtils.dip2px(context!!,2.5f)
                }
            })
        }
    }



}