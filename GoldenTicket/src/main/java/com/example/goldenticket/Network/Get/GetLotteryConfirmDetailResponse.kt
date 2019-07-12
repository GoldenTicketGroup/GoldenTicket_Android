package com.example.goldenticket.Network.Get

import com.example.goldenticket.Data.LotteryConfirmVPDetailData

data class GetLotteryConfirmDetailResponse (
     var status: Int,
     var success: Boolean,
     var message: String,
     var data: ArrayList<LotteryConfirmVPDetailData>
)