package com.dazzi.goldenticket.Activity

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dazzi.goldenticket.DB.SharedPreferenceController
import com.dazzi.goldenticket.Network.ApplicationController
import com.dazzi.goldenticket.Network.NetworkService
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.text.Editable
import com.dazzi.goldenticket.Network.Post.PostLoginResponse
import com.dazzi.goldenticket.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId


class LoginActivity : BaseActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.dazzi.goldenticket.R.layout.activity_login)

        //data 밑줄 활성화
        isDataVaild()

        //로그인 버튼을 눌렀을 때 이벤트
        btn_loginactivity_login.setOnClickListener {

            showProgressDialog()

            val login_u_id = et_loginactivity_id.text.toString()
            val login_u_pw: String = et_loginactivity_pw.text.toString()
            var fcm_token : String?

            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("TAG", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    fcm_token = task.result?.token

                    // Log and toast
                    val msg = getString(R.string.msg_token_fmt, fcm_token)
                    Log.d("TAG", msg)

                    //toast("$$$$$$$$"+fcm_token)
                    //아이디와 패스워드에 데이터가 있는지 검색하고
                    //있으면 서버에게 전달하여 로그인 요청
                    if (isValid(login_u_id, login_u_pw)) postLoginResponse(login_u_id, login_u_pw, fcm_token!!)
                })

            /*//아이디와 패스워드에 데이터가 있는지 검색하고
            //있으면 서버에게 전달하여 로그인 요청
            if (isValid(login_u_id, login_u_pw)) postLoginResponse(login_u_id, login_u_pw, fcm_token!!)*/
        }

        //회원 가입 버튼을 눌렀을 때 이벤트
        tv_loginactivity_signup.setOnClickListener {
            startActivity<SignUpActivity>()
        }
    }

    //아이디와 패스워드가 모두 채워져 있는지 확인 없으면 포커스
    fun isValid(u_id: String, u_pw: String): Boolean {
        if (u_id == "") {
            et_loginactivity_id.requestFocus()
            toast("이메일 주소를 입력하세요")
        } else if (u_pw == "") {
            et_loginactivity_pw.requestFocus()
            toast("비밀번호를 입력하세요")
        } else return true
        return false
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        var id_text = et_loginactivity_id.text.toString()
        var pw_text = et_loginactivity_pw.text.toString()
        setContentView(com.dazzi.goldenticket.R.layout.activity_login)
        et_loginactivity_id.text = id_text as Editable
        et_loginactivity_pw.text = pw_text as Editable
    }

    //아이디와 패스워드가 포커스가 되면 밑줄
    //아이디와 패스워드에 문자가 있으면 밑줄
    private fun isDataVaild() {
        et_loginactivity_id.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_loginactivity_id.text.toString() != "") v.setBackgroundResource(com.dazzi.goldenticket.R.drawable.underline_yellow)
            else v.setBackgroundResource(com.dazzi.goldenticket.R.drawable.underline_white)
        }
        et_loginactivity_pw.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_loginactivity_pw.text.toString() != "") v.setBackgroundResource(com.dazzi.goldenticket.R.drawable.underline_yellow)
            else v.setBackgroundResource(com.dazzi.goldenticket.R.drawable.underline_white)
        }
    }

    //서버에 로그인 요청
    fun postLoginResponse(u_id: String, u_pw: String, fcm_token :String) {

        //id,password를 받아서 JSON객체로 만든다.
        var jsonObject = JSONObject()
        jsonObject.put("email", u_id)
        jsonObject.put("password", u_pw)
        jsonObject.put("fcm_token", fcm_token)

        //networkService를 통해 실제로 통신을 요청
        //application/x-www-form-urlencoded 는 해더로 전송된다.
        //gsonObject 는 body로 전송된다.
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postLoginResponse: Call<PostLoginResponse> =
            networkService.postLoginResponse("application/json", gsonObject)

        postLoginResponse.enqueue(object : Callback<PostLoginResponse> {
            override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {
                hideProgressDialog()
                Log.e("login failed", t.toString())
                toast("아이디와 비밀번호를 확인해 주세요")
            }

            override fun onResponse(call: Call<PostLoginResponse>, response: Response<PostLoginResponse>) {
                hideProgressDialog()
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        //Request Login
                        toast("로그인이 되었습니다.")
                        Log.d("login", "로그인이 되었습니다." + response.body()!!.data!!)
                        SharedPreferenceController.setUserInfo(applicationContext, response.body()!!.data!!)

                        startActivity<MainActivity>()
                        finish()
                    }
                }
            }
        })
    }
}