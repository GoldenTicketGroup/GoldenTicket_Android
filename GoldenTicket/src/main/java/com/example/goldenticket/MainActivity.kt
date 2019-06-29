package com.example.goldenticket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_main_side.setOnClickListener {
            dl.openDrawer(ll_drawer)
        }

        iv_drawer_close.setOnClickListener {
            dl.closeDrawers()
        }

        //dl.addDrawerListener(object.DrawerLayout.DrawerListener)
    }
}
