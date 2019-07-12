package com.example.goldenticket.Fragment


import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.icu.util.UniversalTimeScale.toLong
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.example.goldenticket.Activity.LotteryConfirmActivity
import com.example.goldenticket.Activity.StageInfoActivity
import com.example.goldenticket.Data.LotteryListData
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.Get.GetLotteryListResponse
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.DB.SharedPreferenceController.getUserToken
import com.example.goldenticket.Network.Get.GetLotteryConfirmDetailResponse


import com.example.goldenticket.R
import kotlinx.android.synthetic.main.fragment_lottery_first_timer.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*




class LotteryFirstTimerFragment : Fragment() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var start_time: String


    var diff : String = ""
    val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.ENGLISH)
    val now_time = System.currentTimeMillis()
    var confirm_time_sdf: Long = 0

    var mStartTimeInMillis: Long = 0 //10분으로 설정
    var mCountDownTimer: CountDownTimer? = null
    //var mTimerRunning: Boolean = false
    var mTimeLeftInMillis = mStartTimeInMillis
    var mEndTime: Long = 0

    var show_idx = 0

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
        return inflater.inflate(com.example.goldenticket.R.layout.fragment_lottery_first_timer, container, false)
    }

    //화면이 다시 돌아왔을 때 남은 시간와 타이머 상태를 가져온다.
    //타이머 텍스트와 버튼 상태를 다시 설정한다.
    override fun onStart() {
        super.onStart()

        var prefs: SharedPreferences = context!!.getSharedPreferences("GoldenTicket", MODE_PRIVATE)

        mStartTimeInMillis = prefs.getLong("startTimeInMillis",mStartTimeInMillis)
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis)

        mEndTime = prefs.getLong("endTime", 0)
        mTimeLeftInMillis = mEndTime - System.currentTimeMillis()
        updateCountDownText()
        startTimer()
    }

    override fun onStop() {
        super.onStop()

        var prefs: SharedPreferences = context!!.getSharedPreferences("GoldenTicket", MODE_PRIVATE)
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
                tv_first_timer_text.visibility = GONE
                tv_first_timer?.let{tv_first_timer.text = "당첨 확인"}
                view!!.isClickable = true // 타이머 시간이 다 되어서 클릭이 가능하도록 함
            }

            override fun onTick(p0: Long) {
                mTimeLeftInMillis = p0
                updateCountDownText()
                view!!.isClickable = false // 타이머 시간이 남았으면 클릭이 되지 않게 끔 함
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
            timeLeftFormatted = String.format(Locale.getDefault(), "%d : %02d : %02d", hours, minutes, seconds)
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d : %02d", minutes, seconds)
        }

        tv_first_timer?.let{tv_first_timer.text = timeLeftFormatted}
    }

    private fun getMainLotteryListResponse(){

        val getMainLotteryListResponse = networkService.getLotteryListResponse(
            "application/json", getUserToken(context!!))
        getMainLotteryListResponse.enqueue(object: retrofit2.Callback<GetLotteryListResponse> {
            override fun onFailure(call: Call<GetLotteryListResponse>, t: Throwable) {
                Log.e("Lottery List Fail", t.toString())
                mStartTimeInMillis = 0
            }

            override fun onResponse(call: Call<GetLotteryListResponse>, response: Response<GetLotteryListResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        if (response.body()!!.data.size != 0) {
                            show_idx = response.body()!!.data.get(0).show_idx

                            tv_first_timer_title.text = response.body()!!.data.get(0).name
                            start_time = response.body()!!.data.get(0).start_time + "m"

                            confirm_time_sdf = sdf.parse(start_time).time // getTime -> millis타입

                            mStartTimeInMillis = confirm_time_sdf - now_time
                            mTimeLeftInMillis = mStartTimeInMillis

                            mEndTime = System.currentTimeMillis() + mTimeLeftInMillis //현재 시간(초)에 남은 시간을 더한다.

                            var prefs: SharedPreferences = context!!.getSharedPreferences("GoldenTicket", MODE_PRIVATE)
                            var editor = prefs.edit()

                            editor.putLong("startTimeInMillis", mStartTimeInMillis)
                            editor.putLong("millisLeft", mTimeLeftInMillis)

                            editor.putLong("endTime", mEndTime)
                            editor.apply()


                            // 타이머 시간이 다 되었을 경우만 클릭이 가능함
                            if (view!!.isClickable){
                                view!!.onClick {
                                    Log.d("CLICKTEST: ", response.body()!!.data.get(0).lottery_idx.toString())
                                    val getLotteryConfirmDetailResponse = networkService.getLotteryConfirmDetail(
                                        "application/json", getUserToken(context!!),response.body()!!.data.get(0).lottery_idx)
                                    getLotteryConfirmDetailResponse.enqueue(object: Callback<GetLotteryConfirmDetailResponse>{
                                        override fun onFailure(call: Call<GetLotteryConfirmDetailResponse>, t: Throwable) {
                                            Log.e("Lottery Detail Fail", t.toString())
                                        }

                                        override fun onResponse(
                                            call: Call<GetLotteryConfirmDetailResponse>,
                                            response: Response<GetLotteryConfirmDetailResponse>
                                        ) {
                                            if (response.isSuccessful){
                                                if(response.body()!!.status == 200){
                                                    when(response.body()!!.data.size){
                                                        0 -> ctx.startActivity<LotteryConfirmActivity>("status" to 2,"idx" to show_idx)
                                                        1 -> ctx.startActivity<LotteryConfirmActivity>("status" to 1)
                                                    }
                                                }
                                            }
                                        }

                                    })
                                }
                            }


                        }
                    }
                }
            }
        })
    }
}