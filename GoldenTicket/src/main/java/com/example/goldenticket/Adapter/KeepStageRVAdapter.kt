package com.example.goldenticket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goldenticket.Data.KeepStageData
import com.example.goldenticket.R

class KeepStageRVAdapter(val ctx: Context, val dataList: ArrayList<KeepStageData>): RecyclerView.Adapter<KeepStageRVAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_keep_item,viewGroup,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(ctx)
            .load(R.drawable.test)
            .into(holder.img_thumbnail)

    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img_thumbnail = itemView.findViewById(R.id.ivKeepStage) as ImageView
    }
}