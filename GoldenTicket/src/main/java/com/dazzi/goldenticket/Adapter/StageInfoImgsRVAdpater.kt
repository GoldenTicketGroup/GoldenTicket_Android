package com.dazzi.goldenticket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dazzi.goldenticket.Data.StageInfoImgsData
import com.dazzi.goldenticket.R

class StageInfoImgsRVAdpater(val ctx: Context, var dataList: ArrayList<StageInfoImgsData>): RecyclerView.Adapter<StageInfoImgsRVAdpater.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_stageinfo_imgs, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(ctx)
            .load(dataList[position].image_url)
            .apply(RequestOptions.centerCropTransform())
            .into(holder.info_img_url)
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var info_img_url = itemView.findViewById(R.id.iv_stageinfo_info_img) as ImageView
    }
}