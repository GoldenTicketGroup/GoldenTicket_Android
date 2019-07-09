package com.example.goldenticket.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goldenticket.Adapter.NoticeRecyclerViewAdapter
import com.example.goldenticket.Adapter.QuestionRecyclerViewAdapter
import com.example.goldenticket.Data.QuestionData
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.toolbar_drawer.*

class QuestionActivity : AppCompatActivity() {

    val dataList = arrayListOf<QuestionData>(
            QuestionData("하루에 응모 가능 개수가 정해져 있나요?", "하루 최대 2개의 공연에 응모하실 수 있습니다."),
            QuestionData(
                    "결제 완료 후 티켓 취소가 가능한가요?", "당첨자 선정은 랜덤 추첨을 통해 선정됩니다."
            ),
            QuestionData("좌석은 어떻게 정해지나요?", "좌석은 당첨 후 결제가 완료되었을 때 공연측으로부터 배정받게 됩니다.\n" +
                    "특정 좌석을 미리 선택할 수 없습니다.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        tb_title.setText(R.string.FAQ)

        iv_back.setOnClickListener {
            finish()
        }

        rv_question.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_question.layoutManager = LinearLayoutManager(this)
        rv_question.adapter = QuestionRecyclerViewAdapter(this, dataList)
    }
}