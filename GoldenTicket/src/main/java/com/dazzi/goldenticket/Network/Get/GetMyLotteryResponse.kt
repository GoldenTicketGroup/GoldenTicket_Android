package com.dazzi.goldenticket.Network.Get

import com.dazzi.goldenticket.Data.MyLotteryData

data class GetMyLotteryResponse(
    val status: String,
    val success: Boolean,
    val message: String,
    val data: ArrayList<MyLotteryData>
)