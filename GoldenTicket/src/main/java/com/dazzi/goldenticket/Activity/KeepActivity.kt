package com.dazzi.goldenticket.Activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.dazzi.goldenticket.Adapter.KeepStageRVAdapter
import com.dazzi.goldenticket.DB.SharedPreferenceController
import com.dazzi.goldenticket.Data.KeepShowData
import com.dazzi.goldenticket.Network.ApplicationController
import com.dazzi.goldenticket.Network.Get.GetKeepShowResponse
import com.dazzi.goldenticket.Network.NetworkService
import com.dazzi.goldenticket.etc.RecyclerViewDecoration
import kotlinx.android.synthetic.main.activity_keep.*
import kotlinx.android.synthetic.main.toolbar_drawer.*
import kotlinx.android.synthetic.main.toolbar_drawer.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class KeepActivity : AppCompatActivity() {

    lateinit var keepRecyclerViewAdapter: KeepStageRVAdapter

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.dazzi.goldenticket.R.layout.activity_keep)


        /** 툴바 타이틀 설정 **/
        tb_title.text = "관심있는 공연"
        tb_keep.iv_back.onClick {
            finish()
        }

        /** 관심있는 공연 조회 **/
        var keepShowDataList: ArrayList<KeepShowData>

        val getKeepShowData = networkService.getKeepShow("application/json",SharedPreferenceController.getUserToken(this))
        getKeepShowData.enqueue(object: Callback<GetKeepShowResponse> {
            override fun onFailure(call: Call<GetKeepShowResponse>, t: Throwable) {
                Log.e("Get CardDetail Failed: ",t.toString())
            }

            override fun onResponse(call: Call<GetKeepShowResponse>, response: Response<GetKeepShowResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){

                        keepShowDataList = response.body()!!.data

                        var keepStageRVAdapter: KeepStageRVAdapter = KeepStageRVAdapter(applicationContext,keepShowDataList)
                        rvKeepStage.adapter = keepStageRVAdapter
                        rvKeepStage.layoutManager = GridLayoutManager(applicationContext,2)
                        rvKeepStage.addItemDecoration(RecyclerViewDecoration())
                        rvKeepStage.setHasFixedSize(true)

                        if(keepShowDataList.isEmpty()) {
                            keep_empty_image.visibility = View.VISIBLE
                            rvKeepStage.visibility = View.GONE
                        } else {
                            keep_empty_image.visibility = View.GONE
                            rvKeepStage.visibility = View.VISIBLE
                        }
                    }
                }
            }

        })


    }
}