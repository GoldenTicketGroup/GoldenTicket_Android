package com.example.goldenticket.Network.Get

import com.example.goldenticket.Data.MyLotteryDetailData

data class GetMyLotteryDetailResponse (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: MyLotteryDetailData
)