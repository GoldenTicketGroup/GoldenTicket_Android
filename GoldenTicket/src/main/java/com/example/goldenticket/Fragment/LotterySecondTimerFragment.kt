package com.example.goldenticket.Fragment


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.goldenticket.R
import kotlinx.android.synthetic.main.fragment_lottery_first_timer.*
import java.util.*

class LotterySecondTimerFragment : Fragment() {

    var mStartTimeInMillis: Long = 60000 * 61 //61분으로 설정
    var mCountDownTimer: CountDownTimer? = null
    //var mTimerRunning: Boolean = false
    var mTimeLeftInMillis = mStartTimeInMillis
    var mEndTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //응모한 티켓이 있을 때 타이머가 돌아가고 없으면 다른 View가 나온다.
        startTimer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lottery_first_timer, container, false)
    }

    //첫 번째 파라미터 남은 시간, 두 번째 파라미터 카운트 다운이 되는 시간간격
    //시간 간격 만큼 onFinish, onTick 이 실행이 된다.
    //카운트 다운 실행 중 일때 남은 시간을 화면에 보여주어야 한다.
    //카운트 다운이 완료가 되었을 때 버튼의 상태가 바껴야 한다.
    private fun startTimer() {
        //현재 시간(초)에 남은 시간을 더한다.
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis

        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onFinish() {

                // mTimerRunning = false
            }

            override fun onTick(p0: Long) {
                mTimeLeftInMillis = p0
                updateCountDownText()
            }
        }.start()
        //mTimerRunning = true
    }

    //남은 시간을 화면에 출력한다.
    private fun updateCountDownText() {
        var hours = (mTimeLeftInMillis / 1000) / 3600
        var minutes = ((mTimeLeftInMillis / 1000) % 3600) / 60
        var seconds = (mTimeLeftInMillis / 1000) % 60

        var timeLeftFormatted: String = "00:00:00"


        //60분이 넘으면 시간 까지 아니면 분, 초만 나온다.
        if(hours > 0 ) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d",hours, minutes, seconds)
        }else{
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }

        tv_first_timer.text = timeLeftFormatted
    }

    override fun onStop() {
        super.onStop()
        var prefs : SharedPreferences = context!!.getSharedPreferences("aaa", Context.MODE_PRIVATE)
        var editor = prefs.edit()

        editor.putLong("startTimeInMillis",mStartTimeInMillis)
        editor.putLong("millisLeft", mTimeLeftInMillis)
        //editor.putBoolean("timerRunning",mTimerRunning)
        editor.putLong("endTime",mEndTime)

        editor.apply()

        mCountDownTimer?.cancel()
    }
    //화면이 다시 돌아왔을 때 남은 시간와 타이머 상태를 가져온다.
    //타이머 텍스트와 버튼 상태를 다시 설정한다.
    override fun onStart() {
        super.onStart()

        var prefs : SharedPreferences = context!!.getSharedPreferences("aaa", Context.MODE_PRIVATE)

        mStartTimeInMillis = prefs.getLong("startTimeInMillis",600000)
        mTimeLeftInMillis = prefs.getLong("millisLeft",mStartTimeInMillis)
        //mTimerRunning = prefs.getBoolean("timerRunning", false)
        mEndTime = prefs.getLong("endTime", 0)
        mTimeLeftInMillis = mEndTime - System.currentTimeMillis()
        updateCountDownText()
        startTimer()

        /*if(mTimerRunning){
            mEndTime = prefs.getLong("endTime", 0)
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis()

            if(mTimeLeftInMillis < 0){
                mTimeLeftInMillis = 0
                mTimerRunning = false
                updateCountDownText()
            }else{
                startTimer()
            }
        }*/
    }
}
