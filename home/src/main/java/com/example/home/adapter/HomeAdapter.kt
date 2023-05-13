package com.example.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.home.R
import com.example.service.model.BannerResponse
import com.example.service.model.HomeResponse

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.InnerHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val data = mhomeDataList[position]
        holder.name.text = data.shareUser
        holder.title.text = data.title
        holder.shareDate.text = data.niceDate
        holder.type.text = data.superChapterName
       if(data.collect){
           holder.like.setImageResource(R.drawable.ic_action_like)
       }else{
           holder.like.setImageResource(R.drawable.ic_action_no_like)
       }
        holder.like.setOnClickListener {
            if(data.collect){
                holder.like.setImageResource(R.drawable.ic_action_no_like)
            }else{
                holder.like.setImageResource(R.drawable.ic_action_like)
            }
            if(likeItemClick!=null){
                likeItemClick.onLikeItemClick(data)
            }
        }

    }

    private lateinit var likeItemClick:onLikeItemClickListener

    interface onLikeItemClickListener{

        fun onLikeItemClick(data:HomeResponse.Data.DataX)

    }

    fun setOnLikeItemClickListener(listener:onLikeItemClickListener){
        this.likeItemClick=listener
    }

    override fun getItemCount(): Int {
        return mhomeDataList.size

    }

    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.homeItemAuthor)
        val shareDate: TextView = itemView.findViewById(R.id.homeItemDate)
        val title: TextView = itemView.findViewById(R.id.homeItemTitle)
        val icon: ImageView = itemView.findViewById(R.id.homeItemHead)
        val type: TextView = itemView.findViewById(R.id.homeItemType)
        val like: ImageView = itemView.findViewById(R.id.homeItemLike)
    }

    val mhomeDataList = ArrayList<HomeResponse.Data.DataX>()

    fun setHomeDataList(homeResponse: HomeResponse.Data) {
        mhomeDataList.clear()
        val data = homeResponse.datas
        mhomeDataList.addAll(data)
        notifyDataSetChanged()
    }
}