package com.example.goldenticket.Network.Get

import com.example.goldenticket.Data.LotteryListData

data class GetLotteryListResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<LotteryListData>
)