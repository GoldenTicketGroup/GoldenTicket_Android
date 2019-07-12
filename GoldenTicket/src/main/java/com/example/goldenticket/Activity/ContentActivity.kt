package com.example.goldenticket.Activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goldenticket.Adapter.CardDetailRVAdapter
import com.example.goldenticket.Data.CardDetailData
import com.example.goldenticket.Data.ContentDetailData
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.Network.Get.GetCardDetailResponse
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_month_contents.*
import kotlinx.android.synthetic.main.close_custom_actionbar.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month_contents)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        /** 카드 취소 버튼  **/
        btnClose.onClick {
            finish()
        }


        /** 카드 상세 조회 **/
        var contentDetailDataList: ArrayList<ContentDetailData>

        val getCardDetail = networkService.getCardDetail("application/json",intent.getIntExtra("idx", 2))
        getCardDetail.enqueue(object: Callback<GetCardDetailResponse>{
            override fun onFailure(call: Call<GetCardDetailResponse>, t: Throwable) {
                Log.e("Get CardDetail Failed: ",t.toString())
            }

            override fun onResponse(call: Call<GetCardDetailResponse>, response: Response<GetCardDetailResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        Glide.with(applicationContext)
                            .load(response.body()!!.data.image_url)
                            .into(ivCardImage)

                        var tempContentArray: List<String> = response.body()!!.data.title.split("/r")
                        var tempString: String = ""
                        for(i in 0..tempContentArray.size-1){
                            tempString += tempContentArray[i]+" "
                        }
                        tvCardTitle.text=tempString

                        tvCardContent.text=response.body()!!.data.card_content

                        contentDetailDataList = response.body()!!.data.content

                        var cardDetailRVAdapter: CardDetailRVAdapter = CardDetailRVAdapter(applicationContext,contentDetailDataList)
                        rvContent.adapter = cardDetailRVAdapter
                        rvContent.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
                        rvContent.setHasFixedSize(true)
                    }
                }
            }

        })

    }
}