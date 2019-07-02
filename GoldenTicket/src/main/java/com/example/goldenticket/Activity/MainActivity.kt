package com.example.goldenticket.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.goldenticket.*
import com.example.goldenticket.Adapter.LotteryConfirmAdapter
import com.example.goldenticket.Adapter.MainContentsAdapter
import com.example.goldenticket.Adapter.MonthContentsRVAdapter
import com.example.goldenticket.Adapter.ShowMainRecyclerViewAdapter
import com.example.goldenticket.Data.LotteryConfirmData
import com.example.goldenticket.Data.MainContentsData
import com.example.goldenticket.Data.MonthContentsData
import com.example.goldenticket.Data.ShowData
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick


class MainActivity : AppCompatActivity() {

    val data = arrayListOf<ShowData>(
        ShowData(1, "캣츠", "혜화 소극장", "17:00 ~ 19:00"),
        ShowData(2, "뮤지컬 벤허", "혜화 소극장", "17:00 ~ 19:00"),
        ShowData(3, "마틸다", "혜화 소극장", "17:00 ~ 19:00")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** 당첨확인 뷰페이저 부분 **/
        configureLotteryConfirmVP()

        /** 하단 월별 콘텐츠 리사이클러뷰 부분 **/
        configureMainContentsRV()


        rv_product.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        rv_product.adapter = ShowMainRecyclerViewAdapter(this, data)
        LinearSnapHelper().attachToRecyclerView(rv_product)

        iv_main_side.setOnClickListener {
            dl.openDrawer(ll_drawer)
        }
        iv_drawer_close.setOnClickListener {
            dl.closeDrawers()
        }
       drawerSelected()
    }

    private fun configureLotteryConfirmVP(){
        var lotteryConfirmDataList = ArrayList<LotteryConfirmData>() // TODO: 서버에게 받을 응모한 공연 데이터 리스트
        lotteryConfirmDataList.add(
            LotteryConfirmData(
                "골든 티켓 만세", "11 : 11 : 11")
        )
        lotteryConfirmDataList.add(
            LotteryConfirmData(
                "귀여운 골티", "22 : 22 : 22"))
        lotteryConfirmDataList.add(
            LotteryConfirmData(
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

    private fun btnVisibilityCheck(position: Int, dataList: ArrayList<LotteryConfirmData>){ // TODO: 당첨확인 버튼 유무 체크
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
            val intent = Intent(this, UserUpdateActivity::class.java)
            startActivity(intent)
        }
        rl_win.setOnClickListener {
            val intent = Intent(this, MyLotteryActivity::class.java)
            startActivity(intent)
        }
        rl_like.setOnClickListener {
            val intent = Intent(this, KeepActivity::class.java)
            startActivity(intent)
        }
        rl_notice.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)
        }
        rl_settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        rl_FAQ.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }
    }
}
