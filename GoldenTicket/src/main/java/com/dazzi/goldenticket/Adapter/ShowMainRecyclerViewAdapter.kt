package com.dazzi.goldenticket.Adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.Data.ShowData
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.Activity.StageInfoActivity
import org.jetbrains.anko.startActivity

class ShowMainRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<ShowData>) :
    RecyclerView.Adapter<ShowMainRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.cv_poster_main, viewGroup, false)
        return Holder(view)

    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        Glide.with(ctx)
            .load(dataList[position].image_url)
            .into(holder.poster)
        holder.show_name.text = dataList[position].name
        holder.location.text = dataList[position].location
        holder.time.text = dataList[position].running_time

        holder.container.setOnClickListener {

            ctx.startActivity<StageInfoActivity>(
                "idx" to dataList[position].show_idx
            )
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var container = itemView.findViewById(R.id.cv_main_poster) as CardView
        var poster = itemView.findViewById(R.id.iv_main_poster) as ImageView
        var show_name = itemView.findViewById(R.id.tv_rv_item_name) as TextView
        var location = itemView.findViewById(R.id.tv_rv_item_location) as TextView
        var time = itemView.findViewById(R.id.tv_rv_item_time) as TextView

    }

}