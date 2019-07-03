package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_lottery_confirm_payment.*
import kotlinx.android.synthetic.main.toolbar_drawer.*
import java.text.NumberFormat
import java.util.*

class LotteryConfirmPaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_confirm_payment)

        tb_title.text = "결제 안내"
        iv_back.setOnClickListener {
            finish()
        }

        tv_lotteryconfirmpayment_title.text = "액희보고싶"
        tv_lotteryconfirmpayment_price.text = NumberFormat.getNumberInstance(Locale.US).format(20000) +" 원"

        btn_lotteryconfirmdetail_submit.setOnClickListener {
            finish()
        }
    }
}
