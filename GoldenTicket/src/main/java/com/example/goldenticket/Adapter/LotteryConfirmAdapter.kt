package com.example.goldenticket.Adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.goldenticket.Fragment.LotteryFirstTimerFragment
import com.example.goldenticket.Fragment.LotterySecondTimerFragment

class LotteryConfirmAdapter(fm : FragmentManager, private val num_fragment:Int) : FragmentStatePagerAdapter(fm){

    //Singleton Design Pattern: 기존에 생성되었던 객체를 재사용
    companion object{
        private var lotteryFirstTimerFragment : LotteryFirstTimerFragment? = null
        private var lotterySecondTimerFragment : LotterySecondTimerFragment? = null
    }

    @Synchronized
    fun getLotteryFirstTimerFragment(): LotteryFirstTimerFragment {
        if (lotteryFirstTimerFragment == null) lotteryFirstTimerFragment = LotteryFirstTimerFragment()
        return lotteryFirstTimerFragment!!
    }

    @Synchronized
    fun getLotterySecondTimerFragment(): LotterySecondTimerFragment {
        if (lotterySecondTimerFragment == null) lotterySecondTimerFragment = LotterySecondTimerFragment()
        return lotterySecondTimerFragment!!
    }



    override fun getItem(p0: Int): Fragment {
        return when(p0) {
            0 -> getLotteryFirstTimerFragment()
            1 -> getLotterySecondTimerFragment()
            else -> null
        }!!
    }

    override fun getCount(): Int {
        return num_fragment
    }
}






    /*override fun instantiateItem(container: ViewGroup, position: Int): Any {
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
    }*/
