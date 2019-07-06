package com.example.goldenticket.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goldenticket.Data.ContentDetailData
import com.example.goldenticket.R

class ContentDetailRVAdapter(val ctx: Context, val dataList: ArrayList<ContentDetailData>): RecyclerView.Adapter<ContentDetailRVAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_month_contents_item,p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        var content = ""

        holder.contentsTitle.text = dataList[position].subtitle
        holder.contentsShowName.text = dataList[position].title
        Glide.with(ctx)
            .load(dataList[position].image_url)
            .into(holder.contentsShowImg)

        for (i in 0..dataList[position].content.size-1){
            content += dataList[position].content[i]
        }

        holder.contentsContent.text = content
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var contentsTitle  = itemView.findViewById(R.id.tvContentItemTitle) as TextView
        var contentsShowName  = itemView.findViewById(R.id.tvContentShowName) as TextView
        var contentsShowImg = itemView.findViewById(R.id.ivContentShowImage) as ImageView
        var contentsContent  = itemView.findViewById(R.id.tvContentContent) as TextView

    }
}