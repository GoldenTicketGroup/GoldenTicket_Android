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
import com.example.goldenticket.Data.KeepStageData
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
        view.setOnClickListener {
            //해당 포지션의 show_idx를 stageinfo의 path variable로 전달
            view.context.startActivity<StageInfoActivity>("idx" to 20) //TODO: idx 서버에서 받아서 수정
        }
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(ctx)
            .load(R.drawable.test)
            .into(holder.search_result_img_url)
        holder.search_result_like.isSelected = true


        holder.search_result_like.setOnClickListener {

            /** 좋아요 취소 **/
            if(holder.search_result_like.isSelected){

                // TODO: 통신완료 후에 바꾸는걸로 수정해야함
                holder.search_result_like.setBackgroundResource(R.drawable.icon_like_nofill)
                holder.search_result_like.isSelected = false

                var jsonObject = JSONObject()
                jsonObject.put("showIdx","1") // TODO: 인덱스 값 수정해야함
                val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

                // TODO: user token 쉐어드에서 가져와야함
                val deleteShowLike = networkService.deleteShowLike("application/json","adjlafkjad", gsonObject)
                deleteShowLike.enqueue(object: Callback<DeleteShowLikeResponse> {
                    override fun onFailure(call: Call<DeleteShowLikeResponse>, t: Throwable) {
                        Log.e("Delete ShowLike Failed:",t.toString())
                    }

                    override fun onResponse(
                        call: Call<DeleteShowLikeResponse>,
                        response: Response<DeleteShowLikeResponse>
                    ) {
                        if(response.isSuccessful){
                            if(response.body()!!.status == 200){
                                // 통신 완료된 후 no fill like로 변경
//                                holder.btn_like.isSelected = !holder.btn_like.isSelected
                            }
                        }
                    }

                })
            }
            /** 좋아요 **/
            else{
                // TODO: 통신완료 후에 바꾸는걸로 수정해야함
                holder.search_result_like.setBackgroundResource(R.drawable.icon_like_fill)
                holder.search_result_like.isSelected = true

                var jsonObject = JSONObject()
                jsonObject.put("showIdx","1") // TODO: 인덱스 값 수정해야함
                val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

                // TODO: user token 쉐어드에서 가져와야함
                val postShowLike = networkService.postShowLike("application/json","adjlafkjad", gsonObject)
                postShowLike.enqueue(object: Callback<PostShowLikeResponse> {
                    override fun onFailure(call: Call<PostShowLikeResponse>, t: Throwable) {
                        Log.e("Delete ShowLike Failed:",t.toString())
                    }

                    override fun onResponse(
                        call: Call<PostShowLikeResponse>,
                        response: Response<PostShowLikeResponse>
                    ) {
                        if(response.isSuccessful){
                            if(response.body()!!.status == 200){
                                // 통신 완료된 후 fill like로 변경
//                                holder.btn_like.isSelected = !holder.btn_like.isSelected
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