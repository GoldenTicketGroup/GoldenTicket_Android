package com.example.goldenticket.Network.Post

import com.example.goldenticket.Data.CardDetailData

data class GetCardDetailResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: CardDetailData
)