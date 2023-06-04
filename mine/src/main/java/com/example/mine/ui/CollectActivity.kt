package com.example.mine.ui

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.example.common.ktx.context
import com.example.common.utils.MySizeUtils

import com.example.mine.R
import com.example.mine.adapter.CollectListAdapter


class CollectActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(CollectViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)
        //刚开始是第1页，后面还要加载更多
        viewModel.getCollectListInfo(0)
        val recyclerView=findViewById<RecyclerView>(R.id.recycler_collect)
        recyclerView.layoutManager= LinearLayoutManager(context)
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration(){
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

        val adapter= CollectListAdapter()
        viewModel.collectListInfoLiveData.observe(this, Observer {
            result->
            val collectList=result.getOrNull()
            LogUtils.d(this,collectList)
            if(collectList!=null){
                adapter.setCollectListData(collectList.data)
                recyclerView.adapter=adapter
            }else{
                LogUtils.d(this,"无法获得收藏文章列表信息")
            }
        })



    }


}