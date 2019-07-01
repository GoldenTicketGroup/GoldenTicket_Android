package com.example.goldenticket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goldenticket.Data.MonthContentsDetailData
import com.example.goldenticket.R

class MonthContentsRVAdapter(val ctx: Context, val dataList: ArrayList<MonthContentsDetailData>): RecyclerView.Adapter<MonthContentsRVAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_month_contents_item,p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.contentsTitle.text = dataList[position].subtitle
        holder.contentsShowName.text = dataList[position].showName
        Glide.with(ctx)
            .load(R.drawable.test) // TODO: 서버에서 받은 이미지로 변경해야함
            .into(holder.contentsShowImg)
        holder.contentsContent.text = dataList[position].showContent
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var contentsTitle  = itemView.findViewById(R.id.tvContentsItemTitle) as TextView
        var contentsShowName  = itemView.findViewById(R.id.tvContentsShowName) as TextView
        var contentsShowImg = itemView.findViewById(R.id.ivMonthContentsShowImage) as ImageView
        var contentsContent  = itemView.findViewById(R.id.tvMonthContentsContent) as TextView

    }
}