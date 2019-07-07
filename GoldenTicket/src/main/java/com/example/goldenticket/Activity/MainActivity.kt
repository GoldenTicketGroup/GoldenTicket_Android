package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenticket.*
import com.example.goldenticket.Adapter.LotteryConfirmAdapter
import com.example.goldenticket.Adapter.CardListAdapter
import com.example.goldenticket.Adapter.ShowMainRecyclerViewAdapter
import com.example.goldenticket.Data.*
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import android.graphics.Rect
import android.util.Log
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.NetworkService
import com.example.goldenticket.Network.Post.GetCardListResponse
import kotlinx.android.synthetic.main.toolbar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** 상단 티켓 아이콘 **/
        iv_main_ticket.onClick {
            // status: 1 = 응모 전, 2 = 결제 전, 3 = 결제 완료
            var status = 1
            when(status) {
                1 -> {
                    startActivity<MyLotteryNothingActivity>()
                }
                2 -> {
                    startActivity<MyLotteryPaymentActivity>()
                }
                3 -> {
                    startActivity<MyLotteryDetailActivity>()
                }
            }
        }

        /** 상단 공연 포스터 리사이클러뷰 부분 **/
        configureShowRV()

        /** 당첨확인 뷰페이저 부분 **/
        configureLotteryConfirmVP()

        /** 하단 검색 창 클릭 부분 **/
        tvSearch.onClick {
            startActivity<SearchActivity>()
        }

        /** 하단 월별 콘텐츠 리사이클러뷰 부분 **/
        configureMainContentsRV()

        iv_main_side.setOnClickListener {
            dl.openDrawer(ll_drawer)
        }
        iv_drawer_close.setOnClickListener {
            dl.closeDrawers()
        }

        /** 드로워 부분 **/
       drawerSelected()
    }

    private fun configureShowRV(){
        val data = arrayListOf<ShowData>(
                ShowData(1, "캣츠", "혜화 소극장", "17:00 ~ 19:00"),
                ShowData(2, "뮤지컬 벤허", "혜화 소극장", "17:00 ~ 19:00"),
                ShowData(3, "마틸다", "혜화 소극장", "17:00 ~ 19:00")
        )

        rv_product.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        rv_product.adapter = ShowMainRecyclerViewAdapter(this, data)
        LinearSnapHelper().attachToRecyclerView(rv_product)
        rv_product.addItemDecoration(MarginItemDecoration(110, 90))
    }

    private fun configureLotteryConfirmVP(){
        var lotteryConfirmDataList = ArrayList<LotteryConfirmVPData>() // TODO: 서버에게 받을 응모한 공연 데이터 리스트
        lotteryConfirmDataList.add(
            LotteryConfirmVPData(
                "골든 티켓 만세", "11 : 11 : 11")
        )
        lotteryConfirmDataList.add(
            LotteryConfirmVPData(
                "귀여운 골티", "22 : 22 : 22"))


        btnVisibilityCheck(vpLotteryConfirm.currentItem,lotteryConfirmDataList)

        var lotteryConfirmAdapter = LotteryConfirmAdapter(layoutInflater,lotteryConfirmDataList)
        vpLotteryConfirm.adapter = lotteryConfirmAdapter

        ibtnNextLeft.onClick {
            var position = vpLotteryConfirm.currentItem
            vpLotteryConfirm.setCurrentItem(position-1,true)
            btnVisibilityCheck(vpLotteryConfirm.currentItem,lotteryConfirmDataList)
        }
        ibtnNextRight.onClick {
            var position = vpLotteryConfirm.currentItem
            vpLotteryConfirm.setCurrentItem(position+1,true)
            btnVisibilityCheck(vpLotteryConfirm.currentItem,lotteryConfirmDataList)
        }
    }
    private fun configureMainContentsRV(){

        lateinit var cardListAdapter: CardListAdapter


        val getCardList = networkService.getCardList("application/json")
        getCardList.enqueue(object : Callback<GetCardListResponse>{
            override fun onFailure(call: Call<GetCardListResponse>, t: Throwable) {
                Log.e("Get Card List Failed: ",t.toString())
            }

            override fun onResponse(call: Call<GetCardListResponse>, response: Response<GetCardListResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        var cardListDataList: ArrayList<CardListData> = response.body()!!.data!!

                        cardListAdapter = CardListAdapter(applicationContext, cardListDataList)
                        rvContents.adapter = cardListAdapter
                        rvContents.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
                        rvContents.setHasFixedSize(true)

                        Log.d("TEST",cardListDataList.toString())
                        Log.d("TEST",cardListDataList.get(0).category)
                    }
                }
            }

        })


    }

    private fun btnVisibilityCheck(position: Int, dataList: ArrayList<LotteryConfirmVPData>){ // TODO: 당첨확인 버튼 유무 체크
        if (position==dataList.size-1) {
            ibtnNextRight.visibility= View.INVISIBLE
            ibtnNextLeft.visibility= View.VISIBLE
        }
        else if (position==0){
            ibtnNextLeft.visibility= View.INVISIBLE
            ibtnNextRight.visibility= View.VISIBLE
        }
        else{
            ibtnNextLeft.visibility= View.VISIBLE
            ibtnNextRight.visibility= View.VISIBLE
        }
    }

    private fun drawerSelected() {
        ll_user_update.setOnClickListener {
            startActivity<UserUpdateActivity>()
        }
        rl_win.setOnClickListener {
            startActivity<MyLotteryActivity>()
        }
        rl_like.setOnClickListener {
            startActivity<KeepActivity>()
        }
        rl_notice.setOnClickListener {
            startActivity<NoticeActivity>()
        }
        rl_settings.setOnClickListener {
            startActivity<SettingsActivity>()
        }
        rl_FAQ.setOnClickListener {
            startActivity<QuestionActivity>()
        }
    }

    /*리사이클러뷰 간격*/
    class MarginItemDecoration(private val first_space: Int, private val space: Int): androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                left = first_space
                }
                right = space
            }
        }
    }

}
