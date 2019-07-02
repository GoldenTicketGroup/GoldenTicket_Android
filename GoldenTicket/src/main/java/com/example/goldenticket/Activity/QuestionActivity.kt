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
            QuestionData("하루에 응모 가능 개수가 정해져 있나요?", "2019-07-11", "hi"),
            QuestionData(
                    "결제 완료 후 티켓 취소가 가능한가요?", "2019-07-08", "결제 완료 후 티켓 취소 및 변경은 불가합니다. \n" +
                    "\n" +
                    "감사합니다."
            ),
            QuestionData("좌석은 어떻게 정해지나요?", "2019-07-08", "좌석은~")
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