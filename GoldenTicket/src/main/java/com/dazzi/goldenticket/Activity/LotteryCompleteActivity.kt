package com.dazzi.goldenticket.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.DB.SharedPreferenceController
import com.dazzi.goldenticket.Network.ApplicationController
import com.dazzi.goldenticket.Network.NetworkService
import com.dazzi.goldenticket.R
import kotlinx.android.synthetic.main.activity_lottery_complete.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.json.JSONObject

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
//            startActivityForResult(Intent(ctx,MainActivity::class.java),0)
            startActivity<MainActivity>()
            finish()
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