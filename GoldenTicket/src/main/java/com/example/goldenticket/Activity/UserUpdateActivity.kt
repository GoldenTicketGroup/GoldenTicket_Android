package com.example.goldenticket.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.toolbar_drawer.*

class UserUpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userupdate)

        tb_title.setText(R.string.user_update)

        iv_back.setOnClickListener {
            finish()
        }
    }
}