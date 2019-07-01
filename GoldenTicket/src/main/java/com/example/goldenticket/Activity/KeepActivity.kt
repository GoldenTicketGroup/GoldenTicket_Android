package com.example.goldenticket.Activity


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.goldenticket.Adapter.KeepStageRVAdapter
import com.example.goldenticket.Data.KeepStageData
import com.example.goldenticket.R
import com.example.goldenticket.etc.RecyclerViewDecoration
import kotlinx.android.synthetic.main.activity_keep.*
import kotlinx.android.synthetic.main.activity_keep.view.*
import java.lang.Boolean

class KeepActivity : AppCompatActivity() {

    lateinit var keepRecyclerViewAdapter: KeepStageRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keep)

        var dataList: ArrayList<KeepStageData> = ArrayList()
        dataList.add(
            KeepStageData(
                "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjGkfDw4pHjAhUB5rwKHYekBmsQjRx6BAgBEAU&url=http%3A%2F%2Fhangangah.com%2F%25EA%25B0%2595%25EC%2595%2584%25EC%25A7%2580-%25EC%2583%2581%25EC%258B%259D-4%2F&psig=AOvVaw2DY9w_UJObZrGOAhL-ZsDf&ust=1562003555694345",
                Boolean.TRUE
            ))
        dataList.add(
            KeepStageData(
                "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwj7sZqL45HjAhUKVLwKHT6fBf4QjRx6BAgBEAU&url=https%3A%2F%2Fmypetlife.co.kr%2F10333%2F&psig=AOvVaw2DY9w_UJObZrGOAhL-ZsDf&ust=1562003555694345",
                Boolean.FALSE
            ))


        keepRecyclerViewAdapter = KeepStageRVAdapter(applicationContext, dataList)
        rvKeepStage.adapter = keepRecyclerViewAdapter
        rvKeepStage.layoutManager = GridLayoutManager(applicationContext,2)
        rvKeepStage.addItemDecoration(RecyclerViewDecoration())
        rvKeepStage.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): kotlin.Boolean {
        val actionBar = supportActionBar

        actionBar!!.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(false) // 액션바 아이콘을 업 네비게이션 형태로 표시
        actionBar.setDisplayShowTitleEnabled(false) // 액션바에 표시되는 제목의 표시유무 설정
        actionBar.setDisplayShowHomeEnabled(false) // 홈 아이콘 숨김처리
        actionBar.elevation="0".toFloat()


        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val actionbar = inflater.inflate(R.layout.keep_custom_actionbar, null)

        actionBar.setCustomView(actionbar)

        //액션바 양쪽 공백 없애기
        val parent = actionbar.getParent() as androidx.appcompat.widget.Toolbar
        parent.setContentInsetsAbsolute(0, 0)

        return true
    }

}
