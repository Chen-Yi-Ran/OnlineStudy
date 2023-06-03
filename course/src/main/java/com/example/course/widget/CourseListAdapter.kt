package com.example.course.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.course.R
import com.example.service.model.CourseResponse
import com.example.service.model.SubListResponse

class CourseListAdapter : RecyclerView.Adapter<CourseListAdapter.InnerHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.courselist_item, parent, false);
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val data = mCourseList[position]
        holder.name.text = data.name
        holder.author.text = data.author
        holder.desc.text = data.desc
        Glide.with(holder.icon).load(data.cover).into(holder.icon)

    }

    private lateinit var likeItemClick:onLikeItemClickListener

    interface onLikeItemClickListener{

        fun onLikeItemClick(data: SubListResponse.Data)

    }

    fun setOnLikeItemClickListener(listener:onLikeItemClickListener){
        this.likeItemClick=listener
    }

    override fun getItemCount(): Int {
        return mCourseList.size

    }

    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val desc:TextView=itemView.findViewById(R.id.desc)
        val author:TextView=itemView.findViewById(R.id.author)
        val icon:ImageView=itemView.findViewById(R.id.imageView)
    }

    val mCourseList = ArrayList<SubListResponse.Data>()

    fun setHomeDataList(subListResponse: SubListResponse) {
        mCourseList.clear()
        val data = subListResponse
        mCourseList.addAll(data.data)
        notifyDataSetChanged()
    }
}