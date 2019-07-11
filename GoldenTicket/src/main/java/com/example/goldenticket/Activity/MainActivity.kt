package com.example.goldenticket.Activity

import android.animation.TimeInterpolator
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenticket.*
import com.example.goldenticket.Adapter.LotteryConfirmAdapter
import com.example.goldenticket.Adapter.CardListAdapter
import com.example.goldenticket.Adapter.ShowMainRecyclerViewAdapter
import com.example.goldenticket.Data.*
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import android.graphics.Rect
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.AccelerateInterpolator
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import com.example.goldenticket.DB.SharedPreferenceController.getUserName
import com.example.goldenticket.DB.SharedPreferenceController.getUserToken
import com.example.goldenticket.Network.ApplicationController
import com.example.goldenticket.Network.Get.GetCardListResponse
import com.example.goldenticket.Network.Get.GetLotteryListResponse
import com.example.goldenticket.Network.Get.GetMainPosterResponse
import com.example.goldenticket.Network.Get.GetMyLotteryResponse
import com.example.goldenticket.Network.NetworkService
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.toolbar_main.*
import org.jetbrains.anko.find
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Types.NULL
import kotlin.collections.ArrayList


class MainActivity : BaseActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var showMainRecyclerViewAdapter: ShowMainRecyclerViewAdapter
    var temp_num_fragment: Int = 0
    val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("TOKENTEST: ", getUserToken(this))

        //progressON("Loading...")

        /** 상단 티켓 아이콘 **/
        iv_main_ticket.onClick {
            /*// status: 1 = 응모 전, 2 = 결제 전, 3 = 결제 완료
            var status = 1
            when (status) {
                1 -> {
                    startActivity<MyLotteryNothingActivity>()
                }
                2 -> {
                    startActivity<MyLotteryPaymentActivity>()
                }
                3 -> {
                    startActivity<MyLotteryDetailActivity>()
                }
            }*/
            startActivity<MyLotteryDetailActivity>()
        }
        val snapHelper = GravitySnapHelper(Gravity.START)
        snapHelper.attachToRecyclerView(rv_product)


        val u_name = getUserName(this)
        tv_main_name.text = u_name
        tv_profile_name.text = u_name


        /** 상단 공연 포스터 리사이클러뷰 부분 **/
        configureShowRV()

        /** 당첨확인 뷰페이저 부분 **/
        configureLotteryConfirmVP()

        /** 하단 검색 창 클릭 부분 **/
        tvSearch.onClick {
            startActivity<SearchActivity>()
        }

        /** 하단 월별 콘텐츠 리사이클러뷰 부분 **/
        configureMainContentsRV()

        iv_main_side.setOnClickListener {
            dl.openDrawer(ll_drawer)
        }
        iv_drawer_close.setOnClickListener {
            dl.closeDrawers()
        }

        /** 드로워 부분 **/
        drawerSelected()

        //첫 번째 타이머와 두 번째 타이머가 되었을 때 화살표 가시성 설정
        vpLotteryConfirm.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                btnVisibilityCheck(vpLotteryConfirm.currentItem)
                /*if (position == 1) {
                    ibtnNextRight.visibility = View.INVISIBLE
                    ibtnNextLeft.visibility = View.VISIBLE
                } else {
                    ibtnNextRight.visibility = View.VISIBLE
                    ibtnNextLeft.visibility = View.INVISIBLE
                }*/
            }

            override fun onPageSelected(position: Int) {
            }

        })

        getMyLotteryResponse()
    }

    override fun onStart() {
        super.onStart()
        val u_name = getUserName(this)
        tv_main_name.text = u_name
        tv_profile_name.text = u_name

        configureLotteryConfirmVP()
    }

    private fun configureShowRV() {

        showProgressDialog()

        var ShowDataList: ArrayList<ShowData> = ArrayList()
        showMainRecyclerViewAdapter = ShowMainRecyclerViewAdapter(this, ShowDataList)

        val linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
        rv_product.adapter = showMainRecyclerViewAdapter
        rv_product.setHasFixedSize(true)
        rv_product.layoutManager = linearLayoutManager
        rv_product.addItemDecoration(MarginItemDecoration(40, 0))


        //서버에서 상단 포스터 가져오기
        getMainPosterResponse()

        val snapHelper = GravitySnapHelper(Gravity.START)
        snapHelper.attachToRecyclerView(rv_product)

        //recyclerView의 초기 상태를 설정한다.
        //rv_product: 리사이클러뷰 id
        //item은 최상단태그로 cardView로 되어있다.
        /*rv_product.findViewHolderForAdapterPosition(0)?.let {
            handler.postDelayed({

                val viewHolderDefault = rv_product.findViewHolderForAdapterPosition(0)
                val eventparentDefault = viewHolderDefault?.itemView?.findViewById(R.id.cv_main_poster) as CardView
                eventparentDefault.animate().scaleX(0.95f).scaleY(0.95f).setDuration(200).start()

            }, 1000)
        }*/

        //스크롤이 되었을 때 아이템의 크기가 변화된다.
        rv_product.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    //스크롤을 하지 않은 상태
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val view: View? = snapHelper.findSnapView(linearLayoutManager)
                        val pos = linearLayoutManager.getPosition(view!!)

                        val viewHolder = rv_product.findViewHolderForAdapterPosition(pos)

                        val eventparent = viewHolder!!.itemView.findViewById(R.id.cv_main_poster) as CardView
                        eventparent.animate().scaleY(0.95f).scaleX(0.95f).setDuration(350)
                            .setInterpolator(AccelerateInterpolator() as TimeInterpolator?)
                            .start()

                        //스크롤 중인 상태
                    } else {

                        val view = snapHelper.findSnapView(linearLayoutManager)
                        val pos = linearLayoutManager.getPosition(view!!)

                        val viewHolder = rv_product.findViewHolderForAdapterPosition(pos)

                        val eventparent = viewHolder!!.itemView.findViewById(R.id.cv_main_poster) as CardView
                        eventparent.animate().scaleY(0.85f).scaleX(0.85f).setDuration(350)
                            .setInterpolator(AccelerateInterpolator()).start()

                    }
                }
            })
    }

    private fun configureLotteryConfirmVP() {

        var lotteryConfirmAdapter: LotteryConfirmAdapter

        val getMainLotteryListResponse = networkService.getLotteryListResponse(
            "application/json", getUserToken(this))
        getMainLotteryListResponse.enqueue(object : retrofit2.Callback<GetLotteryListResponse> {
            override fun onFailure(call: Call<GetLotteryListResponse>, t: Throwable) {
                Log.e("Lottery List Fail", t.toString())
                temp_num_fragment = 0
            }

            override fun onResponse(call: Call<GetLotteryListResponse>, response: Response<GetLotteryListResponse>) {
                Log.e("Lottery List Fail", response.body()!!.message)
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        temp_num_fragment = response.body()!!.data.size
                        //temp_num_fragment = 1

                        if(temp_num_fragment == 0) {
                            vpLotteryConfirm.visibility = INVISIBLE
                            tvLotteryNothing.visibility = VISIBLE
                        }
                        btnVisibilityCheck(vpLotteryConfirm.currentItem)
                        lotteryConfirmAdapter = LotteryConfirmAdapter(supportFragmentManager, temp_num_fragment)
                        vpLotteryConfirm.adapter = lotteryConfirmAdapter
                        lotteryConfirmAdapter.notifyDataSetChanged()
                    }
                }

            }
        })

        ibtnNextLeft.onClick {
            var position = vpLotteryConfirm.currentItem
            vpLotteryConfirm.setCurrentItem(position - 1, true)
            btnVisibilityCheck(vpLotteryConfirm.currentItem)
        }
        ibtnNextRight.onClick {
            var position = vpLotteryConfirm.currentItem
            vpLotteryConfirm.setCurrentItem(position + 1, true)
            btnVisibilityCheck(vpLotteryConfirm.currentItem)
        }
    }

    private fun configureMainContentsRV() {
        //progressON("Loading...")
        lateinit var cardListAdapter: CardListAdapter


        val getCardList = networkService.getCardList("application/json")
        getCardList.enqueue(object : Callback<GetCardListResponse> {
            override fun onFailure(call: Call<GetCardListResponse>, t: Throwable) {
                Log.e("Get Card List Failed: ", t.toString())
            }

            override fun onResponse(call: Call<GetCardListResponse>, response: Response<GetCardListResponse>) {
                //progressOFF()

                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        var cardListDataList: ArrayList<CardListData> = response.body()!!.data

                        cardListAdapter = CardListAdapter(applicationContext, cardListDataList)
                        rvContents.adapter = cardListAdapter
                        rvContents.layoutManager =
                            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                        rvContents.setHasFixedSize(true)


                        Log.d("TEST", cardListDataList.toString())
                        Log.d("TEST", cardListDataList.get(0).category)

                    }
                }
            }

        })


    }

    private fun btnVisibilityCheck(position: Int) { // TODO: 당첨확인 버튼 유무 체크
        if (position == 0) {
            if (temp_num_fragment == 1 || temp_num_fragment == 0) {
                //position이 0이고 데이터가 하나일때, 데이터가 하나도 없을 때 화살표 버튼이 둘다 뜨면 안됨
                ibtnNextRight.visibility = View.INVISIBLE
                ibtnNextLeft.visibility = View.INVISIBLE
            } else {
                ibtnNextRight.visibility = View.VISIBLE
                ibtnNextLeft.visibility = View.INVISIBLE
            }
        } else if (position == 1) {
            ibtnNextRight.visibility = View.INVISIBLE
            ibtnNextLeft.visibility = View.VISIBLE
        }
    }

    private fun drawerSelected() {
        ll_user_update.setOnClickListener {
            startActivity<UserUpdateActivity>()
        }
        rl_win.setOnClickListener {
            startActivity<MyLotteryActivity>()
        }
        rl_like.setOnClickListener {
            startActivity<KeepActivity>()
        }
        rl_notice.setOnClickListener {
            startActivity<NoticeActivity>()
        }
        rl_settings.setOnClickListener {
            startActivity<SettingsActivity>()
        }
        rl_FAQ.setOnClickListener {
            startActivity<QuestionActivity>()
        }
    }

    /*리사이클러뷰 간격*/
    class MarginItemDecoration(
        private
        val first_space: Int, private val space: Int
    ) :
        androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: androidx.recyclerview.widget.RecyclerView,
            state: androidx.recyclerview.widget.RecyclerView.State
        ) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    left = first_space
                }
                right = space
            }
        }
    }

    private fun getMainPosterResponse() {
        val getMainPosterResponse = networkService.getMainPosterResponse(
            "application/json"
        )
        getMainPosterResponse.enqueue(object : retrofit2.Callback<GetMainPosterResponse> {
            override fun onFailure(call: Call<GetMainPosterResponse>, t: Throwable) {
                Log.e("Main Poster List Fail", t.toString())
            }

            override fun onResponse(call: Call<GetMainPosterResponse>, response: Response<GetMainPosterResponse>) {
                hideProgressDialog()

                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        val tmp: ArrayList<ShowData> = response.body()!!.data!!
                        showMainRecyclerViewAdapter.dataList = tmp
                        showMainRecyclerViewAdapter.notifyDataSetChanged()

                        if (tmp.isEmpty()) {
                            rv_product.visibility = View.GONE
                            empty_view.visibility = View.VISIBLE
                        } else {
                            rv_product.visibility = View.VISIBLE
                            empty_view.visibility = View.GONE
                        }


                    }

                }
            }
        })
    }
    private fun getMyLotteryResponse() {

        val getMyLotteryResponse = networkService.getMyLotteryResponse("application/json", getUserToken(this))
        getMyLotteryResponse.enqueue(object : Callback<GetMyLotteryResponse> {
            override fun onFailure(call: Call<GetMyLotteryResponse>, t: Throwable) {
                Log.e("My Lottery Fail", t.toString())
            }

            override fun onResponse(call: Call<GetMyLotteryResponse>, response: Response<GetMyLotteryResponse>) {
                if (response.isSuccessful) {
                    Log.e("MainActivity::", "getMyLotteryResponse::onResponse::" + response.body()!!.message)
                    var size = response.body()!!.data.size
                    tv_win_num.text = size.toString()
                }
            }
        })
    }

}

