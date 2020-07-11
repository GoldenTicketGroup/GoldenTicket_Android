package com.dazzi.goldenticket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.Activity.StageInfoActivity
import com.dazzi.goldenticket.Data.ContentDetailData
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity



class CardDetailRVAdapter(val ctx: Context, val dataList: ArrayList<ContentDetailData>): RecyclerView.Adapter<CardDetailRVAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(com.dazzi.goldenticket.R.layout.rv_month_contents_item,p0,false)
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

        dataList[position].content = dataList[position].content.replace("/r","\n")
        dataList[position].content = dataList[position].content.replace(" ","\u00A0")
        holder.contentContent.text = dataList[position].content

        holder.contentGoShow.onClick {
            ctx.startActivity<StageInfoActivity>("idx" to dataList[position].show_idx)
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var contentSubtitle  = itemView.findViewById(com.dazzi.goldenticket.R.id.tvContentItemSubTitle) as TextView
        var contentTitle  = itemView.findViewById(com.dazzi.goldenticket.R.id.tvContentItemTitle) as TextView
        var contentImage = itemView.findViewById(com.dazzi.goldenticket.R.id.ivContentItemImage) as ImageView
        var contentContent  = itemView.findViewById(com.dazzi.goldenticket.R.id.tvContentItemContent) as TextView
        var contentGoShow = itemView.findViewById(com.dazzi.goldenticket.R.id.btnGoShowInfo) as Button

    }
}