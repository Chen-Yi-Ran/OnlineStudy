package com.example.home.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.service.model.BannerResponse
import com.youth.banner.adapter.BannerAdapter

class ImageAdapter(mDatas:List<BannerResponse.Data>):BannerAdapter<BannerResponse.Data,ImageAdapter.BannerViewHolder>(mDatas) {


    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageAdapter.BannerViewHolder {
       var imageView= ImageView(parent?.context)
        imageView.layoutParams=ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.scaleType=ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: ImageAdapter.BannerViewHolder?,
        data: BannerResponse.Data?,
        position: Int,
        size: Int
    ) {
        if (holder != null) {
            Glide.with(holder.imageView)
                .load(data?.imagePath)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(holder.imageView)
        }
    }

    class BannerViewHolder : RecyclerView.ViewHolder {

        constructor(view: ImageView):super(view){
            imageView=view
        }

        lateinit var imageView:ImageView




    }


}