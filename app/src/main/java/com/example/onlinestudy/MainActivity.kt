package com.example.onlinestudy

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.common.base.BaseActivity
import com.example.course.CourseFragment
import com.example.home.HomeFragment
import com.example.mine.MineFragment

import com.example.onlinestudy.databinding.ActivityMainBinding
import com.example.study.StudyFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Error

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes() = R.layout.activity_main

    override fun initData() {
        super.initData()
    }

    //将Fragment对象存储在map当中,map第一次初始化第二次会直接获取，减少复用
    private val fragments = mapOf<Int, Fragment>(
        INDEX_HOME to HomeFragment(),
        INDEX_COURSE to CourseFragment(),
        INDEX_STUDY to StudyFragment(),
        INDEX_MINE to MineFragment()
    )

    override fun initView() {
        super.initView()
        //进行设置BottomNavigationView可以联动切换Fragment
//        val navController = findNavController(R.id.fcv_main)
//        mBinding.bnvMain.setupWithNavController(navController)
        mBinding.apply {
            vp2Main.adapter=MainViewPagerAdapter(this@MainActivity,fragments)
            //viewPager2每个页面切换变动的时候，将位置同步到BottomNavigationView
            vp2Main.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bnvMain.selectedItemId=when(position){
                        INDEX_HOME->R.id.homeFragment
                        INDEX_COURSE->R.id.courseFragment
                        INDEX_STUDY->R.id.studyFragment
                        INDEX_MINE->R.id.mineFragment
                        else->error("viewPager2的fragments索引位置${position}越界")
                    }
                }
            })
            //BottomNavigationView的点击事件响应到viewpager
            bnvMain.setOnNavigationItemSelectedListener {
                    item->
                when(item.itemId){
                    R.id.homeFragment -> vp2Main.currentItem= INDEX_HOME
                    R.id.courseFragment -> vp2Main.currentItem= INDEX_COURSE
                    R.id.studyFragment -> vp2Main.currentItem= INDEX_STUDY
                    R.id.mineFragment -> vp2Main.currentItem= INDEX_MINE
                    else-> error("Bnv的item的Id${item.itemId}没有对应的viewpager2的元素")
                }
                true
            }
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

}

/**
 * 首页的viewpager2的适配器
 */
class MainViewPagerAdapter(fragmentActivity: FragmentActivity,
       private   val  fragments:Map<Int,Fragment>
):FragmentStateAdapter(fragmentActivity){
    override fun getItemCount()=fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]?: error("请确保Fragments数据源和viewpager2的index匹配设置")
    }

}