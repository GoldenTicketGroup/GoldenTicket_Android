package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.goldenticket.DB.SharedPreferenceController
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.Network.Post.PostLotteryResponse
import com.example.goldenticket.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_lottery_complete.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LotteryCompleteActivity : AppCompatActivity() {

    val jsonObject = JSONObject()

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_complete)


        Log.e("LottCompleteActi::", "onCreate::schedule_idx::" + intent.getIntExtra("idx", -1))

        Glide.with(this)
            .load(R.drawable.complete)
            .into(ivComplete)

        tvCommentLuck.text = SharedPreferenceController.getUserName(this@LotteryCompleteActivity) + "님 행운을 빌어요!"
        btnOkay.setOnClickListener {
//            jsonObject.put("schedule_idx", intent.getIntExtra("idx", -1))
//            postLotteryResponse()
            startActivity<MainActivity>()
        }
        ibtn_lottery_complete_close.onClick {finish()}
    }

//    private fun postLotteryResponse() {
//        //val token = SharedPreferenceController.getUserToken(this@LotteryCompleteActivity)
//        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4Ijo0LCJlbWFpbCI6ImVtYWlsMzMyNEBuYXZlci5jb20iLCJpYXQiOjE1NjIzMjE4ODZ9.JUsSqUu8OWnBAb3Hjt8uB09vHQV-eZ3VEiq8q8CHTk0"
//
//        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
//        val postLotteryResponse: Call<PostLotteryResponse> = networkService.postLotteryResponse("application/json", token, gsonObject)
//        postLotteryResponse.enqueue(object: Callback<PostLotteryResponse> {
//            override fun onFailure(call: Call<PostLotteryResponse>, t: Throwable) {
//                Log.e("LotteryCompleteActi::", "postLotteryResponse::Post_Lottery_Register_Fail")
//            }
//
//            override fun onResponse(call: Call<PostLotteryResponse>, response: Response<PostLotteryResponse>) {
//                if (response.isSuccessful) {
//                    Log.e("LotteryCompleteActi::", "postLotteryResponse::onResponse::Success::" + response.body()!!.message)
//                    toast(response.body()!!.message)
//                }
//                else {
//                    Log.e("LotteryCompleteActi::", "postLotteryResponse::onResponse::Fail::" + response.body()!!.message)
//                    toast(response.body()!!.message)
//                }
//            }
//        })
//    }

}
