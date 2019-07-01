package com.example.goldenticket.Activity

import android.opengl.ETC1.isValid
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_user_update.*
import org.jetbrains.anko.toast

class UserUpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_update)

        //data 밑줄 활성화
        isDataVaild()

        //수정 버튼을 눌렀을 때 이벤트
        btn_userupdate.setOnClickListener {
            val update_u_name = et_userupdate_name.text.toString()
            val update_u_email = et_userupdate_email.text.toString()
            val update_u_phone = et_userupdate_phone.text.toString()

            //서버에게 요청
            /* if (isValid(update_u_name, update_u_email,update_u_phone))
                 putUserResponse(update_u_name, update_u_email,update_u_phone)*/
        }
    }

    private fun isDataVaild() {
        et_userupdate_name.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_userupdate_name.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
        et_userupdate_email.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_userupdate_email.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
        et_userupdate_phone.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_userupdate_phone.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
    }

    fun isValid(u_name: String, u_email: String, u_phone: String): Boolean {
        if (u_name == "") {
            toast("이름을 입력하세요 .")
            et_userupdate_name.requestFocus()
        }
        else if (u_email == "") {
            toast("이메일을 입력하세요 .")
            et_userupdate_email.requestFocus()
        }
        else if (u_phone == "") {
            toast("핸드폰을 입력하세요.")
            et_userupdate_phone.requestFocus()
        }
        else return true
        return false
    }

    //서버에 사용자 업데이트 요청
    /*fun putUserResponse(u_name: String, u_email: String, u_phone:String) {

        //id,password를 받아서 JSON객체로 만든다.
        var jsonObject = JSONObject()
        jsonObject.put("name", u_name)
        jsonObject.put("email", u_email)
        jsonObject.put("phone", u_phone)

        //networkService를 통해 실제로 통신을 요청
        //application/x-www-form-urlencoded 는 해더로 전송된다.
        //gsonObject 는 body로 전송된다.
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val putUserResponse: Call<PutUserResponse> =
            networkService.putUserResponse("application/json", gsonObject)

        putUserResponse.enqueue(object : Callback<PutUserResponse> {
            override fun onFailure(call: Call<PutUserResponse>, t: Throwable) {
                Log.e("update user failed",t.toString())
            }

            override fun onResponse(call: Call<PutUserResponse>, response: Response<PutUserResponse>) {
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
