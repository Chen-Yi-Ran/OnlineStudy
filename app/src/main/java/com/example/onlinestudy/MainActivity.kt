package com.example.onlinestudy

import android.view.MenuItem
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.common.base.BaseActivity
import com.example.common.widget.BnvVp2Mediator
import com.example.course.CourseFragment
import com.example.home.HomeFragment
import com.example.mine.MineFragment

import com.example.onlinestudy.databinding.ActivityMainBinding
import com.example.study.StudyFragment

import java.lang.Error

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes() = R.layout.activity_main

    override fun initData() {
        super.initData()
    }

    //将Fragment对象存储在map当中,map第一次初始化第二次会直接获取，解决复用问题
    private val fragments = mapOf<Int, ReFragment>(
        INDEX_HOME to { HomeFragment() },//每次返回这个{ HomeFragment() }函数都是一个Fragment对象
        INDEX_COURSE to { CourseFragment() },
        INDEX_STUDY to { StudyFragment() },
        INDEX_MINE to { MineFragment() }
    )

    override fun initView() {
        super.initView()
        //进行设置BottomNavigationView可以联动切换Fragment
//        val navController = findNavController(R.id.fcv_main)
//        mBinding.bnvMain.setupWithNavController(navController)
        mBinding.apply {
            vp2Main.adapter = MainViewPagerAdapter(this@MainActivity, fragments)
            BnvVp2Mediator(bnvMain, vp2Main){
               btn,vp2->
                vp2.isUserInputEnabled=true//用户是否可以滑动

            }.attach()

        }

    }

    override fun initConfig() {
        super.initConfig()
    }

    companion object {
        const val INDEX_HOME = 0//首页home对应的索引位置
        const val INDEX_COURSE = 1//课程fg对应的索引位置
        const val INDEX_STUDY = 2//学习中心的Index
        const val INDEX_MINE = 3//我的index
    }

    /**
     * 首页的viewpager2的适配器
     */
    class MainViewPagerAdapter(
        fragmentActivity: FragmentActivity,
        private val fragments: Map<Int, ReFragment>
    ) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int): Fragment {
            //因为fragments[position]是get这个ReFragment函数，需要invoke去执返回Fragment对象
            return fragments[position] ?.invoke()?:throw IndexOutOfBoundsException("ViewPager接收参数index越界了")
        }

    }

}
//类型别名定义
typealias ReFragment=() -> Fragment