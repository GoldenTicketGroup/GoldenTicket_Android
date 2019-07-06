package com.example.goldenticket.Activity


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.goldenticket.Adapter.KeepStageRVAdapter
import com.example.goldenticket.Data.KeepStageData
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.Network.Post.DeleteShowLikeResponse
import com.example.goldenticket.Network.Post.PostShowLikeResponse
import com.example.goldenticket.R
import com.example.goldenticket.etc.RecyclerViewDecoration
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_keep.*
import kotlinx.android.synthetic.main.activity_keep.view.*
import kotlinx.android.synthetic.main.activity_month_contents.*
import kotlinx.android.synthetic.main.rv_keep_item.*
import kotlinx.android.synthetic.main.toolbar_drawer.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Boolean

class KeepActivity : AppCompatActivity() {

    lateinit var keepRecyclerViewAdapter: KeepStageRVAdapter

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keep)


        /** 툴바 타이틀 설정 **/
        tb_title.text = "관심있는 공연"


        // TODO: 관심있는 공연 GET 통신해야 함
        var dataList: ArrayList<KeepStageData> = ArrayList()
        dataList.add(
            KeepStageData(
                "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjGkfDw4pHjAhUB5rwKHYekBmsQjRx6BAgBEAU&url=http%3A%2F%2Fhangangah.com%2F%25EA%25B0%2595%25EC%2595%2584%25EC%25A7%2580-%25EC%2583%2581%25EC%258B%259D-4%2F&psig=AOvVaw2DY9w_UJObZrGOAhL-ZsDf&ust=1562003555694345",
                Boolean.TRUE
            ))
        dataList.add(
            KeepStageData(
                "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwj7sZqL45HjAhUKVLwKHT6fBf4QjRx6BAgBEAU&url=https%3A%2F%2Fmypetlife.co.kr%2F10333%2F&psig=AOvVaw2DY9w_UJObZrGOAhL-ZsDf&ust=1562003555694345",
                Boolean.FALSE
            ))


        keepRecyclerViewAdapter = KeepStageRVAdapter(applicationContext, dataList)
        rvKeepStage.adapter = keepRecyclerViewAdapter
        rvKeepStage.layoutManager = GridLayoutManager(applicationContext,2)
        rvKeepStage.addItemDecoration(RecyclerViewDecoration())
        rvKeepStage.setHasFixedSize(true)



        ibtnKeepShowLike.setOnClickListener {
            /** 좋아요 취소 **/
            if(ibtnKeepShowLike.isSelected){
                var jsonObject = JSONObject()
                jsonObject.put("showIdx","1") // TODO: 인덱스 값 수정해야함
                val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

                // TODO: user token 쉐어드에서 가져와야함
                val deleteShowLike = networkService.deleteShowLike("application/json","adjlafkjad", gsonObject)
                deleteShowLike.enqueue(object: Callback<DeleteShowLikeResponse>{
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
                                ibtnKeepShowLike.isSelected = !ibtnKeepShowLike.isSelected
                            }
                        }
                    }

                })
            }
            /** 좋아요 **/ // 근데 이게 필요가 있나 어차피 관심있는 공연에는 다 좋아요 되어있는 것만 있을 텐데
            else{
                var jsonObject = JSONObject()
                jsonObject.put("showIdx","1") // TODO: 인덱스 값 수정해야함
                val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

                // TODO: user token 쉐어드에서 가져와야함
                val postShowLike = networkService.postShowLike("application/json","adjlafkjad", gsonObject)
                postShowLike.enqueue(object: Callback<PostShowLikeResponse>{
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
                                ibtnKeepShowLike.isSelected = !ibtnKeepShowLike.isSelected
                            }
                        }
                    }

                })
            }
        }
    }


}
