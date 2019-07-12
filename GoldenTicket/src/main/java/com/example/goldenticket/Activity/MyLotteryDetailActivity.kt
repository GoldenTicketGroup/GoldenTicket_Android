package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.goldenticket.DB.SharedPreferenceController
import com.example.goldenticket.Data.MyLotteryDetailData
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.Get.GetMyLotteryDetailResponse
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_my_lottery_detail.*
import kotlinx.android.synthetic.main.toolbar_drawer.*
import kotlinx.android.synthetic.main.toolbar_drawer.view.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyLotteryDetailActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_lottery_detail)

        if (intent.getIntExtra("idx", -1) != -1) { //인덱스통신
            getMyLotteryDetailResponse()
        } else { //토큰통신
            getMyLotteryDetailAvailableResponse()
        }
    }

    private fun setView(status: Int) {
        when (status) {
            204 -> {
                rl_mylottery_detail.visibility = View.GONE
                rl_mylottery_payment.visibility = View.GONE
                ll_mylottery_nothing.visibility = View.VISIBLE

                Glide.with(this)
                    .load(R.drawable.catching)
                    .into(iv_mylottery_nothing_character)

                btn_mylottery_nothing_stagelist.setOnClickListener {
                    finish()
                    startActivity<SearchActivity>()
                }
                ibtn_mylottery_nothing_close.setOnClickListener {
                    finish()
                }
            }
            205 -> {
                rl_mylottery_detail.visibility = View.GONE
                rl_mylottery_payment.visibility = View.VISIBLE
                ll_mylottery_nothing.visibility = View.GONE

                tb_mylottery_payment.tb_title.text = "당첨 티켓"
                tb_mylottery_payment.iv_back.setOnClickListener {
                    startActivity<MainActivity>()
                }

                btn_mylottery_payment_submit.setOnClickListener {
                    startActivity<MainActivity>()
                }
            }
            200 -> {
                rl_mylottery_detail.visibility = View.VISIBLE
                rl_mylottery_payment.visibility = View.GONE
                ll_mylottery_nothing.visibility = View.GONE

                tb_mylottery_detail.tb_title.text = "당첨 내역"
                tb_mylottery_detail.iv_back.setOnClickListener {
                    startActivity<MainActivity>()
                }
                btn_mylottery_detail_submit.setOnClickListener {
                    startActivity<MainActivity>()
                }
            }
        }
    }

    // 인덱스 통신
    private fun getMyLotteryDetailResponse() {
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4Ijo0LCJlbWFpbCI6ImVtYWlsMzMyNEBuYXZlci5jb20iLCJpYXQiOjE1NjIzMjE4ODZ9.JUsSqUu8OWnBAb3Hjt8uB09vHQV-eZ3VEiq8q8CHTk0"
        val token = SharedPreferenceController.getUserToken(this@MyLotteryDetailActivity)

        val getMyLotteryDetailResponse = networkService.getMyLotteryDetailResponse("application/json", token, intent.getIntExtra("idx", -1)) //메인에서 idx로 보내준 ticket_idx
        getMyLotteryDetailResponse.enqueue(object: Callback<GetMyLotteryDetailResponse> {
            override fun onFailure(call: Call<GetMyLotteryDetailResponse>, t: Throwable) {
                Log.e("MyLottDetailActivity::", "GET_My_Lottery_Data_Failed")
            }

            override fun onResponse(call: Call<GetMyLotteryDetailResponse>, response: Response<GetMyLotteryDetailResponse>) {
                if (response.isSuccessful) {

                    if (response.body()!!.status == 204) {
                        Log.e("MyLotteryDetailActi::", response.body()!!.data.toString())
                        setView(204) // 당첨된 거 없을 때
                    }

                    var tempData: MyLotteryDetailData = response.body()!!.data
                    if (tempData != null) {

                        if (response.body()!!.data.is_paid == 0) { // 당첨 됐는데 결제 전일 때
                            setView(205)

                            Glide.with(this@MyLotteryDetailActivity)
                                .load(response.body()!!.data.image_url)
                                .into(iv_mylottery_payment_poster)

                            tv_mylottery_payment_title.text = response.body()!!.data.name
                            tv_mylottery_payment_price.text = response.body()!!.data.price

                        } else if (response.body()!!.data.is_paid == 1) { // 당첨 됐고 결제 완료일 때
                            setView(200)

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
                }
                else {
                    Log.e("MyLottDetailActivity::", "onResponse::Fail")
                }
            }
        })
    }

    // 토큰 통신
    private fun getMyLotteryDetailAvailableResponse() {
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4Ijo0LCJlbWFpbCI6ImVtYWlsMzMyNEBuYXZlci5jb20iLCJpYXQiOjE1NjIzMjE4ODZ9.JUsSqUu8OWnBAb3Hjt8uB09vHQV-eZ3VEiq8q8CHTk0"
        val token = SharedPreferenceController.getUserToken(this@MyLotteryDetailActivity)

        val getMyLotteryDetailAvailableResponse = networkService.getMyLotteryDetailAvailableResponse("application/json", token)
        getMyLotteryDetailAvailableResponse.enqueue(object: Callback<GetMyLotteryDetailResponse> {
            override fun onFailure(call: Call<GetMyLotteryDetailResponse>, t: Throwable) {
                Log.e("MyLotteryDetailActi::", "GET_My_Lottery_Data_Failed")
            }

            override fun onResponse(call: Call<GetMyLotteryDetailResponse>, response: Response<GetMyLotteryDetailResponse>) {
                if (response.isSuccessful) {

                    if (response.body()!!.status == 204) {
                        Log.e("MyLotteryDetailActi::", response.body()!!.data.toString())
                        setView(204) // 당첨된 거 없을 때
                    }

                    var tempData: MyLotteryDetailData = response.body()!!.data
                    if (tempData != null) {

                        if (response.body()!!.data.is_paid == 0) { // 당첨 됐는데 결제 전일 때
                            setView(205)

                            Glide.with(this@MyLotteryDetailActivity)
                                .load(response.body()!!.data.image_url)
                                .into(iv_mylottery_payment_poster)

                            tv_mylottery_payment_title.text = response.body()!!.data.name
                            tv_mylottery_payment_price.text = response.body()!!.data.price

                        } else if (response.body()!!.data.is_paid == 1) { // 당첨 됐고 결제 완료일 때
                            setView(200)

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
                }
                else {
                    Log.e("MyLottDetailActivity::", "onResponse::Fail")
                }
            }
        })
    }
}