package com.example.goldenticket.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import com.bumptech.glide.Glide
import com.example.goldenticket.DB.SharedPreferenceController
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.Network.Post.PostLotteryResponse
import com.example.goldenticket.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_lottery_confirm.*
import kotlinx.android.synthetic.main.activity_lottery_notice.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LotteryNoticeActivity : AppCompatActivity() {

    val jsonObject = JSONObject()

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_notice)

        Glide.with(this)
            .load(R.drawable.notice)
            .into(ivNotice)


        var show_idx = intent.getIntExtra("schedule_idx", -1)
        Log.e("LottNoticeActi::", "onCreate::schedule_idx::" + show_idx)

        jsonObject.put("schedule_idx", show_idx)

        btnGoLottery.setOnClickListener {
            postLotteryResponse()
//            val intent = Intent(this, LotteryCompleteActivity::class.java)
//            intent.putExtra("idx", show_idx)
//            startActivity<LotteryCompleteActivity>()
        }

        ibtn_lottery_notice_close.onClick { finish() }
        
    }

    private fun postLotteryResponse() {
        val token = SharedPreferenceController.getUserToken(this)
//        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4Ijo0LCJlbWFpbCI6ImVtYWlsMzMyNEBuYXZlci5jb20iLCJpYXQiOjE1NjIzMjE4ODZ9.JUsSqUu8OWnBAb3Hjt8uB09vHQV-eZ3VEiq8q8CHTk0"

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postLotteryResponse: Call<PostLotteryResponse> = networkService.postLotteryResponse("application/json", token, gsonObject)
        postLotteryResponse.enqueue(object: Callback<PostLotteryResponse> {
            override fun onFailure(call: Call<PostLotteryResponse>, t: Throwable) {
                Log.e("LotteryCompleteActi::", "postLotteryResponse::Post_Lottery_Register_Fail")
            }

            override fun onResponse(call: Call<PostLotteryResponse>, response: Response<PostLotteryResponse>) {
                if (response.isSuccessful) {
                    if(response.body()!!.status == 200) startActivity<LotteryCompleteActivity>()
                    else if(response.body()!!.status == 205) {
                        toast(response.body()!!.message)
                        finish()
                    }
                    else if(response.body()!!.status == 204) {
                        toast(response.body()!!.message)
                        finish()
                    }
                }
                else {
                    Log.e("LotteryCompleteActi::", "postLotteryResponse::onResponse::Fail::" + response.body()!!.message)
                    toast(response.body()!!.message)
                }
            }
        })
    }

}
