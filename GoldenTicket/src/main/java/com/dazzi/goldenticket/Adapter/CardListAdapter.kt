package com.dazzi.goldenticket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.Activity.ContentActivity
import com.dazzi.goldenticket.Data.CardListData
import com.dazzi.goldenticket.R
import kotlinx.android.synthetic.main.rv_main_contents_item.view.*
import org.jetbrains.anko.startActivity

class CardListAdapter(val ctx: Context, val dataList: ArrayList<CardListData>): RecyclerView.Adapter<CardListAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_main_contents_item,p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        Glide.with(ctx)
            .load(dataList[position].imageUrl)
            .into(holder.img)

        dataList[position].title = dataList[position].title.replace("/r","\n")
        holder.title.text = dataList[position].title
        holder.category.text = dataList[position].category

        holder.cv.setOnClickListener {
            ctx.startActivity<ContentActivity>(
                "idx" to dataList[position].cardIdx
            )
        }

    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var cv = itemView.cvCard
        var title  = itemView.findViewById(R.id.tvTitle) as TextView
        var category  = itemView.findViewById(R.id.tvCategory) as TextView
        var img = itemView.findViewById(R.id.ivCard) as ImageView
    }
}