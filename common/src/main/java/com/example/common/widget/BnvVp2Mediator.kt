package com.example.common.widget

import android.view.MenuItem
import androidx.core.view.forEachIndexed
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class BnvVp2Mediator(private val bnv: BottomNavigationView,
                     private val vp2: ViewPager2,
private val config:((BottomNavigationView,ViewPager2)->Unit)?=null) {
    //构造函数里面的参数如果不加private val只能在初始化的时候能够使用，加上了的话能够全局使用
    //config配置函数，默认为null，需要配置的时候加上即可
   private val map = mutableMapOf<MenuItem, Int>()
    init {


        //通过循环遍历构建map,每个item和位置构成map映射

        bnv.menu.forEachIndexed { index, item ->
            map[item] = index
        }



    }

    fun attach(){
        //执行高阶函数config
        config?.invoke(bnv,vp2)

        //viewPager2每个页面切换变动的时候，将位置同步到BottomNavigationView
        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bnv.selectedItemId = bnv.menu.getItem(position).itemId
            }
        })
        //BottomNavigationView的点击事件响应到viewpager
        bnv.setOnNavigationItemSelectedListener { item ->
            vp2.currentItem = map[item] ?: error("Bnv的item的Id${item.itemId}没有对应的viewPager2的元素")
            true
        }
    }

}

