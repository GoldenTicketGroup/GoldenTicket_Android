package com.dazzi.goldenticket.Network.Get

import com.dazzi.goldenticket.Data.CardDetailData

data class GetCardDetailResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: CardDetailData
)