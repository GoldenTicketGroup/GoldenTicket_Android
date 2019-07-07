package com.example.goldenticket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goldenticket.Activity.StageInfoActivity
import com.example.goldenticket.Data.KeepStageData
import com.example.goldenticket.R
import org.jetbrains.anko.startActivity

class KeepStageRVAdapter(val ctx: Context, val dataList: ArrayList<KeepStageData>): RecyclerView.Adapter<KeepStageRVAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_keep_item,viewGroup,false)
        view.setOnClickListener {
            //해당 포지션의 show_idx를 stageinfo의 path variable로 전달
            view.context.startActivity<StageInfoActivity>("idx" to 20) //TODO: idx 서버에서 받아서 수정
        }
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