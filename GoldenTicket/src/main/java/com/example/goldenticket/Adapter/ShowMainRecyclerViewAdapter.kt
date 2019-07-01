package com.example.goldenticket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenticket.Data.ShowData
import com.example.goldenticket.R
import com.example.goldenticket.Activity.StageInfoActivity
import org.jetbrains.anko.startActivity

class ShowMainRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<ShowData>): RecyclerView.Adapter<ShowMainRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.cv_poster_main, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        /*Glide.with(ctx)
            .load(dataList[position].img_url)
            .into(holder.poster)*/
        holder.show_name.text = dataList[position].title
        holder.location.text = dataList[position].location
        holder.time.text = dataList[position].time

        holder.container.setOnClickListener {
            ctx.startActivity<StageInfoActivity>(
                "idx" to dataList[position].show_id,
                "title" to dataList[position].title,
                "location" to dataList[position].location,
                "time" to dataList[position].time)
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var container = itemView.findViewById(R.id.cv_main_poster) as CardView
        var poster = itemView.findViewById(R.id.iv_main_poster) as ImageView
        var show_name = itemView.findViewById(R.id.tv_rv_item_name) as TextView
        var location = itemView.findViewById(R.id.tv_rv_item_location) as TextView
        var time = itemView.findViewById(R.id.tv_rv_item_time) as TextView
    }
}