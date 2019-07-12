package com.dazzi.goldenticket.Network.Get

import com.dazzi.goldenticket.Data.MyLotteryDetailData

data class GetMyLotteryDetailResponse (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: MyLotteryDetailData
)