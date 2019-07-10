package com.example.goldenticket.Fragment


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.goldenticket.Data.LotteryListData
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.Get.GetLotteryListResponse
import com.example.goldenticket.Network.NetworkService

import com.example.goldenticket.R
import kotlinx.android.synthetic.main.fragment_lottery_first_timer.*
import kotlinx.android.synthetic.main.fragment_lottery_second_timer.*
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList



class LotterySecondTimerFragment : Fragment() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var start_time: String

    val nowSeconds: Long
        get() = Calendar.getInstance().timeInMillis / 1000


    var diff : String = ""
    val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.ENGLISH)
    val now_time = System.currentTimeMillis()
    var confirm_time_sdf: Long = 0

    var mStartTimeInMillis: Long = 0 //10분으로 설정
    var mCountDownTimer: CountDownTimer? = null
    //var mTimerRunning: Boolean = false
    var mTimeLeftInMillis = mStartTimeInMillis
    var mEndTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //응모한 티켓이 있을 때 타이머가 돌아가고 없으면 다른 View가 나온다.
        getMainLotteryListResponse()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.example.goldenticket.R.layout.fragment_lottery_second_timer, container, false)

    }

    //화면이 다시 돌아왔을 때 남은 시간와 타이머 상태를 가져온다.
    //타이머 텍스트와 버튼 상태를 다시 설정한다.
    override fun onStart() {
        super.onStart()

        var prefs: SharedPreferences = context!!.getSharedPreferences("GoldenTicketSecond", Context.MODE_PRIVATE)

        mStartTimeInMillis = prefs.getLong("startTimeInMillis",mStartTimeInMillis)
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis)

        mEndTime = prefs.getLong("endTime", 0)
        mTimeLeftInMillis = mEndTime - System.currentTimeMillis()
        updateCountDownText()
        startTimer()
    }

    override fun onStop() {
        super.onStop()
        var prefs: SharedPreferences = context!!.getSharedPreferences("GoldenTicketSecond", Context.MODE_PRIVATE)
        var editor = prefs.edit()

        editor.putLong("startTimeInMillis", mStartTimeInMillis)
        editor.putLong("millisLeft", mTimeLeftInMillis)

        editor.putLong("endTime", mEndTime)
        editor.apply()

        mCountDownTimer?.cancel()

    }

    //첫 번째 파라미터 남은 시간, 두 번째 파라미터 카운트 다운이 되는 시간간격
    //시간 간격 만큼 onFinish, onTick 이 실행이 된다.
    //카운트 다운 실행 중 일때 남은 시간을 화면에 보여주어야 한다.
    //카운트 다운이 완료가 되었을 때 버튼의 상태가 바껴야 한다.
    private fun startTimer() {

        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onFinish() {
                tv_second_timer_text.visibility = View.GONE
                tv_second_timer?.let{tv_second_timer.text = "당첨 확인"}
            }

            override fun onTick(p0: Long) {
                mTimeLeftInMillis = p0
                updateCountDownText()
            }
        }.start()
    }

    //남은 시간을 화면에 출력한다.
    private fun updateCountDownText() {


        var hours = (mTimeLeftInMillis / 1000) / 3600
        var minutes = ((mTimeLeftInMillis / 1000) % 3600) / 60
        var seconds = (mTimeLeftInMillis / 1000) % 60

        var timeLeftFormatted: String = "00:00"

        //60분이 넘으면 시간 까지 아니면 분, 초만 나온다.
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }

        tv_second_timer?.let{tv_second_timer.text = timeLeftFormatted}
    }

    private fun getMainLotteryListResponse(){
        val getMainLotteryListResponse = networkService.getLotteryListResponse(
            "application/json", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxMiwiZW1haWwiOiJlbWFpbDExMjRAbmF2ZXIuY29tIiwiaWF0IjoxNTYyNDg0MzUwfQ.6X8aYIp1rfeh9T43KBQSyz3hRIRRoo3M-W7CYQm4Pg8")
        getMainLotteryListResponse.enqueue(object: retrofit2.Callback<GetLotteryListResponse> {
            override fun onFailure(call: Call<GetLotteryListResponse>, t: Throwable) {
                Log.e("Lottery List Fail", t.toString())
                mStartTimeInMillis = 0
            }

            override fun onResponse(call: Call<GetLotteryListResponse>, response: Response<GetLotteryListResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        if (response.body()!!.data.size != 0) {
                            tv_second_timer_title.text = response.body()!!.data.get(1).name
                            start_time = response.body()!!.data.get(1).start_time + "m"

                            confirm_time_sdf = sdf.parse(start_time).time // getTime -> millis타입

                            mStartTimeInMillis = confirm_time_sdf - now_time
                            mTimeLeftInMillis = mStartTimeInMillis

                            mEndTime = System.currentTimeMillis() + mTimeLeftInMillis //현재 시간(초)에 남은 시간을 더한다.


                            var prefs: SharedPreferences = context!!.getSharedPreferences("GoldenTicketSecond", Context.MODE_PRIVATE)
                            var editor = prefs.edit()

                            editor.putLong("startTimeInMillis", mStartTimeInMillis)
                            editor.putLong("millisLeft", mTimeLeftInMillis)

                            editor.putLong("endTime", mEndTime)
                            editor.apply()

                            startTimer()
                        }
                    }
                }
            }
        })
    }
}