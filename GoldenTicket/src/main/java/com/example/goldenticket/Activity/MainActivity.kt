package com.example.goldenticket.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.goldenticket.*
import com.example.goldenticket.Adapter.ShowMainRecyclerViewAdapter
import com.example.goldenticket.Data.ShowData
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val data = arrayListOf<ShowData>(
        ShowData(1, "캣츠", "혜화 소극장", "17:00 ~ 19:00"),
        ShowData(2, "뮤지컬 벤허", "혜화 소극장", "17:00 ~ 19:00"),
        ShowData(3, "마틸다", "혜화 소극장", "17:00 ~ 19:00")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_product.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        rv_product.adapter = ShowMainRecyclerViewAdapter(this, data)
        LinearSnapHelper().attachToRecyclerView(rv_product);

        iv_main_side.setOnClickListener {
            dl.openDrawer(ll_drawer)
        }
        iv_drawer_close.setOnClickListener {
            dl.closeDrawers()
        }
       drawerSelected()
    }

    private fun drawerSelected() {
        ll_user_update.setOnClickListener {
            val intent = Intent(this, UserUpdateActivity::class.java)
            startActivity(intent)
        }
        tv_win_num.setOnClickListener {
            val intent = Intent(this, MyLotteryActivity::class.java)
            startActivity(intent)
        }
        iv_next_like.setOnClickListener {
            val intent = Intent(this, KeepActivity::class.java)
            startActivity(intent)
        }
        iv_next_notice.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)
        }
        iv_next_settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        iv_next_FAQ.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }
    }
}
