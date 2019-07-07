package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_lottery_confirm.*

class LotteryConfirmActivity : AppCompatActivity() {

    // 메인에서 타이머 onclick시, 당첨/미당첨 캐릭터화면
    // status:: 1=당첨, 2=미당첨

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_confirm)

        setLayoutByStatusCode(1)
        setOnClickListener()
    }

    private fun setOnClickListener() {

    }

    private fun setLayoutByStatusCode(statusCode: Int) {
        when (statusCode) {
            1 -> {
                tv_lotteryconfirm_title.text = "당첨입니다!"
                tv_lotteryconfirm_title.setTextColor(getResources().getColor(R.color.colorCoral))
                iv_lotteryconfirm_character.setImageResource(R.drawable.character_win)
                tv_lotteryconfirm_content.visibility = View.INVISIBLE
                tv_lotteryconfirm_congrate.visibility = View.VISIBLE
                tv_lotteryconfirm_highlight.text = "결제는 30분 이내에 진행해주세요.\n 30분이 지나면 자동취소됩니다."
                //tv_lotteryconfirm_highlight.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                btn_lotteryconfirm_stagelike.visibility = View.INVISIBLE
                btn_lotteryconfirm_stagelist.text = "다른 공연 보기"
            }
            2 -> {
                tv_lotteryconfirm_title.text = "아쉽지만 당첨되지\n 않았어요"
                tv_lotteryconfirm_title.setTextColor(getResources().getColor(R.color.colorCoral))
                iv_lotteryconfirm_character.setImageResource(R.drawable.character_fail)
                tv_lotteryconfirm_content.visibility = View.VISIBLE
                tv_lotteryconfirm_congrate.visibility = View.INVISIBLE
                tv_lotteryconfirm_content.text = "걱정하지 마세요. 매일 새로운\n 골든티켓에 응모할 수 있습니다."
                tv_lotteryconfirm_highlight.text = "보고싶은 공연의 알림을 설정해두세요."
                btn_lotteryconfirm_stagelike.visibility = View.VISIBLE
                btn_lotteryconfirm_stagelist.text = "다른 공연 보기"
            }
        }
    }
}
