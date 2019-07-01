package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.example.goldenticket.Adapter.TutorialSliderAdapter
import kotlinx.android.synthetic.main.activity_tutorial.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import android.util.Log.d as d1


class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.goldenticket.R.layout.activity_tutorial)

        //튜토리얼 이미지 슬라이더 처리
        vp_tutorial_slider.adapter = TutorialSliderAdapter(supportFragmentManager, 3)
        vp_tutorial_slider.offscreenPageLimit = 2
        tl_tutorial_indicator.setupWithViewPager(vp_tutorial_slider)

        //SKIP text 누르면 화면으로 넘어감
        tv_tutorial_skip.setOnClickListener {
            startActivity<MainActivity>()
        }

        //슬라이더 이미지가 바뀌었을 때 이벤트
        //마지막 이미지일 때만 버튼이 시작이다. 아니면 다음으로 된다.
        vp_tutorial_slider.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == 2) {
                    btn_tutorial_next.text = "시작"
                } else
                    btn_tutorial_next.text = "다음"
            }

            override fun onPageSelected(position: Int) {
            }

        })

        //다음 버튼을 누르면 이미지 슬라이더가 넘어간다.
        //마지막 슬라이더 이미지에서는 버튼 text가 시작으로 바뀐다.
        //시작 버튼을 누르면 mainactivity로 간다.
        btn_tutorial_next.setOnClickListener {
            if (btn_tutorial_next.text.toString() == "시작") {
                startActivity<MainActivity>()
            } else {
                vp_tutorial_slider.setCurrentItem(getItem(+1), true)
            }
            if (vp_tutorial_slider.currentItem == 2) {
                btn_tutorial_next.text = "시작"
            }
        }
    }

    /*fun changeButtonText() {
        when(vp_tutorial_slider.currentItem){
            0 -> btn_tutorial_next.text = "다음"
            1 -> btn_tutorial_next.text = "다음"
            2 -> btn_tutorial_next.text = "시작"
        }
    }*/


    private fun getItem(i: Int): Int {
        return vp_tutorial_slider.currentItem + i
    }
}
