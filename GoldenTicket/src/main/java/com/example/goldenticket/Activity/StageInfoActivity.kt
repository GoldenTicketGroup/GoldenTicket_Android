package com.example.goldenticket.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.goldenticket.Adapter.StageInfoActorsRVAdapter
import com.example.goldenticket.Adapter.StageInfoImgsRVAdpater
import com.example.goldenticket.DB.SharedPreferenceController
import com.example.goldenticket.Data.StageInfoActorsData
import com.example.goldenticket.Data.StageInfoData
import com.example.goldenticket.Data.StageInfoImgsData
import com.example.goldenticket.Data.StageInfoSchedulesData
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.Delete.DeleteShowLikeResponse
import com.example.goldenticket.Network.Get.GetStageInfoResponse
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.Network.Post.PostLotteryResponse
import com.example.goldenticket.Network.Post.PostShowLikeResponse
import com.example.goldenticket.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.JsonObject
import com.google.gson.JsonParser

import kotlinx.android.synthetic.main.activity_stage_info.*
import kotlinx.android.synthetic.main.fragment_stage_info_entry_dialog.*
import kotlinx.android.synthetic.main.toolbar_stage_info.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StageInfoActivity : AppCompatActivity() {

    /*val jsonObject = JSONObject()*/

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    //lateinit var stageInfoData: StageInfoData
    //lateinit var stageInfoLikeData: StageInfoLikeData

    var actors: ArrayList<StageInfoActorsData> = ArrayList()
    var imgs: ArrayList<StageInfoImgsData> = ArrayList()
    var schedules: ArrayList<StageInfoSchedulesData> = ArrayList()
    var times: MutableMap<String, Int> = mutableMapOf()

    var show_idx: Int = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
        }*/


        // Action Bar Custom
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(com.example.goldenticket.R.layout.activity_stage_info)

        getStageInfoResponse()

        ibtn_stageinfo_like.isSelected = false // TODO: 서버에게 받아와서 수정하기

        setOnClickListener()
    }


    private fun setOnClickListener() {
        ibtn_stageinfo_pre.setOnClickListener {
            finish()
        }
        ibtn_stageinfo_like.setOnClickListener {
            when (ibtn_stageinfo_like.isSelected) {
                true -> {
                    //취소통신
                    var jsonObject = JSONObject()
                    jsonObject.put("show_idx", show_idx)
                    val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
                    var token = SharedPreferenceController.getUserToken(this)
                    // TODO: user token 쉐어드에서 가져와야함
                    val deleteShowLike = networkService.deleteShowLike("application/json", token, gsonObject)
                    deleteShowLike.enqueue(object : Callback<DeleteShowLikeResponse> {
                        override fun onFailure(call: Call<DeleteShowLikeResponse>, t: Throwable) {
                            Log.e("Delete ShowLike Failed:", t.toString())
                        }

                        override fun onResponse(
                            call: Call<DeleteShowLikeResponse>,
                            response: Response<DeleteShowLikeResponse>
                        ) {
                            if (response.isSuccessful) {
                                if (response.body()!!.status == 200) {
                                    ibtn_stageinfo_like.isSelected = false
                                }
                            }
                        }

                    })
                }
                false -> {
                    //조하효통신
                    var jsonObject = JSONObject()
                    jsonObject.put("show_idx", show_idx)
                    val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
                    var token = SharedPreferenceController.getUserToken(this)
                    // TODO: user token 쉐어드에서 가져와야함
                    val postShowLike = networkService.postShowLike("application/json", token, gsonObject)
                    postShowLike.enqueue(object : Callback<PostShowLikeResponse> {
                        override fun onFailure(call: Call<PostShowLikeResponse>, t: Throwable) {
                            Log.e("Post ShowLike Failed:", t.toString())
                        }

                        override fun onResponse(
                            call: Call<PostShowLikeResponse>,
                            response: Response<PostShowLikeResponse>
                        ) {
                            if (response.isSuccessful) {
                                if (response.body()!!.status == 200) {
                                    ibtn_stageinfo_like.isSelected = true

                                }
                            }
                        }

                    })

                }
            }
        }

        //위로 화살표를 눌렀을 때
        btn_stageinfo_activate_dl.setOnClickListener {

            val behavior = BottomSheetBehavior.from(ll_stageinfo_bottom_sheet)
            behavior.setPeekHeight(1000) //정확한 height단위 전혀모르겠음, dp와 float라

            btn_stageinfo_activate_dl.visibility = View.GONE
            btn_stageinfo_inactivate_dl.visibility = View.VISIBLE
            rl_stageinfo_select_time_spinner.visibility = View.VISIBLE
            btn_stageinfo_entry.visibility = View.GONE
            btn_stageinfo_submit.visibility = View.VISIBLE
        }
        btn_stageinfo_inactivate_dl.setOnClickListener {
            btn_stageinfo_activate_dl.visibility = View.VISIBLE
            btn_stageinfo_inactivate_dl.visibility = View.GONE
            rl_stageinfo_select_time_spinner.visibility = View.GONE
            btn_stageinfo_entry.visibility = View.VISIBLE
            btn_stageinfo_submit.visibility = View.GONE
        }
        btn_stageinfo_entry.setOnClickListener {
            btn_stageinfo_activate_dl.visibility = View.GONE
            btn_stageinfo_inactivate_dl.visibility = View.VISIBLE
            rl_stageinfo_select_time_spinner.visibility = View.VISIBLE
            btn_stageinfo_entry.visibility = View.GONE
            btn_stageinfo_submit.visibility = View.VISIBLE

        }
        btn_stageinfo_submit.setOnClickListener {
            btn_stageinfo_activate_dl.visibility = View.VISIBLE
            btn_stageinfo_inactivate_dl.visibility = View.GONE
            rl_stageinfo_select_time_spinner.visibility = View.GONE
            btn_stageinfo_entry.visibility = View.VISIBLE
            btn_stageinfo_submit.visibility = View.GONE

            /*jsonObject.put("schedule_idx", times.get(spn_stageinfo_select_time.selectedItem.toString()))*/

            val intent = Intent(this, LotteryNoticeActivity::class.java)
            intent.putExtra("schedule_idx", times.get(spn_stageinfo_select_time.selectedItem.toString()))
            startActivity(intent)

            /*postLotteryResponse()*/
        }
    }

    private fun setRecyclerView() {

        Log.e("StageInfoActivity::", "getStageInfoResponse::setRecyclerView:: " + actors.toString())
        rv_stageinfo_actors.adapter = StageInfoActorsRVAdapter(this@StageInfoActivity, actors)
        rv_stageinfo_actors.layoutManager =
            LinearLayoutManager(this@StageInfoActivity, LinearLayoutManager.HORIZONTAL, false)

        Log.e("StageInfoActivity::", "getStageInfoResponse::setRecyclerView:: " + imgs.toString())
        rv_stageinfo_imgs.adapter = StageInfoImgsRVAdpater(this, imgs)
        rv_stageinfo_imgs.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }

    private fun setSpinner(times: MutableMap<String, Int>) {
        spn_stageinfo_select_time.adapter = ArrayAdapter(
            this,
            com.example.goldenticket.R.layout.spn_item_stageinfo_starttimes,
            times.keys.toTypedArray()
        )
        spn_stageinfo_select_time.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) { /*nothing*/
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) { /*nothing*/
            }
        }
    }

    private fun setBottomSheet() {

        /*val behavior = BottomSheetBehavior.from(ll_stageinfo_bottom_sheet)
        behavior.setPeekHeight(800)*/
    }

    private fun getStageInfoResponse() {
        var token = SharedPreferenceController.getUserToken(this)
        val getStageInfoResponse =
            networkService.getStageInfoResponse("application/json", token, show_idx)
        getStageInfoResponse.enqueue(object : Callback<GetStageInfoResponse> {

            override fun onFailure(call: Call<GetStageInfoResponse>, t: Throwable) {
                Log.e("StageInfoActivity:: ", "getStageInfoResponse:: Get_StageInfo_Failed")
                Log.e("StageInfoActivity:: ", t.toString())
            }

            override fun onResponse(call: Call<GetStageInfoResponse>, response: Response<GetStageInfoResponse>) {
                if (response.isSuccessful) {
                    Log.e(
                        "StageInfoActivity:: ",
                        "getStageInfoResponse:: Success:: " + response.body()!!.data.toString()
                    )
                    var tempData: StageInfoData = response.body()!!.data
                    if (tempData != null) {
                        Log.e(
                            "StageInfoActivity::",
                            "getStageInfoResponse::poster:: " + response.body()!!.data.image_url
                        )

                        show_idx = intent.getIntExtra("idx",1) // TODO: 이거 인덱스 디폴트 값 뭘로 해 ...?

                        Glide.with(this@StageInfoActivity)
                            .load(response.body()!!.data.image_url)
                            .into(iv_stageinfo_bg)
                        Glide.with(this@StageInfoActivity)
                            .load(response.body()!!.data.image_url)
                            .into(iv_stageinfo_poster)
                        tv_stageinfo_title.text = response.body()!!.data.name
                        tv_stageinfo_costprice.text = response.body()!!.data.original_price + "원"
                        tv_stageinfo_saleprice.text = response.body()!!.data.discount_price + "원"
                        tv_stageinfo_date.text = response.body()!!.data.duration
                        tv_stageinfo_location.text = response.body()!!.data.location

                        actors = response.body()!!.data.artist
                        Log.e("StageInfoActivity::", "getStageInfoResponse::actors:: " + actors.toString())
                        imgs = response.body()!!.data.poster
                        Log.e("StageInfoActivity::", "getStageInfoResponse::imgs:: " + imgs.toString())

                        setRecyclerView()

                        //0: 응모 불가, 1: 응모 가능
                        schedules = response.body()!!.data.schedule
                        for (schedule in schedules) {
                            if (schedule.draw_available == 1) {
                                times.put(schedule.time, schedule.schedule_idx)
                            }
                        }
                        if (times.size > 0) {
                            setBottomSheet()
                            setSpinner(times)
                        }
                    } else {
                        Glide.with(this@StageInfoActivity)
                            .load(
                                "data:image/jpeg;base64,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"
                            )
                            .into(iv_stageinfo_bg)
                    }
                } else {
                    Log.e("StageInfoActivity:: ", "getStageInfoResponse:: Fail")
                }
            }
        })
    }

    /*private fun postLotteryResponse() {
        val token = SharedPreferenceController.getUserToken(this)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postLotteryResponse: Call<PostLotteryResponse> = networkService.postLotteryResponse("application/json", token, gsonObject)
        postLotteryResponse.enqueue(object: Callback<PostLotteryResponse> {
            override fun onFailure(call: Call<PostLotteryResponse>, t: Throwable) {
                Log.e("StageInfoActivity::", "postLotteryResponse::Post_Lottery_Register_Fail")
            }

            override fun onResponse(call: Call<PostLotteryResponse>, response: Response<PostLotteryResponse>) {
                if (response.isSuccessful) {
                    Log.e("StageInfoActivity::", "postLotteryResponse::onResponse::Success::" + response.body()!!.message)
                    toast(response.body()!!.message)
                }
                else {
                    Log.e("StageInfoActivity::", "postLotteryResponse::onResponse::Fail::" + response.body()!!.message)
                    toast(response.body()!!.message)
                }
            }
        })
    }*/
}
