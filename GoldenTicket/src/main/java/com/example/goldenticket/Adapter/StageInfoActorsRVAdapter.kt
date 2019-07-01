package com.example.goldenticket.Adapter

import android.content.Context
import android.system.Os.bind
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goldenticket.Data.StageInfoActorsData
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.rv_item_stageinfo_actors.view.*

class StageInfoActorsRVAdapter(val ctx: Context, val dataList: ArrayList<StageInfoActorsData>): RecyclerView.Adapter<StageInfoActorsRVAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_stageinfo_actors, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(ctx)
            .load(dataList[position].actor_img_url)
            .into(holder.actor_img_url)
        holder.actor_name.text = dataList[position].actor_name
        holder.actor_role.text = dataList[position].actor_role

    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var actor_img_url = itemView.findViewById(R.id.iv_stageinfo_actor_img) as ImageView
        var actor_name = itemView.findViewById(R.id.tv_stageinfo_actor_name) as TextView
        var actor_role = itemView.findViewById(R.id.tv_stageinfo_actor_role) as TextView

    }
}