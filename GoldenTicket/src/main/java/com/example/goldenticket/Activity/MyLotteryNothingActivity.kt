package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_my_lottery_nothing.*
import org.jetbrains.anko.startActivity

class MyLotteryNothingActivity : AppCompatActivity() {

    // 메인에서 티켓아이콘 onclick시, 미응모 상태화면

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_lottery_nothing)

        btn_mylottery_nothing_stagelist.setOnClickListener {
            startActivity<SearchActivity>()
        }
        ibtn_mylottery_nothing_close.setOnClickListener {
            finish()
        }
    }
}
