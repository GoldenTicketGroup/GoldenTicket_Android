package com.example.goldenticket.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.goldenticket.Data.StageInfoActorsData
import com.example.goldenticket.R

class StageInfoActorsRVAdapter(val ctx: Context, var dataList: ArrayList<StageInfoActorsData>): RecyclerView.Adapter<StageInfoActorsRVAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_stageinfo_actors, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //로딩 느려질 시, var options: RequestOptions = RequestOptions().placeholder(R.drawable.placeholder), .apply(options), 이미지 바꾸기
        Log.e("InfoActorsRVAdptr::: ", "onBindViewHolder::actors:: " + dataList[position].image_url)

        /*var data: ByteArray? = null
        try {
            data = dataList[position].image_url.toByteArray()
        } catch (e1: UnsupportedEncodingException) {
            e1.printStackTrace()
        }
        val base64 = Base64.encodeToString(data, Base64.DEFAULT)
        Log.e("InfoActorsRVAdptr::: ", "onBindViewHolder::actors::base64:: " + base64.toString())*/

        Glide.with(ctx)
            .load(dataList[position].image_url)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.actor_img_url)
        holder.actor_name.text = dataList[position].name
        holder.actor_role.text = dataList[position].role

    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var actor_img_url = itemView.findViewById(R.id.iv_stageinfo_actor_img) as ImageView
        var actor_name = itemView.findViewById(R.id.tv_stageinfo_actor_name) as TextView
        var actor_role = itemView.findViewById(R.id.tv_stageinfo_actor_role) as TextView

    }
}