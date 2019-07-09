package com.example.goldenticket.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import com.bumptech.glide.Glide
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_lottery_confirm.*
import kotlinx.android.synthetic.main.activity_lottery_notice.*
import org.jetbrains.anko.startActivity

class LotteryNoticeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_notice)

        Glide.with(this)
            .load(R.drawable.notice)
            .into(ivNotice)

        btnGoLottery.setOnClickListener {
            val intent = Intent(this, LotteryCompleteActivity::class.java)
            intent.putExtra("schedule_idx", intent.getIntExtra("schedule_idx", -1))
            startActivity(intent)
        }
    }

}
