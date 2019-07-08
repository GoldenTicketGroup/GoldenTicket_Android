package com.example.goldenticket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goldenticket.Data.MyLotteryData
import com.example.goldenticket.R

class MyLotteryRVAdapter(val ctx: Context, val dataList: ArrayList<MyLotteryData>): RecyclerView.Adapter<MyLotteryRVAdapter.Holder>() {

    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_mylottery, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(ctx)
            .load(dataList[position].image_url)
            .into(holder.stageinfo_poster_url)
        holder.stageinfo_date.text = dataList[position].date
        holder.stageinfo_title.text = dataList[position].name
        holder.stageinfo_detail.text = dataList[position].seatName
        holder.stageinfo_location.text = dataList[position].location
        holder.stageinfo_time.text = dataList[position].running_time

        if(itemClick != null)
        {
            holder?.itemView?.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        var stageinfo_poster_url = itemView.findViewById(R.id.iv_lotteryconfirm_poster) as ImageView
        var stageinfo_date = itemView.findViewById(R.id.tv_lotteryconfirm_date) as TextView
        var stageinfo_title = itemView.findViewById(R.id.tv_lotteryconfirm_title) as TextView
        var stageinfo_detail = itemView.findViewById(R.id.tv_lotteryconfirm_detail) as TextView
        var stageinfo_location = itemView.findViewById(R.id.tv_lotteryconfirm_location) as TextView
        var stageinfo_time = itemView.findViewById(R.id.tv_lotteryconfirm_time) as TextView
    }
}