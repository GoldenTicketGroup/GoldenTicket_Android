package com.example.goldenticket.Network.Get

import com.example.goldenticket.Data.CardDetailData

data class GetCardDetailResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: CardDetailData
)