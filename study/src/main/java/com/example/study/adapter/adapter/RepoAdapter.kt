package com.example.study.adapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.example.service.model.ListProjectResponse

import com.example.study.R

class RepoAdapter : PagingDataAdapter<ListProjectResponse.Data.DataX, RepoAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ListProjectResponse.Data.DataX>() {
            override fun areItemsTheSame(
                oldItem: ListProjectResponse.Data.DataX,
                newItem: ListProjectResponse.Data.DataX
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListProjectResponse.Data.DataX,
                newItem: ListProjectResponse.Data.DataX
            ): Boolean {
                return oldItem == newItem

            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_text)
        val shareUser: TextView = itemView.findViewById(R.id.description_text)
        val starCount: TextView = itemView.findViewById(R.id.niceShareDate)
        val icon:ImageView=itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
      //  LogUtils.d("这是repo${repo}")
        if (repo != null) {
            holder.name.text =repo.title
            holder.shareUser.text = repo.desc
            holder.starCount.text = repo.niceShareDate.toString()
           Glide.with(holder.icon).load(repo.envelopePic).into(holder.icon)
        }
        holder.itemView.setOnClickListener {
            if(mItemCallbackListener!=null&&repo!=null){
                mItemCallbackListener.toWebViewActivity(repo.link,repo.id,repo.title)
            }
        }

    }

    lateinit var mItemCallbackListener:ItemCallback
    fun setOnItemClickListener(listener:ItemCallback){
        mItemCallbackListener=listener
    }

interface ItemCallback{
   fun toWebViewActivity(url:String,id:Int,title:String)
}



}

