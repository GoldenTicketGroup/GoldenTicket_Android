package com.example.goldenticket.Network.Post

import com.example.goldenticket.Data.CardListData

data class GetCardListResponse(
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<CardListData>
)