package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    //이메일 형식 정규화
    val VALID_EMAIL_ADDRESS_REGEX: Pattern =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
    // 비밀번호 4자리 ~ 16자리까지 가능
    val VALID_PASSWOLD_REGEX_ALPHA_NUM: Pattern = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //포커스가 되거나 문자가 있을 때 밑줄이 활성화
        isDataVaild()

        //회원가입 버튼 이벤트
        //데이터가 옳바로 입력되었는지 확인한다.( 이메일형식, 비밀번호 형식)
        //2개의 비밀번호 입력이 일치하는지 확인한다.
        //true이면 서버에 회원정보가 저장이 된다.
        //저장이 완료되면 로그인 창으로 넘어간다.
        btn_signactivity_sign.setOnClickListener {
            val signup_u_email: String = et_signupactivity_email.text.toString()
            val signup_u_pw: String = et_signupactivity_pw.text.toString()
            val signup_u_pw2: String = et_signupactivity_pw2.text.toString()
            val signup_u_name:String = et_signupactivity_name.text.toString()
            val signup_phone: String = et_signupactivity_phone.text.toString()

            //true인 경우 서버에 회원정보를 저장한다.
            if(isUserInfoValid(signup_u_name,signup_phone,signup_u_email, signup_u_pw, signup_u_pw2)){
                // TODO : 서버와의 연결
                postSignupResponse(signup_u_email, signup_u_pw, signup_u_name,signup_phone)
            }


        }

        //뒤로가기 이미지를 입력하였을 때 로그인 창으로 넘어감
        iv_signactivity_back.setOnClickListener {
            startActivity<LoginActivity>()
            finish()
        }


        //이메일로 로그인 버튼을 눌렀을 때 로그인 화면으로 넘어감
        tv_signactivity_login.setOnClickListener {
            startActivity<LoginActivity>()
            finish()
        }
    }

    //빈 문자열인지 확인 -> 형식이 맞는지 확인
    private fun isUserInfoValid(u_name:String, u_phone:String, u_email: String, u_pw: String, u_pw2: String): Boolean {
        if (u_name == "") {
            toast("이름을 입력하세요 .")
            et_signupactivity_name.requestFocus()
        }
        else if (u_email == "") {
            toast("이메일을 입력하세요 .")
            et_signupactivity_email.requestFocus()
        }
        else if (u_phone == "") {
            toast("핸드폰을 입력하세요.")
            et_signupactivity_phone.requestFocus()
        }
        else if (u_pw == "") {
            toast("비밀번호를 입력하세요.")
            et_signupactivity_pw.requestFocus()
        }
        else if (u_pw2 == "") {
            toast("비밀번호 확인을 입력하세요.")
            et_signupactivity_pw2.requestFocus()
        }
        else if (!validateEmail(u_email)) {
            toast("이메일 형식이 아닙니다.")
            et_signupactivity_email.requestFocus()
        }
        else if (!validatePassword(u_pw)) {
            toast("패스워드 형식이 아닙니다.")
            et_signupactivity_pw.requestFocus()
        }
        else if (u_pw != u_pw2) {
            toast("두 패스워드가 다릅니다.")
            et_signupactivity_pw2.requestFocus()
        }
        else
            toast("회원가입이 되었습니다.")
            return true
        return false
    }

    //아이디와 패스워드가 포커스가 되면 밑줄
    //아이디와 패스워드에 문자가 있으면 밑줄
    private fun isDataVaild() {
        et_signupactivity_name.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_signupactivity_name.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
        et_signupactivity_email.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_signupactivity_email.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
        et_signupactivity_phone.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_signupactivity_phone.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
        et_signupactivity_pw.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_signupactivity_pw.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
        et_signupactivity_pw2.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_signupactivity_pw2.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }

    }

    //패스워드 형식인지 유효성 검사
    fun validatePassword(pwStr: String): Boolean {
        val matcher: Matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr)
        return matcher.matches()
    }

    //이메일 형식인지 유효성 검사
    fun validateEmail(emailStr: String): Boolean {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
        return matcher.find()
    }

    //서버에 회원 가입 정보를 저장한다.
    /*private fun postSignupResponse(u_id: String, u_pw: String, u_name: String , u_phone: String) {

        //id,password,name 데이터를 받아서 JSON 객체로 만든다.
        var jsonObject = JSONObject()
        jsonObject.put("id", u_id)
        jsonObject.put("password", u_pw)
        jsonObject.put("name", u_name)
        jsonObject.put("phone", u_phone)

        //gsonObject는 body로 들어간다.
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postSignupResponse: Call<PostSignupResponse> =
            networkService.postSignupResponse("application/json", gsonObject)
        postSignupResponse.enqueue(object : Callback<PostSignupResponse> {
            override fun onFailure(call: Call<PostSignupResponse>, t: Throwable) {
                Log.e("Login failed", t.toString())
            }

            override fun onResponse(call: Call<PostSignupResponse>, response: Response<PostSignupResponse>) {
                if (response.isSuccessful) {
                    toast(response.body()!!.message)
                    if (response.body()!!.status == 201) {
                        //Resquest Signup
                        val simpleDateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                        val e_time = simpleDateFormat.format(Date())

                        val intent = Intent()
                        intent.putExtra("end_time", e_time)
                        setResult(Activity.RESULT_OK, intent)

                        finish()
                    }
                }
            }

        })
    }*/


}
