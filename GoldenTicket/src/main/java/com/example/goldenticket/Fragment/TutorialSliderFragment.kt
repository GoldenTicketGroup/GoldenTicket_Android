package com.example.goldenticket.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.activity_tutorial.*
import kotlinx.android.synthetic.main.fragment_tutorial_slider.*


class TutorialSliderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tutorial_slider, container, false)
    }

    //Activity 생성된 후, Activity에 View가 올라간 뒤에 호출되는 생명주기
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tutirial_img:Int = arguments!!.getInt("tutorial_img")
        Glide.with(this)
            .load(tutirial_img)
            .into(iv_tutorial_slider)
    }

}
