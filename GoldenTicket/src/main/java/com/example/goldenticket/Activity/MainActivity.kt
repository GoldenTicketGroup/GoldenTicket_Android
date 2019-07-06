package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenticket.*
import com.example.goldenticket.Adapter.MainContentsAdapter
import com.example.goldenticket.Adapter.ShowMainRecyclerViewAdapter
import com.example.goldenticket.Data.*
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import android.graphics.Rect
import android.util.Log
import com.example.goldenticket.Adapter.LotteryConfirmAdapter
import com.example.goldenticket.DB.SharedPreferenceController
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.GET.GetMainPosterResponse
import com.example.goldenticket.Network.NetworkService
import kotlinx.android.synthetic.main.toolbar_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import retrofit2.Call
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {

    val networkService : NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var showMainRecyclerViewAdapter: ShowMainRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val u_name = SharedPreferenceController.getUserName(this)
        //tv_main_name.setText(u_name)
        //tv_profile_name.setText(u_name)


        /** 상단 공연 포스터 리사이클러뷰 부분 **/
        configureShowRV()

        /** 당첨확인 뷰페이저 부분 **/
        configureLotteryConfirmVP()

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
        var ShowDataList: ArrayList<ShowData> = ArrayList()

        showMainRecyclerViewAdapter = ShowMainRecyclerViewAdapter(this, ShowDataList)
        rv_product.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        rv_product.adapter = showMainRecyclerViewAdapter
        LinearSnapHelper().attachToRecyclerView(rv_product)
        rv_product.addItemDecoration(MarginItemDecoration(110, 90))

        getMainPosterResponse()

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
        lotteryConfirmDataList.add(
            LotteryConfirmVPData(
                "만만세 골티", "22 : 22 : 22"))
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
        var mainContentsDataList: ArrayList<MainContentsData> = ArrayList() // TODO: 서버에게 받을 월별 콘텐츠 리스트
        mainContentsDataList.add(
            MainContentsData(
                "이번 달 공연", "7월 연휴,\n공연장 나들이\n어때요?"))
        mainContentsDataList.add(
            MainContentsData(
                "이번 달 뮤지컬", "7월 추천,\n시원한\n뮤지컬!"))


        var mainContentsAdapter = MainContentsAdapter(this, mainContentsDataList)
        rvContents.adapter = mainContentsAdapter
        rvContents.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        rvContents.setHasFixedSize(true)
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

    private fun getMainPosterResponse(){
        val getMainPosterResponse = networkService.getMainPosterResponse(
            "application/json")
        getMainPosterResponse.enqueue(object: retrofit2.Callback<GetMainPosterResponse> {
            override fun onFailure(call: Call<GetMainPosterResponse>, t: Throwable) {
                Log.e("Main Poster List Fail", t.toString())
            }

            override fun onResponse(call: Call<GetMainPosterResponse>, response: Response<GetMainPosterResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        val tmp: ArrayList<ShowData> = response.body()!!.data!!
                        showMainRecyclerViewAdapter.dataList = tmp
                        showMainRecyclerViewAdapter.notifyDataSetChanged()

                        if(tmp.isEmpty()) {
                            rv_product.visibility = View.GONE
                            empty_view.visibility = View.VISIBLE
                        }
                        else {
                            rv_product.visibility = View.VISIBLE
                            empty_view.visibility = View.GONE
                        }
                    }
                }
            }
        })
    }

}
