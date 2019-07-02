package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_lottery_confirm_detail.*
import kotlinx.android.synthetic.main.toolbar_drawer.*

class LotteryConfirmDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_confirm_detail)

        tb_title.text = "당첨 티켓"
        iv_back.setOnClickListener {
            finish()
        }
    }

    private fun setOnClickListener() {

    }
}
