package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.goldenticket.DB.SharedPreferenceController
import com.example.goldenticket.Data.MyLotteryDetailData
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.Get.GetMyLotteryDetailResponse
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_my_lottery_detail.*
import kotlinx.android.synthetic.main.toolbar_drawer.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyLotteryDetailActivity : AppCompatActivity() {

    //메인에서 티켓아이콘 onclick시, 결제 완료 화면
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_lottery_detail)

        tb_title.text = "당첨 티켓"
        iv_back.setOnClickListener {
            startActivity<MainActivity>()
        }

        btn_mylottery_detail_submit.setOnClickListener {
            startActivity<MainActivity>()
        }

        getMyLotteryResponse()
    }

    private fun getMyLotteryResponse() {
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4Ijo0LCJlbWFpbCI6ImVtYWlsMzMyNEBuYXZlci5jb20iLCJpYXQiOjE1NjIzMjE4ODZ9.JUsSqUu8OWnBAb3Hjt8uB09vHQV-eZ3VEiq8q8CHTk0"
        //val token = SharedPreferenceController.getUserToken(this@MyLotteryDetailActivity)

        val getMyLotteryDetailResponse = networkService.getMyLotteryDetailResponse("application/json", token, intent.getIntExtra("idx", -1)) //메인에서 idx로 보내준 ticket_idx
        getMyLotteryDetailResponse.enqueue(object: Callback<GetMyLotteryDetailResponse> {
            override fun onFailure(call: Call<GetMyLotteryDetailResponse>, t: Throwable) {
                Log.e("MyLottDetailActivity::", "GET_My_Lottery_Data_Failed")
            }

            override fun onResponse(call: Call<GetMyLotteryDetailResponse>, response: Response<GetMyLotteryDetailResponse>) {
                if (response.isSuccessful) {

                    var tempData: MyLotteryDetailData = response.body()!!.data
                    if (tempData != null) {
                        Glide.with(this@MyLotteryDetailActivity)
                            .load(response.body()!!.data.image_url)
                            .into(iv_mylottery_detail_poster)
                        tv_mylottery_detail_date.text = response.body()!!.data.date
                        tv_mylottery_detail_title.text = response.body()!!.data.name
                        tv_mylottery_detail_detail.text = response.body()!!.data.seatType
                        tv_mylottery_detail_location.text = response.body()!!.data.location
                        tv_mylottery_detail_time.text = response.body()!!.data.running_time
                        tv_mylottery_detail_seat.text = response.body()!!.data.seatName
                        Glide.with(this@MyLotteryDetailActivity)
                            .load(response.body()!!.data.image_url)
                            .into(iv_mylottery_detail_qrcode)
                    }
                }
                else {
                    Log.e("MyLottDetailActivity::", "onResponse::Fail")
                }
            }
        })
    }
}
