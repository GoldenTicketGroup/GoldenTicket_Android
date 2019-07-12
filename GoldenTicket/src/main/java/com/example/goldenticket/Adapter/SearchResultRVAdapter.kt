package com.example.goldenticket.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goldenticket.Activity.StageInfoActivity
import com.example.goldenticket.DB.SharedPreferenceController
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.Network.Delete.DeleteShowLikeResponse
import com.example.goldenticket.Network.Post.PostShowLikeResponse
import com.example.goldenticket.Network.Post.SearchData
import com.example.goldenticket.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import org.jetbrains.anko.startActivity

class SearchResultRVAdapter(val ctx: Context, val dataList: ArrayList<SearchData>): RecyclerView.Adapter<SearchResultRVAdapter.Holder>() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_keep_item,viewGroup,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(ctx)
            .load(dataList[position].image_url)
            .into(holder.search_result_img_url)

        Log.e("SearchResultRVAdtr::", "onBindViewHolder::is_liked" + dataList[position].is_liked)

        when (dataList[position].is_liked) {
            0 -> {
                holder.search_result_like.isSelected = false
                holder.search_result_like.setBackgroundResource(R.drawable.icon_like_nofill)
            }
            1 -> {
                holder.search_result_like.isSelected = true
                holder.search_result_like.setBackgroundResource(R.drawable.icon_like_fill)
            }
        }

        holder.itemView.setOnClickListener {
            //해당 포지션의 show_idx를 stageinfo의 path variable로 전달
            Log.e("SearchResultRVAdtr::", "onCreateViewHolder::setOnClickListener::" + dataList[position].show_idx)
            ctx.startActivity<StageInfoActivity>("idx" to dataList[position].show_idx)
        }

        holder.search_result_like.setOnClickListener {

            /** 좋아요 취소 **/
            if(holder.search_result_like.isSelected){
                val token = SharedPreferenceController.getUserToken(ctx)

                var jsonObject = JSONObject()
                jsonObject.put("show_idx", dataList[position].show_idx)
                val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

                val deleteShowLike = networkService.deleteShowLike("application/json", token, gsonObject)
                deleteShowLike.enqueue(object: Callback<DeleteShowLikeResponse> {
                    override fun onFailure(call: Call<DeleteShowLikeResponse>, t: Throwable) {
                        Log.e("Delete ShowLike Failed:",t.toString())
                    }

                    override fun onResponse(
                        call: Call<DeleteShowLikeResponse>,
                        response: Response<DeleteShowLikeResponse>
                    ) {
                        if(response.isSuccessful){
                            Log.e("SearcyResultRVAdtr::", "like_click_listener::취소::onResponse" + response.body()!!.message)
                            if(response.body()!!.status == 200){
                                // 통신 완료된 후 no fill like로 변경
                                holder.search_result_like.isSelected = !holder.search_result_like.isSelected
                                holder.search_result_like.setBackgroundResource(R.drawable.icon_like_nofill)
                            }
                        }
                    }

                })
            }
            /** 좋아요 **/
            else{
                val token = SharedPreferenceController.getUserToken(ctx)

                var jsonObject = JSONObject()
                jsonObject.put("show_idx", dataList[position].show_idx) // TODO: 인덱스 값 수정해야함
                val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

                val postShowLike = networkService.postShowLike("application/json", token, gsonObject)
                postShowLike.enqueue(object: Callback<PostShowLikeResponse> {
                    override fun onFailure(call: Call<PostShowLikeResponse>, t: Throwable) {
                        Log.e("INSERT ShowLike Failed:",t.toString())
                    }

                    override fun onResponse(
                        call: Call<PostShowLikeResponse>,
                        response: Response<PostShowLikeResponse>
                    ) {
                        if(response.isSuccessful){
                            Log.e("SearcyResultRVAdtr::", "like_click_listener::조하::onResponse" + response.body()!!.message)
                            if(response.body()!!.status == 200){
                                // 통신 완료된 후 fill like로 변경
                                holder.search_result_like.isSelected = !holder.search_result_like.isSelected
                                holder.search_result_like.setBackgroundResource(R.drawable.icon_like_fill)
                            }
                        }
                    }

                })
            }
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var search_result_img_url = itemView.findViewById(com.example.goldenticket.R.id.ivKeepStage) as ImageView
        var search_result_like = itemView.findViewById(com.example.goldenticket.R.id.ibtnKeepShowLike) as ImageButton
    }
}