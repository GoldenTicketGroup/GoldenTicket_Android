package com.example.goldenticket.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var tagList: ArrayList<String> = ArrayList()
        tagList.add("공포"); tagList.add("블루스퀘어"); tagList.add("세종문화회관");
        tagList.add("판타지"); tagList.add("스릴러"); tagList.add("예술의전"); tagList.add("충무아트센터");
        tagList.add("로맨스"); tagList.add("뮤지컬"); tagList.add("대학로연극");

        setOnClickListener()
    }

    private fun setOnClickListener() {
        ibtn_search_pre.setOnClickListener {
            finish()
        }
        ibtn_search_submit.setOnClickListener() {
            //et_search_searchbar.text를 가져와서 dataList의 title, place...가 포함하는
        }
        tv_search_cancel.setOnClickListener {
            finish()
        }

        //추천 검색어 onclick 시, 검색기능 처리
    }
}
