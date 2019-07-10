package com.example.goldenticket.Activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goldenticket.Adapter.CardDetailRVAdapter
import com.example.goldenticket.Adapter.KeepStageRVAdapter
import com.example.goldenticket.Data.ContentDetailData
import com.example.goldenticket.Data.KeepShowData
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.Get.GetCardDetailResponse
import com.example.goldenticket.Network.Get.GetKeepShowResponse
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.etc.RecyclerViewDecoration
import kotlinx.android.synthetic.main.activity_keep.*
import kotlinx.android.synthetic.main.activity_month_contents.*
import kotlinx.android.synthetic.main.toolbar_drawer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Boolean



class KeepActivity : AppCompatActivity() {

    lateinit var keepRecyclerViewAdapter: KeepStageRVAdapter

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.goldenticket.R.layout.activity_keep)


        /** 툴바 타이틀 설정 **/
        tb_title.text = "관심있는 공연"

        /** 관심있는 공연 조회 **/
        var keepShowDataList: ArrayList<KeepShowData>

        // TODO: 토큰 쉐어드에서 가져와야 함
        val getKeepShowData = networkService.getKeepShow("application/json","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxMiwiZW1haWwiOiJlbWFpbDExMjRAbmF2ZXIuY29tIiwiaWF0IjoxNTYyNDg0MzUwfQ.6X8aYIp1rfeh9T43KBQSyz3hRIRRoo3M-W7CYQm4Pg8")
        getKeepShowData.enqueue(object: Callback<GetKeepShowResponse> {
            override fun onFailure(call: Call<GetKeepShowResponse>, t: Throwable) {
                Log.e("Get CardDetail Failed: ",t.toString())
            }

            override fun onResponse(call: Call<GetKeepShowResponse>, response: Response<GetKeepShowResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){

                        Log.d("밍구",response.body()!!.data.toString())


                        keepShowDataList = response.body()!!.data

                        var keepStageRVAdapter: KeepStageRVAdapter = KeepStageRVAdapter(applicationContext,keepShowDataList)
                        rvKeepStage.adapter = keepStageRVAdapter
                        rvKeepStage.layoutManager = GridLayoutManager(applicationContext,2)
                        rvKeepStage.addItemDecoration(RecyclerViewDecoration())
                        rvKeepStage.setHasFixedSize(true)
                    }
                }
            }

        })


    }
}
