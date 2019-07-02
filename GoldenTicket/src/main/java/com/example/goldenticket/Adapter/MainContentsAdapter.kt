package com.example.goldenticket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenticket.Data.MainContentsData
import com.example.goldenticket.R

class MainContentsAdapter(val ctx: Context, val dataList: ArrayList<MainContentsData>): RecyclerView.Adapter<MainContentsAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_main_contents_item,p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        /*
        Glide.with(ctx)
            .load(dataList[position].author)
            .into(holder.img_thumbnail)
        */


        holder.title.text = dataList[position].title
        holder.comment.text = dataList[position].comment
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title  = itemView.findViewById(R.id.tvTitle) as TextView
        var comment  = itemView.findViewById(R.id.tvComment) as TextView
    }
}