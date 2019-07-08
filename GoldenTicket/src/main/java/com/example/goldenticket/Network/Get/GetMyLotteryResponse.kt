package com.example.goldenticket.Network.Get

import com.example.goldenticket.Data.MyLotteryData

data class GetMyLotteryResponse(
    val status: String,
    val success: Boolean,
    val message: String,
    val data: ArrayList<MyLotteryData>
)