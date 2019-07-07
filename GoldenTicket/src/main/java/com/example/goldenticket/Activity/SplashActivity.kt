package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.jetbrains.anko.startActivity
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.goldenticket.DB.SharedPreferenceController
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //상태바 없애기 full화면
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)


        Glide.with(this)
            .load(R.drawable.splash)
            .into(iv_splash)

    }

    override fun onStart() {
        super.onStart()

        val mHandler = Handler()
        mHandler.postDelayed(startConfirmUser, 2010)
    }

    //토큰을 받아와서 아이디가 있는지 확인
    //저장된 아이디가 있으면 메인화면으로 없으면 로그인 화면으로 넘어간다.
    internal val startConfirmUser: Runnable = Runnable {
        if(SharedPreferenceController.getUserInfo(this)!!.isEmpty()){
            startActivity<TutorialActivity>()
            finish()
        }else {
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }
}
