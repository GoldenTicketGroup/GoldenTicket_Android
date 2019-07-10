package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_lottery_confirm.*
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.activity_user_update.view.*
import org.jetbrains.anko.*

class LotteryConfirmActivity : AppCompatActivity() {

    // 메인에서 타이머 onclick시, 당첨/미당첨 캐릭터화면

    // NETWORK:: status받아오기, 1=당첨, 2=미당첨

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView (R.layout.activity_lottery_confirm)

//        val status = 1
        // LotteryFirstTimerFragment와 LotterySecondTimerFragment에서 status값을 intent로 넘겨준걸 받음
        var status = intent.getIntExtra("status",2)
        setLayoutByStatusCode(status)
        setOnClickListener(status)
    }

    private fun setOnClickListener(status: Int) {
        ibtn_lotteryconfirm_close.setOnClickListener {
            finish()
        }
        btn_lotteryconfirm_stagelike.setOnClickListener {
            alert(title="관심있는 공연 추가", message="추가하시겠습니까?") {
                positiveButton("Yes"){
                    toast("관심있는 공연에 추가하였습니다.")
                }
                negativeButton("No"){
                    toast("취소하였습니다.")
                }
            }.show()
            true
        }
        btn_lotteryconfirm_stagelist.setOnClickListener {
            when (status) {
                1 -> startActivity<LotteryNoticeActivity>()
                2 -> startActivity<SearchActivity>()
            }
        }
    }

    private fun setLayoutByStatusCode(status: Int) {
        when (status) {
            1 -> {
                Glide.with(this)
                    .load(R.drawable.win)
                    .into(iv_lotteryconfirm_character)
                tv_lotteryconfirm_title.text = "당첨입니다!"
                tv_lotteryconfirm_title.setTextColor(getResources().getColor(R.color.colorCoral))
                iv_lotteryconfirm_character.setImageResource(R.drawable.win)
                rl_lotteryconfirm_suggested.visibility = View.VISIBLE
                rl_lotteryconfirm_unsuggested.visibility = View.GONE
                btn_lotteryconfirm_stagelike.visibility = View.INVISIBLE
                btn_lotteryconfirm_stagelist.text = "결제하기"
            }
            2 -> {
                Glide.with(this)
                    .load(R.drawable.fail)
                    .into(iv_lotteryconfirm_character)
                tv_lotteryconfirm_title.text = "아쉽지만 당첨되지\n 않았어요"
                tv_lotteryconfirm_title.setTextColor(getResources().getColor(R.color.colorCoral))
                iv_lotteryconfirm_character.setImageResource(R.drawable.fail)
                rl_lotteryconfirm_suggested.visibility = View.GONE
                rl_lotteryconfirm_unsuggested.visibility = View.VISIBLE
                btn_lotteryconfirm_stagelike.visibility = View.VISIBLE
                btn_lotteryconfirm_stagelist.text = "다른 공연 보기"
            }
        }
    }
}
