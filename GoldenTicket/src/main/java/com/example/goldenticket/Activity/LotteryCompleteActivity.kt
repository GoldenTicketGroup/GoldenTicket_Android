package com.example.goldenticket.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_lottery_complete.*
import org.jetbrains.anko.startActivity

class LotteryCompleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_complete)

        btnOkay.setOnClickListener {
            startActivity<MyLotteryPaymentActivity>()
        }
    }

}
