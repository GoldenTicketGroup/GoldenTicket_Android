package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        ibtn_search_pre.setOnClickListener {
            finish()
        }
        ibtn_search_submit.setOnClickListener() {

        }
        tv_search_cancel.setOnClickListener {
            finish()
        }

        //추천 검색어 onclick 시, 검색기능 처리
    }
}
