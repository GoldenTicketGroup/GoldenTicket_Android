package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_my_lottery_detail.*
import kotlinx.android.synthetic.main.toolbar_drawer.*
import org.jetbrains.anko.startActivity

class MyLotteryDetailActivity : AppCompatActivity() {

    //메인에서 티켓아이콘 onclick시, 결제 완료 화면

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_lottery_detail)

        tb_title.text = "당첨 티켓"
        iv_back.setOnClickListener {
            startActivity<MainActivity>()
        }

        btn_mylottery_detail_submit.setOnClickListener {
            startActivity<MainActivity>()
        }
    }
}
