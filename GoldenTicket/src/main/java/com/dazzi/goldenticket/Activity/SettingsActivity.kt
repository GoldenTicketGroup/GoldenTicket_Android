package com.dazzi.goldenticket.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.toolbar_drawer.*
import androidx.preference.SwitchPreferenceCompat
import com.dazzi.goldenticket.DB.SharedPreferenceController.clearUserToken
import com.dazzi.goldenticket.DB.SharedPreferenceController.getUserName
import com.dazzi.goldenticket.DB.SharedPreferenceController.getUserToken
import com.dazzi.goldenticket.Network.ApplicationController
import com.dazzi.goldenticket.Network.Delete.DeleteUserResponse
import com.dazzi.goldenticket.Network.NetworkService
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.dazzi.goldenticket.R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(
                com.dazzi.goldenticket.R.id.settings,
                SettingsFragment()
            )
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tb_title.text = "설정"

        iv_back.setOnClickListener {
            finish()
        }


    }

    class SettingsFragment : PreferenceFragmentCompat() {

        val networkService : NetworkService by lazy {
            ApplicationController.instance.networkService
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(com.dazzi.goldenticket.R.xml.root_preferences, rootKey)

            val switchKeepPreferences = findPreference<SwitchPreferenceCompat>("pref_keep")
            val switchLotteryPreferences = findPreference<SwitchPreferenceCompat>("pref_lottery")
            val switchMarketingPreferences = findPreference<SwitchPreferenceCompat>("pref_marketing")
            val userNamePreferences = findPreference<Preference>("pref_user_name")
            val logoutPreferences = findPreference<Preference>("pref_logout")
            val withdrawalPreferences = findPreference<Preference>("pref_withdrawal")


            //관심있는 공연 스위치 이벤트
            switchKeepPreferences!!.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    //toast("true")
                } else {
                }
                true
            }

            //당첨 결과 알림 스위치 이벤트
            switchLotteryPreferences!!.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    //toast("true")
                } else {
                    //toast("false")
                }
                true
            }

            //이벤트 및 마케팅 활용 동의 이벤트
            switchMarketingPreferences!!.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    //toast("true")
                } else {
                }
                true
            }

            userNamePreferences!!.title = getUserName(ctx)

            //TODO: 로그아웃 클릭하면 다이얼로그 화면이 나온다.
            logoutPreferences!!.setOnPreferenceClickListener {
                alert(title = "로그아웃", message = "로그아웃을 하시겠습니까?"){
                    positiveButton("Yes"){
                        clearUserToken(ctx)
                        toast("로그아웃을 하였습니다.")
                        startActivity<LoginActivity>()

                    }
                    negativeButton("No"){
                        toast("로그아웃을 취소하였습니다.")
                    }
                }.show()
                true
            }

            //TODO: 회원탈퇴 클릭하면 다이얼로그 화면이 나온다.
            withdrawalPreferences!!.setOnPreferenceClickListener {
                alert(title = "회원탈퇴", message = "탈퇴를 하시겠습니까?"){
                    positiveButton("Yes"){
                        deleteUserResponse(getUserToken(ctx))
                        toast("탈퇴가 되었습니다.")
                    }
                    negativeButton("No"){
                        toast("탈퇴를 취소하였습니다.")
                    }
                }.show()
                true
            }
        }
        fun deleteUserResponse(token : String) {

            val deleteUserResponse: Call<DeleteUserResponse> =
                networkService.deleteUserResponse("application/json", token)

            deleteUserResponse.enqueue(object : Callback<DeleteUserResponse> {
                override fun onFailure(call: Call<DeleteUserResponse>, t: Throwable) {
                    Log.e("login failed",t.toString())
                    toast("탈퇴가 취소 되었습니다.")
                }

                override fun onResponse(call: Call<DeleteUserResponse>, response: Response<DeleteUserResponse>) {

                    if(response.isSuccessful){
                        if(response.body()!!.status == 200){
                            //Request Login
                            toast("회원탈퇴가 되었습니다.")
                            Log.d("login","회원탈퇴가 되었습니다.")
                            clearUserToken(ctx)

                            startActivity<LoginActivity>()

                        }
                    }
                }
            })
        }

    }

}