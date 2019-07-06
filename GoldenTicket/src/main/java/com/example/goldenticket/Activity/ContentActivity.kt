package com.example.goldenticket.Activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.goldenticket.Adapter.CardListAdapter
import com.example.goldenticket.Adapter.ContentDetailRVAdapter
import com.example.goldenticket.Data.CardDetailData
import com.example.goldenticket.Data.CardListData
import com.example.goldenticket.Data.ContentDetailData
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.Network.Post.GetCardDetailResponse
import com.example.goldenticket.Network.Post.GetCardListResponse
import com.example.goldenticket.Network.Post.GetContentDetailResponse
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_month_contents.*
import kotlinx.android.synthetic.main.rv_month_contents_item.*
import org.jetbrains.anko.ctx
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
        ibtnCardFinish.onClick {
            finish()
        }

        /** 카드 상세 조회 (맨 위 RV 아닌 부분) **/
        val getCardDetail = networkService.getCardDetail("application/json",intent.getIntExtra("idx", 2))
        getCardDetail.enqueue(object: Callback<GetCardDetailResponse>{
            override fun onFailure(call: Call<GetCardDetailResponse>, t: Throwable) {
                Log.e("Get CardDetail Failed: ",t.toString())
            }

            override fun onResponse(call: Call<GetCardDetailResponse>, response: Response<GetCardDetailResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        Glide.with(applicationContext)
                            .load(response.body()!!.data!!.imageUrl)
                            .into(ivCardImage)

                        tvCardTitle.text = response.body()!!.data!!.title
                        tvCardContent.text = response.body()!!.data!!.content
                    }
                }
            }

        })

        /** 콘텐츠 상세 조회 (RV 부분) **/
        var contentDetailDataList: ArrayList<ContentDetailData> = ArrayList()

        val getContentDetail = networkService.getContentDetail("application/json",intent.getIntExtra("idx", 2))
        getContentDetail.enqueue(object: Callback<GetContentDetailResponse>{
            override fun onFailure(call: Call<GetContentDetailResponse>, t: Throwable) {
                Log.e("Get ContentDetail Fail:",t.toString())
            }

            override fun onResponse(call: Call<GetContentDetailResponse>, response: Response<GetContentDetailResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        contentDetailDataList = response.body()!!.data

                        var contentDetailRVAdapter: ContentDetailRVAdapter = ContentDetailRVAdapter(applicationContext,contentDetailDataList)
                        rvContent.adapter = contentDetailRVAdapter
                        rvContent.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
                        rvContent.setHasFixedSize(true)
                    }
                }
            }

        })

    }
}
