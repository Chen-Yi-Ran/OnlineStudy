package com.example.mine.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mine.R
import com.example.service.model.CollectListResponse
import com.example.service.model.SubListResponse

class CollectListAdapter : RecyclerView.Adapter<CollectListAdapter.InnerHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect, parent, false);
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val data = CollectList[position]
        holder.name.text = data.chapterName
        holder.date.text=data.niceDate
        holder.title.text=data.title


    }

    private lateinit var likeItemClick:onLikeItemClickListener

    interface onLikeItemClickListener{

        fun onLikeItemClick(data: SubListResponse.Data)

    }

    fun setOnLikeItemClickListener(listener:onLikeItemClickListener){
        this.likeItemClick=listener
    }

    override fun getItemCount(): Int {
        return CollectList.size

    }

    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.colect_chapterName)
        val date: TextView =itemView.findViewById(R.id.collect_date)
        val title: TextView =itemView.findViewById(R.id.collect_title)
    }

    val CollectList = ArrayList<CollectListResponse.Data.DataX>()

    fun setCollectListData(collectListData: CollectListResponse.Data) {
        CollectList.clear()
        val data = collectListData
        CollectList.addAll(data.datas)
        notifyDataSetChanged()
    }
}