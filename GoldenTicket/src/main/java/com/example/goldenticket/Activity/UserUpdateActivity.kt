package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.goldenticket.DB.SharedPreferenceController.getUserEmail
import com.example.goldenticket.DB.SharedPreferenceController.getUserName
import com.example.goldenticket.DB.SharedPreferenceController.getUserPhone
import kotlinx.android.synthetic.main.activity_user_update.*
import kotlinx.android.synthetic.main.toolbar_drawer.*
import org.jetbrains.anko.toast
import java.util.regex.Matcher
import java.util.regex.Pattern
import android.widget.TextView
import com.example.goldenticket.DB.SharedPreferenceController
import com.example.goldenticket.DB.SharedPreferenceController.getUserToken
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.Network.Put.PutUserResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserUpdateActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    //이메일 형식 정규화
    val VALID_EMAIL_ADDRESS_REGEX: Pattern =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.goldenticket.R.layout.activity_user_update)

        //data 밑줄 활성화
        isDataVaild()

        //toolbar text
        tb_title.text = "회원정보 수정"
        //뒤로가기 화살표 클릭시 뒤로가기 메인으로
        iv_back.setOnClickListener {
            finish()
        }

        //수정 버튼을 눌렀을 때 이벤트
        btn_userupdate.setOnClickListener {
            val update_u_name = et_userupdate_name.text.toString()
            val update_u_email = et_userupdate_email.text.toString()
            val update_u_phone = et_userupdate_phone.text.toString()


            //서버에게 요청
            if (isValid(update_u_name, update_u_email, update_u_phone))
                putUserResponse(update_u_name, update_u_email, update_u_phone)
        }
        et_userupdate_name.setText(getUserName(this), TextView.BufferType.EDITABLE)
        et_userupdate_phone.setText(getUserPhone(this), TextView.BufferType.EDITABLE)
        et_userupdate_email.setText(getUserEmail(this), TextView.BufferType.EDITABLE)
    }

    //이메일 형식인지 유효성 검사
    fun validateEmail(emailStr: String): Boolean {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
        return matcher.find()
    }

    private fun isDataVaild() {
        et_userupdate_name.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_userupdate_name.text.toString() != "") v.setBackgroundResource(com.example.goldenticket.R.drawable.underline_yellow)
            else v.setBackgroundResource(com.example.goldenticket.R.drawable.underline_white)
        }
        et_userupdate_email.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_userupdate_email.text.toString() != "") v.setBackgroundResource(com.example.goldenticket.R.drawable.underline_yellow)
            else v.setBackgroundResource(com.example.goldenticket.R.drawable.underline_white)
        }
        et_userupdate_phone.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_userupdate_phone.text.toString() != "") v.setBackgroundResource(com.example.goldenticket.R.drawable.underline_yellow)
            else v.setBackgroundResource(com.example.goldenticket.R.drawable.underline_white)
        }
    }

    fun isValid(u_name: String, u_email: String, u_phone: String): Boolean {
        if (u_name == "") {
            toast("이름을 입력하세요")
            et_userupdate_name.requestFocus()
        } else if (u_email == "") {
            toast("이메일을 입력하세요")
            et_userupdate_email.requestFocus()
        } else if (u_phone == "") {
            toast("핸드폰을 입력하세요")
            et_userupdate_phone.requestFocus()
        } else if (!validateEmail(u_email)) {
            toast("이메일 형식이 아닙니다")
            et_userupdate_email.requestFocus()
        } else return true
        return false
    }

    //서버에 사용자 업데이트 요청
    fun putUserResponse(u_name: String, u_email: String, u_phone: String) {

        //id,password를 받아서 JSON객체로 만든다.
        var jsonObject = JSONObject()
        jsonObject.put("name", u_name)
        jsonObject.put("email", u_email)
        jsonObject.put("phone", u_phone)

        var token = getUserToken(this)

        //networkService를 통해 실제로 통신을 요청
        //application/x-www-form-urlencoded 는 해더로 전송된다.
        //gsonObject 는 body로 전송된다.
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val putUserResponse: Call<PutUserResponse> =
            networkService.putUserResponse("application/json", token, gsonObject)

        putUserResponse.enqueue(object : Callback<PutUserResponse> {
            override fun onFailure(call: Call<PutUserResponse>, t: Throwable) {
                Log.e("update user failed", t.toString())
            }

            override fun onResponse(call: Call<PutUserResponse>, response: Response<PutUserResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        //Request UserUpdate
                        SharedPreferenceController.setUserInfo(applicationContext, response.body()!!.data!!)
                        toast("수정이 되었습니다")
                    }
                }
            }
        })
    }
}
