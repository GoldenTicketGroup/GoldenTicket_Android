package com.example.goldenticket.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goldenticket.Activity.ContentActivity
import com.example.goldenticket.Data.CardListData
import com.example.goldenticket.R
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

        holder.title.text = dataList[position].category
        holder.comment.text = dataList[position].title

        holder.cv.setOnClickListener {
            ctx.startActivity<ContentActivity>(
                "idx" to dataList[position].cardIdx
            )
        }

    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var cv = itemView.findViewById(R.id.cvCard) as CardView
        var title  = itemView.findViewById(R.id.tvTitle) as TextView
        var comment  = itemView.findViewById(R.id.tvComment) as TextView
        var img = itemView.findViewById(R.id.ivContents) as ImageView
    }
}