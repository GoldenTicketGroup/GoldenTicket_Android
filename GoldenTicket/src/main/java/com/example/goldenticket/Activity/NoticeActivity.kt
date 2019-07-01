package com.example.goldenticket.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goldenticket.Data.NoticeData
import com.example.goldenticket.Adapter.NoticeRecyclerViewAdapter
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.toolbar_drawer.*

class NoticeActivity : AppCompatActivity() {

    val data = arrayListOf<NoticeData>(
        NoticeData("7월 시스템 점검 안내", "2019-07-11", "hi"),
        NoticeData(
            "앱 업데이트 공지", "2019-07-08", "안녕하세요. 골든티켓입니다.\n" +
                    "먼저 하위 버전에서 결제 확인이 원할하지\n" +
                    "않은 점 양해 부탁드립니다.\n" +
                    "\n" +
                    "최신버전(v2.1.1)으로 업데이트 해주셔야\n" +
                    "정상적인 이용이 가능합니다.\n" +
                    "\n" +
                    "더 좋은 서비스를 위해 더 노력하는\n" +
                    "골든티켓이 되겠습니다.\n" +
                    "감사합니다."
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        tb_title.setText(R.string.notice)

        iv_back.setOnClickListener {
            finish()
        }

        rv_notice.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_notice.layoutManager = LinearLayoutManager(this)
        rv_notice.adapter = NoticeRecyclerViewAdapter(this, data)
    }
}