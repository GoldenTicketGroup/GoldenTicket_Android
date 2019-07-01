package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //data 밑줄 활성화
        isDataVaild()

        //로그인 버튼을 눌렀을 때 이벤트
        btn_loginactivity_login.setOnClickListener {
            val login_u_id = et_loginactivity_id.text.toString()
            val login_u_pw: String = et_loginactivity_pw.text.toString()


            //아이디와 패스워드에 데이터가 있는지 검색하고
            //있으면 서버에게 전달하여 로그인 요청
            /*if (isValid(login_u_id, login_u_pw)) postLoginResponse(login_u_id, login_u_pw)*/
        }

        //회원 가입 버튼을 눌렀을 때 이벤트
        tv_loginactivity_signup.setOnClickListener {
            startActivity<SignUpActivity>()

        }
    }

    //아이디와 패스워드가 모두 채워져 있는지 확인 없으면 포커스
    fun isValid(u_id: String, u_pw: String): Boolean {
        if (u_id == "") et_loginactivity_id.requestFocus()
        else if (u_pw == "") et_loginactivity_pw.requestFocus()
        else return true
        return false
    }


    //아이디와 패스워드가 포커스가 되면 밑줄
    //아이디와 패스워드에 문자가 있으면 밑줄
    private fun isDataVaild() {
        et_loginactivity_id.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_loginactivity_id.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
        et_loginactivity_pw.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_loginactivity_pw.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
    }

    //서버에 로그인 요청
    /*fun postLoginResponse(u_id: String, u_pw: String) {

        //id,password를 받아서 JSON객체로 만든다.
        var jsonObject = JSONObject()
        jsonObject.put("id", u_id)
        jsonObject.put("password", u_pw)

        //networkService를 통해 실제로 통신을 요청
        //application/x-www-form-urlencoded 는 해더로 전송된다.
        //gsonObject 는 body로 전송된다.
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postLoginResponse: Call<PostLoginResponse> =
            networkService.postLoginResponse("application/json", gsonObject)

        postLoginResponse.enqueue(object : Callback<PostLoginResponse> {
            override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {
                Log.e("login failed",t.toString())
            }

            override fun onResponse(call: Call<PostLoginResponse>, response: Response<PostLoginResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 201){
                        //Request Login
                        SharedPreferenceController.setUserToken(applicationContext, response.body()!!.data!!)
                        finish()
                    }
                }
            }
        })
    }*/
}
