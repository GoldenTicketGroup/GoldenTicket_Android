package com.dazzi.goldenticket.Network.Get

import com.dazzi.goldenticket.Data.LotteryListData

data class GetLotteryListResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<LotteryListData>
)