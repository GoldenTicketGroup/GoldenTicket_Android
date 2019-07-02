package com.example.goldenticket.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import com.example.goldenticket.R

class LotteryNoticeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_notice)
    }

    override fun onCreateOptionsMenu(menu: Menu?): kotlin.Boolean {
        val actionBar = supportActionBar

        actionBar!!.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(false) // 액션바 아이콘을 업 네비게이션 형태로 표시
        actionBar.setDisplayShowTitleEnabled(false) // 액션바에 표시되는 제목의 표시유무 설정
        actionBar.setDisplayShowHomeEnabled(false) // 홈 아이콘 숨김처리
        actionBar.elevation="0".toFloat()


        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val actionbar = inflater.inflate(R.layout.close_custom_actionbar, null)

        actionBar.setCustomView(actionbar)

        //액션바 양쪽 공백 없애기
        val parent = actionbar.getParent() as androidx.appcompat.widget.Toolbar
        parent.setContentInsetsAbsolute(0, 0)

        return true
    }
}
