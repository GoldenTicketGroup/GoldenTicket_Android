package com.example.goldenticket.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.goldenticket.Data.LotteryConfirmData
import com.example.goldenticket.R
import kotlinx.android.synthetic.main.vp_lottery_confirm.view.*

class LotteryConfirmAdapter(var inflater: LayoutInflater, val dataList: ArrayList<LotteryConfirmData>) : PagerAdapter(){

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view: View? = null
        view = inflater.inflate(R.layout.vp_lottery_confirm, null)
        view!!.tvShowName.text = dataList[position].title // TODO: 서버에서 받은 텍스트로 바꾸기
        view!!.tvCheckText.visibility= View.VISIBLE
        view!!.tvTime.text = dataList[position].time
        container.addView(view)

        return view
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0==p1
    }

    override fun getCount(): Int {
        return dataList.size; // TODO: 서버에서 전달받는 데이터 개수로 바꾸기
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}