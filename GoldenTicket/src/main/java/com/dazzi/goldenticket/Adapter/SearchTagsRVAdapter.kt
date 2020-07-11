package com.dazzi.goldenticket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.dazzi.goldenticket.R


class SearchTagsRVAdapter(val ctx: Context, val dataList: ArrayList<String>): RecyclerView.Adapter<SearchTagsRVAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_search_tags, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tv_search_tag.text = dataList[position]
        holder.tv_search_tag.marginBottom
        holder.tv_search_tag.setOnClickListener {
            var keyword = holder.tv_search_tag.text
            //keyword로 검색, 결과값 SearchResultActivity로 전달
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tv_search_tag = itemView.findViewById(R.id.tv_search_tag) as TextView
    }
}
