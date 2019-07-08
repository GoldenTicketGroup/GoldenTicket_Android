package com.example.goldenticket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goldenticket.Data.CardDetailData
import com.example.goldenticket.Data.ContentDetailData
import com.example.goldenticket.R

class CardDetailRVAdapter(val ctx: Context, val dataList: ArrayList<ContentDetailData>): RecyclerView.Adapter<CardDetailRVAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_month_contents_item,p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.contentSubtitle.text = dataList[position].subtitle
        holder.contentTitle.text = dataList[position].title
        Glide.with(ctx)
            .load(dataList[position].image_url)
            .into(holder.contentImage)
        holder.contentContent.text = dataList[position].content
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var contentSubtitle  = itemView.findViewById(R.id.tvContentItemSubTitle) as TextView
        var contentTitle  = itemView.findViewById(R.id.tvContentItemTitle) as TextView
        var contentImage = itemView.findViewById(R.id.ivContentItemImage) as ImageView
        var contentContent  = itemView.findViewById(R.id.tvContentItemContent) as TextView

    }
}