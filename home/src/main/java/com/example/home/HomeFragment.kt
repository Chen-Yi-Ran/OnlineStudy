package com.example.home

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.common.base.BaseFragment
import com.example.common.utils.MySizeUtils
import com.example.home.adapter.HomeAdapter
import com.example.home.databinding.FragmentHomeBinding
import com.example.service.model.BannerResponse
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator


class HomeFragment : BaseFragment() , HomeAdapter.onLikeItemClickListener {
    private val viewModel: HomeViewModel by viewModels { defaultViewModelProviderFactory }

    override fun getLayoutRes() = R.layout.fragment_home

    private val homeAdapter=HomeAdapter()
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {

        return FragmentHomeBinding.bind(view).apply {
            vm = viewModel

            viewModel.getBannerInfo()
            viewModel.getHomeListInfo(0)

            //监听个人信息页面数据
            viewModel.bannerLiveData.observe(this@HomeFragment, { result ->
                val data = result.getOrNull()
//                LogUtils.d(data)
                if (data != null) {
                  //  LogUtils.d("轮播图${data}")
                    banner.setAdapter(object : BannerImageAdapter<BannerResponse.Data?>(data.data) {
                        override fun onBindView(
                            holder: BannerImageHolder?,
                            data: BannerResponse.Data?,
                            position: Int,
                            size: Int
                        ) {
                            //图片加载自己实现
                            if (holder != null) {
                                Glide.with(holder.imageView)
                                    .load(data?.imagePath)
                                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                                    .into(holder.imageView)
                            }
                        }
                    })

                } else {
                    result.exceptionOrNull()?.printStackTrace()
                }
            })
            //添加生命周期观察者
            banner.addBannerLifecycleObserver(this@HomeFragment);
            //设置指示器
            banner.indicator = CircleIndicator(context)

            recyclerHome.layoutManager=LinearLayoutManager(context)
            recyclerHome.adapter=homeAdapter
            homeAdapter.setOnLikeItemClickListener(this@HomeFragment)
            recyclerHome.addItemDecoration(object :RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.top =MySizeUtils.dip2px(context!!,2.5f)
                    outRect.bottom = MySizeUtils.dip2px(context!!,2.5f)
                    outRect.left =MySizeUtils.dip2px(context!!,2.5f)
                    outRect.right =MySizeUtils.dip2px(context!!,2.5f)
                }
            })

        }
    }

    override fun initData() {

        viewModel.collectStaticLiveData.observe(this,{
                result->
            val data=result.getOrNull()
            if(data!=null){
                LogUtils.d(data)
                Toast.makeText(context,"收藏成功", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,"无法成功获取账号信息", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.collectOutSideLiveData.observe(this,{
                result->
            val data=result.getOrNull()
            if(data!=null){
                LogUtils.d(data)
                Toast.makeText(context,"收藏成功", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,"无法成功获取账号信息", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.getHomeListLiveData.observe(this,{
                result->
            val data=result.getOrNull()
            LogUtils.d(data)
            if(data!=null){
                LogUtils.d(data)
                homeAdapter.setHomeDataList(data.data)
            }else{
                Toast.makeText(context,"无法成功获取首页列表信息", Toast.LENGTH_LONG).show()
            }
        })


    }

    override fun onLikeItemClick(
        data: com.example.service.model.HomeResponse.Data.DataX
    ) {

        if(data.id==0){
            viewModel.getOutSideCollect(data)

        }else{
            viewModel.getStaticCollect(data.id)
        }
        }

}