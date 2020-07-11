package com.dazzi.goldenticket.Network.Get

import com.dazzi.goldenticket.Data.LotteryConfirmVPDetailData

data class GetLotteryConfirmDetailResponse (
     var status: Int,
     var success: Boolean,
     var message: String,
     var data: ArrayList<LotteryConfirmVPDetailData>
)