package com.example.goldenticket.Network

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.example.goldenticket.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationController : Application(){

    //manifests에 name속성 설정하기
    //통신하고자 하는 API 서버의 기본 주소
    private val baseURL ="https://goldenticket.ga"
    //internal var progressDialog: AppCompatDialog? = null

    lateinit var networkService: NetworkService

    companion object{
        lateinit var instance: ApplicationController
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        buildNetwork()
    }

    //Retrofit 객체 생성
    private fun buildNetwork() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //Retrofit 객체 활성화
        networkService = retrofit.create(NetworkService::class.java)
    }
   /* fun progressON(activity: Activity?, message: String) {
        if (activity == null || activity.isFinishing) {
            return
        }
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressSET(message)
        } else {
            progressDialog = AppCompatDialog(activity)
            progressDialog!!.setCancelable(false)
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog!!.setContentView(R.layout.progress_loading)
            progressDialog!!.show()
        }
        val img_loading_frame = progressDialog!!.findViewById<View>(R.id.iv_frame_loading) as ImageView?
        val frameAnimation = img_loading_frame!!.background as AnimationDrawable
        img_loading_frame.post { frameAnimation.start() }
        val tv_progress_message = progressDialog!!.findViewById<View>(R.id.tv_progress_message) as TextView?
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message!!.text = message
        }
    }

    fun progressSET(message: String) {
        if (progressDialog == null || !progressDialog!!.isShowing) {
            return
        }
        val tv_progress_message = progressDialog!!.findViewById<View>(R.id.tv_progress_message) as TextView?
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message!!.text = message
        }
    }

    fun progressOFF() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }*/
}