package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.core.view.marginTop
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_my_lottery.*
import org.jetbrains.anko.textColor

class MyLotteryActivity : AppCompatActivity() {

    //status 1, 2, 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_lottery)

        setLayoutByStatusCode(2)
        setOnClickListener()
    }

    private fun setOnClickListener() {

    }

    private fun setLayoutByStatusCode(statusCode: Int) {
        when (statusCode) {
            1 -> {
                tv_mylottery_title.text = "아직 당첨\n 티켓이 없어요"
                iv_mylottery_character.setImageResource(R.drawable.character_fail)
                tv_mylottery_content.visibility = View.VISIBLE
                tv_mylottery_congrate.visibility = View.INVISIBLE
                tv_mylottery_content.text = "매일매일 새로운 기회가\n 쏟아지고 있어요!"
                tv_mylottery_highlight.text = "어떤 공연이 있는지 둘러보러 가볼까요?"
                btn_mylottery_stagelike.visibility = View.INVISIBLE
                btn_mylottery_stagelist.text = "공연 보러 가기"
            }
            2 -> {
                tv_mylottery_title.text = "아쉽지만 당첨되지\n 않았어요"
                tv_mylottery_title.setTextColor(getResources().getColor(R.color.colorCoral))
                iv_mylottery_character.setImageResource(R.drawable.character_fail)
                tv_mylottery_content.visibility = View.VISIBLE
                tv_mylottery_congrate.visibility = View.INVISIBLE
                tv_mylottery_content.text = "걱정하지 마세요. 매일 새로운\n 골든티켓에 응모할 수 있습니다."
                tv_mylottery_highlight.text = "보고싶은 공연의 알림을 설정해두세요."
                btn_mylottery_stagelike.visibility = View.VISIBLE
                btn_mylottery_stagelist.text = "다른 공연 보기"
            }
            3 -> {
                tv_mylottery_title.text = "당첨입니다!"
                tv_mylottery_title.setTextColor(getResources().getColor(R.color.colorCoral))
                iv_mylottery_character.setImageResource(R.drawable.character_win)
                tv_mylottery_content.visibility = View.INVISIBLE
                tv_mylottery_congrate.visibility = View.VISIBLE
                tv_mylottery_highlight.text = "결제는 30분 이내에 진행해주세요.\n 30분이 지나면 자동취소됩니다."
                tv_mylottery_highlight.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                btn_mylottery_stagelike.visibility = View.INVISIBLE
                btn_mylottery_stagelist.text = "다른 공연 보기"
            }
        }
    }
}
