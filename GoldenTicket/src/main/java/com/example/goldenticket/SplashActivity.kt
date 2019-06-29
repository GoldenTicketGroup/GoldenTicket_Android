package com.example.goldenticket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.jetbrains.anko.startActivity
import android.view.WindowManager


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //상태바 없애기 full화면
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()

        val mHandler = Handler()
        mHandler.postDelayed(startConfirmUser, 1200)
    }

    //토큰을 받아와서 아이디가 있는지 확인
    internal val startConfirmUser: Runnable = Runnable {
        startActivity<TutorialActivity>()
        finish()
    }


    override fun onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }

}
