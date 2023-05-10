package com.example.study.adapter.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.blankj.utilcode.util.LogUtils
import com.example.service.model.CategoryProject
import com.example.study.ui.StudyPageFragment

class StudyPagerAdapter :FragmentPagerAdapter{

    private val categoriesList=ArrayList<CategoryProject.Data>()

    constructor(fm:FragmentManager):super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    }

    override fun getCount(): Int {
     return   categoriesList.size;
    }

    override fun getItem(position: Int): Fragment {
      val data=  categoriesList[position]
        val studyPageFragment = StudyPageFragment.newInstance(data)
        return studyPageFragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return categoriesList[position].name
    }

    fun setCategories( categories:CategoryProject){
       categoriesList.clear()
       val data=categories.data
        categoriesList.addAll(data)
        notifyDataSetChanged()
        LogUtils.d("进来了")
    }
}