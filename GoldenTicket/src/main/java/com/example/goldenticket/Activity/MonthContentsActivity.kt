package com.example.goldenticket.Activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenticket.Adapter.MonthContentsRVAdapter
import com.example.goldenticket.Data.MonthContentsData
import com.example.goldenticket.Data.MonthContentsDetailData
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_month_contents.*

class MonthContentsActivity : AppCompatActivity() {

    lateinit var monthContentsAdapter: MonthContentsRVAdapter
    lateinit var detailDataList: ArrayList<MonthContentsDetailData>

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

        detailDataList = ArrayList()
        detailDataList.add(
            MonthContentsDetailData("볼거리 많은 대극장 뮤지컬","<안나 카레니나>","https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwj7sZqL45HjAhUKVLwKHT6fBf4QjRx6BAgBEAU&url=https%3A%2F%2Fmypetlife.co.kr%2F10333%2F&psig=AOvVaw2DY9w_UJObZrGOAhL-ZsDf&ust=1562003555694345",
                "지난 2018년 국내 첫선을 보였던 뮤지컬 <안나 카레니나>가 돌아왔습니다. 톨스토이의 동명 소설을 원작으로 한 러시아 뮤지컬인데요. 사랑 없는 결혼생활을 이어나던 귀부인 안나가 젊고 매력적인 장교 브론스키와 사랑에 빠지면서 일어나는 일을 그렸습니다. LED 스크린을 활용한 연출과 화려한 조명, 스케이트 장면을 비롯한 격정적인 안무 등, 기존 브로드웨이 뮤지컬과는 다른 러시아 뮤지컬만의 독특한 매력을 느껴보고 싶은분께 추천합니다.",
                1))

        detailDataList.add(
            MonthContentsDetailData("깊이 있는 주제의","<킬미 나우>","https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwj7sZqL45HjAhUKVLwKHT6fBf4QjRx6BAgBEAU&url=https%3A%2F%2Fmypetlife.co.kr%2F10333%2F&psig=AOvVaw2DY9w_UJObZrGOAhL-ZsDf&ust=1562003555694345",
                "지난 2018년 국내 첫선을 보였던 뮤지컬 <안나 카레니나>가 돌아왔습니다. 톨스토이의 동명 소설을 원작으로 한 러시아 뮤지컬인데요. 사랑 없는 결혼생활을 이어나던 귀부인 안나가 젊고 매력적인 장교 브론스키와 사랑에 빠지면서 일어나는 일을 그렸습니다. LED 스크린을 활용한 연출과 화려한 조명, 스케이트 장면을 비롯한 격정적인 안무 등, 기존 브로드웨이 뮤지컬과는 다른 러시아 뮤지컬만의 독특한 매력을 느껴보고 싶은분께 추천합니다.",
                2))

        var dataList: ArrayList<MonthContentsData> = ArrayList()
        dataList.add(
            MonthContentsData(
                "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwj7sZqL45HjAhUKVLwKHT6fBf4QjRx6BAgBEAU&url=https%3A%2F%2Fmypetlife.co.kr%2F10333%2F&psig=AOvVaw2DY9w_UJObZrGOAhL-ZsDf&ust=1562003555694345",
                "6월 연휴 뭐 할지 못 정했다면,\n" +
                        "공연장 나들이 어때요?",
                "공휴일 많았던 가정의 달, 5월이 끝나간다고 아쉬워하지 마세요. 6월에도 현충일을 끼고 ‘징검다리 연휴’가 있으니까요. 짧은 연휴지만 알차게 보내고 싶다면 공연장 나들이는 어떨까요? 6월에 보기 좋은 6편의 공연을 소개합니다.",
                detailDataList))

        tvMonthContentsTitle.text = dataList[0].contentsTitle
        tvMonthContentsContent.text = dataList[0].contentsContent

        monthContentsAdapter = MonthContentsRVAdapter(applicationContext, detailDataList)
        rvMonthContents.adapter = monthContentsAdapter
        rvMonthContents.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        rvMonthContents.setHasFixedSize(true)
    }
}
