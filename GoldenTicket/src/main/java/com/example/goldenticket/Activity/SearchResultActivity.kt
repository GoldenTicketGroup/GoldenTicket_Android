package com.example.goldenticket.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenticket.Adapter.KeepStageRVAdapter
import com.example.goldenticket.Data.KeepStageData
import com.example.goldenticket.R
import com.example.goldenticket.etc.RecyclerViewDecoration
import kotlinx.android.synthetic.main.activity_search_result.*
import java.lang.Boolean

class SearchResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        var dataList: ArrayList<KeepStageData> = ArrayList()
        dataList.add(
            KeepStageData(
                "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjGkfDw4pHjAhUB5rwKHYekBmsQjRx6BAgBEAU&url=http%3A%2F%2Fhangangah.com%2F%25EA%25B0%2595%25EC%2595%2584%25EC%25A7%2580-%25EC%2583%2581%25EC%258B%259D-4%2F&psig=AOvVaw2DY9w_UJObZrGOAhL-ZsDf&ust=1562003555694345",
                Boolean.TRUE
            )
        )
        dataList.add(
            KeepStageData(
                "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwj7sZqL45HjAhUKVLwKHT6fBf4QjRx6BAgBEAU&url=https%3A%2F%2Fmypetlife.co.kr%2F10333%2F&psig=AOvVaw2DY9w_UJObZrGOAhL-ZsDf&ust=1562003555694345",
                Boolean.FALSE
            )
        )

        Log.e("SearchResultActivity::", "onCreate::" + intent.getStringExtra("search_tag"))
        et_searchresult_searchbar.setText(intent.getStringExtra("search_tag"))

        rv_search_results.adapter = KeepStageRVAdapter(applicationContext, dataList)
        rv_search_results.layoutManager = GridLayoutManager(applicationContext, 2)
        rv_search_results.addItemDecoration(RecyclerViewDecoration())
        rv_search_results.setHasFixedSize(true)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        ibtn_searchresult_pre.setOnClickListener {
            finish()
        }
    }
}
