package com.example.goldenticket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tutorial.*
import org.jetbrains.anko.startActivity

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        //튜토리얼 이미지 슬라이더 처리
        vp_tutorial_slider.adapter = TutorialSliderAdapter(supportFragmentManager, 3)
        vp_tutorial_slider.offscreenPageLimit = 2
        tl_tutorial_indicator.setupWithViewPager(vp_tutorial_slider)

        //SKIP text 누르면 화면으로 넘어감
        tv_tutorial_skip.setOnClickListener {
            startActivity<MainActivity>()
        }
    }
}
